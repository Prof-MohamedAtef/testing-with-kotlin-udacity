package com.example.android.architecture.blueprints.todoapp.tasks

import android.app.Service
import android.os.Bundle
import androidx.appcompat.resources.R
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.android.architecture.blueprints.todoapp.ServiceLocator
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeAndroidTestRepository
import com.example.android.architecture.blueprints.todoapp.data.source.TasksRepository
import com.example.android.architecture.blueprints.todoapp.taskdetail.TaskDetailFragment
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class TasksFragmentTest{
    private lateinit var repository: TasksRepository

    @Before
    fun initRepository(){
        repository=FakeAndroidTestRepository()
        ServiceLocator.tasksRepository=repository
    }

    @After
    fun cleanupDb()= runBlockingTest { ServiceLocator.resetRepository() }

    @Test
    fun clickTask_navigateToDetailFragmentOne()= runBlockingTest {
        /*
        2 tasks on the screen when launched
         */
        repository.saveTask(Task("TITLE1", "DESCRIPTION1",false, "id1"))
        repository.saveTask(Task("TITLE2", "DESCRIPTION2",false, "id2"))
        val scenario=launchFragmentInContainer<TaskDetailFragment>(Bundle(), com.example.android.architecture.blueprints.todoapp.R.style.AppTheme)
        val navController=mock(NavController::class.java)

        /*
        my launching scenario using mocked/simulated navController
         */
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        // WHEN - Clicking on first item
//        onView(withId(R.id.tasks_list)).perform(RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
//            hasDescendant(withText("TITLE1")),click()))

        // THEN - verify that we navigate to the first detail screen
        verify(navController).navigate(
            TasksFragmentDirections.actionTasksFragmentToTaskDetailFragment("id1")
        )
    }
}