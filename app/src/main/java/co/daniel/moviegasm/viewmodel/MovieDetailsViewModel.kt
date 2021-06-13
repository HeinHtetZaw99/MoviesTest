package co.daniel.moviegasm.viewmodel

import co.daniel.moviegasm.di.modules.MovieRepositoryTag
import co.daniel.moviegasm.repositories.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
@HiltViewModel
class MovieDetailsViewModel @Inject constructor(@MovieRepositoryTag private val movieRepository: MovieRepository) :
    BaseViewModel() {

    fun getMovieDetails(movieID: String) = movieRepository.getMovieByID(movieID)

}