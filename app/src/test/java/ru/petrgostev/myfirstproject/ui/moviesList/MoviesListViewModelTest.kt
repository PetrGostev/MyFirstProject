package ru.petrgostev.myfirstproject.ui.moviesList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagingData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import ru.petrgostev.myfirstproject.TestCoroutineRule
import ru.petrgostev.myfirstproject.data.dataBase.entity.MoviesEntity
import ru.petrgostev.myfirstproject.data.repository.IConfigurationRepository
import ru.petrgostev.myfirstproject.data.repository.IMoviesRepository

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesListViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    lateinit var viewModel: MoviesListViewModel

    @Mock
    lateinit var configurationRepository: IConfigurationRepository

    @Mock
    lateinit var moviesRepository: IMoviesRepository

    @Mock
    private lateinit var pagingObserver: Observer<PagingData<MoviesEntity>>

    @Mock
    lateinit var pagingData: PagingData<MoviesEntity>

    @Before
    fun init() {
        viewModel = MoviesListViewModel(configurationRepository, moviesRepository)
    }

    @Test
    fun getMoviesPagingList() {
        testCoroutineRule.runBlockingTest {
            viewModel.moviesPagingList.observeForever(pagingObserver)
            viewModel._mutableMoviesPagingList.postValue(pagingData)
            verify(pagingObserver).onChanged(pagingData)
            viewModel.moviesPagingList.removeObserver(pagingObserver)
        }
    }
}