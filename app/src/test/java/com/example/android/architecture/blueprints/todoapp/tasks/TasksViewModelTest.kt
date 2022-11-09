package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.ServiceLocator.tasksRepository
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineContext
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

// removed Junit as it support application dependency, and we removed it and no longer need to use
// androidx codes.
@ExperimentalCoroutinesApi
class TasksViewModelTest {


    @get:Rule
    var mainCoroutineRule=MainCoroutineRule()

    //add fake test ( Test Double) Repository

    private lateinit var tasksRepository: FakeTestRepository

    // Subject under test
    private lateinit var tasksViewModel: TasksViewModel


    @Before
    fun setupViewModel() {

        tasksRepository= FakeTestRepository()
        val task1=Task("Title1", "Description1")
        val task2=Task("Title3", "Description2", true)
        val task3=Task("Title2", "Description3", true)
        tasksRepository.addTasks(task1, task2, task3)

        tasksViewModel = TasksViewModel(tasksRepository)
    }

    /*
    testing livedata
    add this Rule
    // Executes each task synchronously using Architecture Components.
     */

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Test
    fun addNewTask_setsNewTaskEvent() {
        // Given a fresh ViewModel

        // Adding New Task
        tasksViewModel.addNewTask()

        /*
           when change happens regarding added tasks
           Then, the new task event is triggered
         */
        val value = tasksViewModel.newTaskEvent.getOrAwaitValue();

        assertThat(
            value.getContentIfNotHandled(), (not(nullValue()))
        )
    }

    @Test
    fun getTasksAddViewVisible() {

        // When the filter type is ALL_TASKS
        tasksViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        assertThat(tasksViewModel.tasksAddViewVisible.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun completeTask_dataAndSnackbarUpdated(){
        val task=Task("Title","Description")
        tasksRepository.addTasks(task)

        tasksViewModel.completeTask(task, true)

        /*
        verify the task is completed
         */
        assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted, `is`(true))

        /*
        verify snackbar is updated
         */
        val snackbarText: Event<Int> = tasksViewModel.snackbarText.getOrAwaitValue()
        assertThat(snackbarText.getContentIfNotHandled(), `is`(R.string.task_marked_complete))
    }
}