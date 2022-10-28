package com.example.android.architecture.blueprints.todoapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert
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

        /*
           when change happens regarding added tasks
           Then, the new task event is triggered
         */
        val value= tasksViewModel.newTaskEvent.getOrAwaitValue();

        assertThat(
            value.getContentIfNotHandled(), (not(nullValue()))
        )
    }
}