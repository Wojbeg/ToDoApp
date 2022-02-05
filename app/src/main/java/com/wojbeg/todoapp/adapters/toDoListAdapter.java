package com.wojbeg.todoapp.adapters;

import static com.wojbeg.todoapp.utils.Constants.doneColor_default;
import static com.wojbeg.todoapp.utils.Constants.toDoColor_default;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.wojbeg.todoapp.R;

import com.wojbeg.todoapp.databinding.TaskPreviewBinding;
import com.wojbeg.todoapp.model.Task;
import com.wojbeg.todoapp.utils.Formatter;

import java.time.format.DateTimeFormatter;

public class toDoListAdapter extends RecyclerView.Adapter<toDoListAdapter.TaskViewHolder> {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public AsyncListDiffer differ = new AsyncListDiffer<Task>(this, diffCallback);
    private adapterListener adapterListener;
    private int toDoColor, doneColor;

    private static final DiffUtil.ItemCallback<Task> diffCallback = new DiffUtil.ItemCallback<Task>() {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.equals(newItem);
        }

    };

    public toDoListAdapter() {
        toDoColor = Color.parseColor(toDoColor_default);
        doneColor = Color.parseColor(doneColor_default);
    }

    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View itemView =
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.task_preview,
                        parent,
                        false);


        return new TaskViewHolder(itemView, adapterListener);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder{

        TaskPreviewBinding binding = TaskPreviewBinding.bind(itemView);
        adapterListener adapterListener;

        public TaskViewHolder(View itemView, adapterListener listener) {
            super(itemView);
            adapterListener = listener;

        }

    }

    // onBindViewHolder - Replace the contents of a view
    public void onBindViewHolder(final TaskViewHolder viewHolder, int position) {

        Task task = (Task) differ.getCurrentList().get(position);
        boolean isChecked = task.getStatus();
        TaskPreviewBinding taskBinding = viewHolder.binding;

        taskBinding.taskCheckBox.setText(task.getName());
        taskBinding.taskCheckBox.setChecked(isChecked);
        taskBinding.taskType.setText(task.getType());
        taskBinding.taskPriority.setText(task.getPriority().toString());
        taskBinding.taskDate.setText(formatter.format(task.getDate()));

        setColors(viewHolder, isChecked);

        taskBinding.taskCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setColors(viewHolder, taskBinding.taskCheckBox.isChecked());
                adapterListener.onTaskClick(task);
            }
        });

    }


    //Changing colors when task complete
    public void setColors(TaskViewHolder viewHolder, boolean isChecked){
        TaskPreviewBinding taskBinding = viewHolder.binding;

        if(isChecked){

            taskBinding.taskCheckBox.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            CompoundButtonCompat.setButtonTintList(taskBinding.taskCheckBox, ColorStateList.valueOf(doneColor));
            taskBinding.taskDate.setTextColor(doneColor);
            taskBinding.taskPriority.setTextColor(doneColor);
            taskBinding.taskIcon.setColorFilter(doneColor);
            taskBinding.taskType.setTextColor(doneColor);
        }else {

            viewHolder.binding.taskCheckBox.setPaintFlags(0);
            CompoundButtonCompat.setButtonTintList(taskBinding.taskCheckBox, ColorStateList.valueOf(toDoColor));
            viewHolder.binding.taskDate.setTextColor(toDoColor);
            taskBinding.taskPriority.setTextColor(toDoColor);
            taskBinding.taskIcon.setColorFilter(toDoColor);
            taskBinding.taskType.setTextColor(toDoColor);

        }

    }

    public void setColors(int newToDoColor, int newDoneColor){
        toDoColor = newToDoColor;
        doneColor = newDoneColor;
    }

    public void setAdapterListener(toDoListAdapter.adapterListener adapterListener) {
        this.adapterListener = adapterListener;
    }

    public interface adapterListener{
        void onTaskClick(Task task);
    }

}
