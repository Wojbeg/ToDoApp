package com.wojbeg.todoapp.ui

import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.PopupMenu
import android.widget.Toast
import androidx.core.view.accessibility.AccessibilityEventCompat.setAction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.wojbeg.todoapp.R
import com.wojbeg.todoapp.adapters.toDoListAdapter
import com.wojbeg.todoapp.databinding.ActivityMainBinding
import com.wojbeg.todoapp.model.Task
import com.wojbeg.todoapp.ui.viewmodels.mainViewModel.MainViewModel
import com.wojbeg.todoapp.ui.viewmodels.mainViewModel.MainViewModelFactory
import com.wojbeg.todoapp.utils.Constants.ADAPTER_TAG
import com.wojbeg.todoapp.utils.Constants.edit_tag
import com.wojbeg.todoapp.utils.Constants.recovered_task
import com.wojbeg.todoapp.utils.SortType
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()
    private val toDoListAdapter: toDoListAdapter by instance(ADAPTER_TAG)

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this, factory)[MainViewModel::class.java]

        setSortBtnAnimations()

        viewModel.getNumberOfTasks().observe(this, Observer {
            number->
            setNoTasksInfo(number == 0)
        })

        toDoListAdapter.setAdapterListener {
            task ->
            task.status = !(task.status!!)
            viewModel.updateTask(task)
        }

        setupRecyclerView()
        setupCreateTaskBtn()

        setupSwipe()

    }

    override fun onResume() {
        super.onResume()

        toDoListAdapter.differ.submitList(null)

        viewModel.tasks.observe(this, Observer {
                tasks->
            toDoListAdapter.differ.submitList(tasks)
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //TODO("toggle action button to dark mode")
       //menuInflater.inflate(,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setupSwipe() {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = toDoListAdapter.differ.currentList[position] as Task

                if(direction == ItemTouchHelper.RIGHT){

                    startActivityEdit(task)

                }else if(direction == ItemTouchHelper.LEFT){

                    viewModel.deleteTask(task)

                    Snackbar.make(binding.root, getString(R.string.delete_success), Snackbar.LENGTH_LONG).apply {
                        setAction(getString(R.string.undo)){

                            viewModel.saveTask(task)
                        }
                        show()
                    }

                }



            }
        }

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(binding.tasks)
        }

    }


    private fun startActivityEdit(task: Task){

        val intent = Intent(applicationContext, CreateTask::class.java);
        intent.putExtra(edit_tag, task)

        this.startActivity(intent);

    }

    private fun setupRecyclerView()=
        binding.tasks.apply {
            adapter = toDoListAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }


    private fun setSortBtnAnimations() {

        //create rotate animation
        val rotateAnimation = RotateAnimation(0f, 90f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimation.duration = 300
        rotateAnimation.fillAfter = true

        binding.sortType.apply {

            setOnClickListener {

                animateSortBtn(rotateAnimation)

                //show popup sorting menu
                showPopup(it)

            }


        }


    }

    private fun animateSortBtn(animation: RotateAnimation){
        binding.sortType.startAnimation(animation)
    }

    private fun showPopup(view: View){

        //create return -90* rotate animation
        val rotateAnimationReturn = RotateAnimation(90f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        rotateAnimationReturn.duration = 300
        rotateAnimationReturn.fillAfter = true

        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.sorting_popup_menu, popup.menu)

        popup.setOnMenuItemClickListener{
            item ->

            when(item.itemId){

                R.id.AlphabetSort -> {
                    viewModel.sortTasks(SortType.NAME)
                    showToastLong(getString(R.string.sortInfoAlphab))
                    true
                }

                R.id.DateSort -> {
                    viewModel.sortTasks(SortType.DATE)
                    showToastLong(getString(R.string.sortInfoDate))
                    true
                }

                R.id.PrioritySort -> {
                    viewModel.sortTasks(SortType.PRIORITY)
                    showToastLong(getString(R.string.sortInfoPriority))
                    true
                }

                R.id.TypeSort -> {
                    viewModel.sortTasks(SortType.TYPE)
                    showToastLong(getString(R.string.sortInfoType))
                    true
                }

                R.id.UnfinishedSort -> {
                    viewModel.sortTasks(SortType.STATUS)
                    showToastLong(getString(R.string.sortInfoUnfinished))
                    true
                }

                R.id.DefaultSort -> {
                    viewModel.sortTasks(SortType.ID)
                    showToastLong(getString(R.string.sortInfoDefault))
                    true
                }

                else->{
                    false
                }

            }
        }

        popup.setOnDismissListener {
            binding.sortType.startAnimation(rotateAnimationReturn)
        }
        popup.show()
    }

    private fun showToastLong(string: String){
        Toast.makeText(this, string, Toast.LENGTH_LONG).show()
    }

    private fun setNoTasksInfo(isEmpty: Boolean){

      var visibility: Int

      if(isEmpty){
          visibility = View.VISIBLE
      }else{
         visibility = View.GONE
      // GONE or INVISIBLE in this case does not matter
      }

      binding.noTasksImage.visibility = visibility
      binding.NoTasksInfo.visibility = visibility
      binding.NoTasksMoreInfo.visibility = visibility

    }

    private fun setupCreateTaskBtn() {

        binding.addTaskBtn.setOnClickListener {

            val intent = Intent(this, CreateTask::class.java);

            startActivity(intent);
        }
    }


}

