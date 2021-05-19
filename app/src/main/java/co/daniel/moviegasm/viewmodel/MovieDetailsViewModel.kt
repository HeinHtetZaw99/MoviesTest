package co.daniel.moviegasm.viewmodel

import co.daniel.moviegasm.repositories.MovieRepository
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {

    fun getMovieDetails(movieID: String) = movieRepository.getMovieByID(movieID)

}