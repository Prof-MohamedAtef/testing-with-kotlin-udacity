package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTestRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class StatisticsViewModelTest{

    //implement ascynchrouns tasks using Architecture Components
    @get:Rule
    var instantExecutorRule=InstantTaskExecutorRule()

    /*
    set the main coroutines dispatcher for unit testing
     */
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule=MainCoroutineRule()

    //subject under test
    private lateinit var statisticsViewModel: StatisticsViewModel

    //Use a fake repository to be injected into the viewmodel.
    private lateinit var tasksRepository: FakeTestRepository

    @Before
    fun setupStatisticsViewModel(){
        // initialize the repository with no tasks.
        tasksRepository= FakeTestRepository()
        statisticsViewModel= StatisticsViewModel(tasksRepository)
    }

    @Test
    fun loadTasks_loading(){
        mainCoroutineRule.pauseDispatcher()
        statisticsViewModel.refresh()
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(true))
        mainCoroutineRule.resumeDispatcher()
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun loadStatisticsWhenTasksAreUnavailable_callErrorToDisplay(){
        //make repo return error
        tasksRepository.setReturnError(true)
        statisticsViewModel.refresh()
        //an error is shown

        assertThat(statisticsViewModel.empty.getOrAwaitValue(), `is`(true))
        assertThat(statisticsViewModel.error.getOrAwaitValue(), `is`(true))
    }
}