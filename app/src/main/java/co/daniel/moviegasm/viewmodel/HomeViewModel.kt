package co.daniel.moviegasm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.domain.interactors.GetMovies
import co.daniel.moviegasm.domain.interactors.SaveMovies
import co.daniel.moviegasm.repositories.MovieRepository
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class HomeViewModel @Inject constructor(
    private val getMovies: GetMovies,
    private val saveMovies: SaveMovies,
    private val movieRepository: MovieRepository
) :
    BaseViewModel() {

    var isMessageListAlreadyLoading: Boolean = false
    var hasMoreToLoadMessage: Boolean = true

    fun getMovieListFromNetwork(fetchFromStart: Boolean) {
        isMessageListAlreadyLoading = true
        getMovies.execute(
            GetMovies.GetMoviesParams(
                isFromCache = false,
                fetchFromStart = fetchFromStart
            )
        )
            .subscribe({
                Log.d("HomeViewModel", "getMovieList from network : $it ")
                hasMoreToLoadMessage = it.isNotEmpty()
                isMessageListAlreadyLoading = false
                val listToBeSaved = ArrayList<MoviesVO>()
                it.forEach { entry ->
                    listToBeSaved.add(entry)
                }

                saveMovies(listToBeSaved)
            }, {
                Timber.e("Error in getMovieList  : $it ")
                isMessageListAlreadyLoading = false
            }).addToCompositeDisposable()
    }

    fun saveMovies(listToBeSaved: List<MoviesVO>) {
        saveMovies.execute(SaveMovies.SaveMoviesParams(listToBeSaved))
            .subscribe {
                Log.i("HomeViewModel", "data saved $listToBeSaved")
            }.addToCompositeDisposable()
    }


    fun getMoviesListFromCache(): LiveData<List<MoviesVO>> {
//        isMessageListAlreadyLoading = false
        return movieRepository.getCachedMovies()
    }

}