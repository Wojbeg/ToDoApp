package com.wojbeg.todoapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.wojbeg.todoapp.R
import com.wojbeg.todoapp.databinding.ActivityCreateTaskBinding
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.ui.viewmodels.createTaskViewModel.CreateTaskViewModel
import com.wojbeg.todoapp.ui.viewmodels.createTaskViewModel.CreateTaskViewModelFactory
import com.wojbeg.todoapp.ui.viewmodels.createTaskViewModel.dataBinding.DataBindingListener
import com.wojbeg.todoapp.utils.Constants.edit_tag
import com.wojbeg.todoapp.utils.Priority
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.time.OffsetDateTime


class CreateTask : AppCompatActivity(), KodeinAware, DataBindingListener {

    //DI
    override val kodein by kodein()
    private val factory: CreateTaskViewModelFactory by instance()

    private lateinit var binding: ActivityCreateTaskBinding

    private lateinit var viewModel: CreateTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_task)

        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this, factory)[CreateTaskViewModel::class.java]
        viewModel.bindingListener = this

        binding.viewmodel = viewModel


        //Hide support bar
        supportActionBar?.hide();

        var window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.purple_200)
        }

        setupSpinnerPriority()

        binding.saveBtn.setOnClickListener {
            if(!viewModel.onSaveBtnClick()){
                binding.taskName.error = getString(R.string.emptyNameError)
                binding.taskName.requestFocus()
            }else{
                finish()
            }
        }



        if(intent!=null){
            if(intent.hasExtra(edit_tag)){

                binding.newTaskTitle.text = resources.getString(R.string.editTask)

                val recoveredTask = intent.extras?.get(edit_tag) as Task
                println("replaced $recoveredTask")
                viewModel.recoverTask(recoveredTask)

                println("działanie spinner ${recoveredTask.priority!!.ordinal}")

                binding.prioritySpinner.setSelection(recoveredTask.priority!!.ordinal)
                val typeArray = resources.getStringArray(R.array.taskType)
                binding.typeSpinner.setSelection(typeArray.indexOf(recoveredTask.type))
                println("działanie spinner ${typeArray.indexOf(recoveredTask.type)}")
                

            }

        }


    }

    private fun setupSpinnerPriority() {
        val spinner = binding.prioritySpinner
        spinner.adapter = ArrayAdapter<Priority>(this, android.R.layout.simple_spinner_item, Priority.values())
        spinner.setSelection(Priority.Normal.ordinal)
    }


    private fun pickDate(){

        val date = viewModel.date

        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                binding.dayValue.text = dayOfMonth.toString()
                binding.monthValue.text = (monthOfYear+1).toString()
                binding.yearValue.text = year.toString()


                viewModel.changeDate(
                    OffsetDateTime.of(year, monthOfYear+1, dayOfMonth, date.hour, date.minute, date.second, date.nano, date.offset)
                )

            },
            date.year,
            date.monthValue-1,
            date.dayOfMonth
        ).show()


    }

    override fun onCloseBtn() {
        finish()
    }

    override fun onChangeDateBtn() {
        pickDate()
    }

}