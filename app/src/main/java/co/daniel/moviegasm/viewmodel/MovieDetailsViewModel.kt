package co.daniel.moviegasm.viewmodel

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
class MovieDetailsViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {

    private val movieDetailsDataLiveData: MutableLiveData<ReturnResult<MoviesVO>> by lazy { MutableLiveData<ReturnResult<MoviesVO>>() }

    fun observeMovieDetails() = movieDetailsDataLiveData


    fun getMovieDetails(movieID: String) {
        movieRepository.getMovieByID(movieID)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("getMovieDetails($movieID)  : $it ")
                movieDetailsDataLiveData.postValue(ReturnResult.PositiveResult(it))
            }, {
                Timber.e("Error in getMovieDetails($movieID)  : $it ")
                movieDetailsDataLiveData.postValue(it.convertToErrorResult())
            }).addToCompositeDisposable()
    }

}