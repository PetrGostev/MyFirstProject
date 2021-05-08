package ru.petrgostev.myfirstproject.ui.moviesDetails

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import ru.petrgostev.myfirstproject.TestCoroutineRule
import ru.petrgostev.myfirstproject.data.network.pojo.MovieDetailsResponse
import ru.petrgostev.myfirstproject.data.repository.IMoviesRepository

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MoviesDetailsViewModelTest {

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    lateinit var moviesRepository: IMoviesRepository

    @Mock
    private lateinit var movieObserver: Observer<MovieDetailsResponse>

    @Mock
    lateinit var movieDetailsResponse: MovieDetailsResponse

    @Test
    fun loadMovie() {
        testCoroutineRule.runBlockingTest {
            val viewModel = MoviesDetailsViewModel(moviesRepository)
            viewModel.movie.observeForever(movieObserver)
            viewModel._mutableMovie.postValue(movieDetailsResponse)
            verify(movieObserver).onChanged(movieDetailsResponse)
            viewModel.movie.removeObserver(movieObserver)
        }
    }
}