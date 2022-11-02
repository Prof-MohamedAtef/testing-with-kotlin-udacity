package com.example.android.architecture.blueprints.todoapp.taskdetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailViewModel
import com.example.android.architecture.blueprints.todoapp.tasks.TasksViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TaskDetailViewModelTest {
    //add fake test ( Test Double) Repository

    private lateinit var tasksRepository: FakeTestRepository

    // Subject under test
    private lateinit var taskDetailViewModel: TaskDetailViewModel

    @Before
    fun setupViewModel() {

        tasksRepository= FakeTestRepository()
        val task1= Task("Title1", "Description1")
        val task2= Task("Title3", "Description2", true)
        val task3= Task("Title2", "Description3", true)
        tasksRepository.addTasks(task1, task2, task3)

        taskDetailViewModel = TaskDetailViewModel(tasksRepository)
    }

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()






}