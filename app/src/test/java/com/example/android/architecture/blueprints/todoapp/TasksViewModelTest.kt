package com.example.android.architecture.blueprints.todoapp

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    @Test
    fun addNewTask_setsNewTaskEvent(){
        val tasksViewModel = TasksViewModel(ApplicationProvider.getApplicationContext())
    }
}