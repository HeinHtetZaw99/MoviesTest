package co.daniel.moviegasm.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.domain.ReturnResult
import co.daniel.moviegasm.repositories.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {

    var isMessageListAlreadyLoading: Boolean = false
    var hasMoreToLoadMessage: Boolean = true

    fun getMovieListFromNetwork() {
        isMessageListAlreadyLoading = true
        movieRepository.getMoviesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Paging","getMovieList from network : $it ")
                hasMoreToLoadMessage = it.isNotEmpty()
                isMessageListAlreadyLoading = false
                val listToBeSaved = ArrayList<MoviesVO>()
                it.forEach { entry ->
                    if (entry != null)
                        listToBeSaved.add(entry)
                }
                movieRepository.saveMovies(listToBeSaved)

            }, {
                Timber.e("Error in getMovieList  : $it ")
                isMessageListAlreadyLoading = false

            }).addToCompositeDisposable()
    }

    fun getMoviesListFromCache(fetchFromStart: Boolean): LiveData<List<MoviesVO>> {
        isMessageListAlreadyLoading = false
        return movieRepository.getCachedMovies(fetchFromStart)
    }

}