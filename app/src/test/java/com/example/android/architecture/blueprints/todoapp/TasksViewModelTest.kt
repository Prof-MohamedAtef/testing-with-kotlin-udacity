package com.example.android.architecture.blueprints.todoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {

    /*
    testing livedata
    add this Rule
     */

    @get:Rule
    var instantExecutorRule=InstantTaskExecutorRule()

    @Test
    fun addNewTask_setsNewTaskEvent(){
        // Given a fresh ViewModel
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())

        // Adding New Task
        tasksViewModel.addNewTask()

        // when change happens regarding added tasks
        /*
            Oopps, we don't have a lifecycle owner in testing.
            So, we need to add an observer forever and then remove it
         */
        tasksViewModel.newTaskEvent.observe()
    }
}