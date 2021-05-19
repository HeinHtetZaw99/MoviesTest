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
class HomeViewModel @Inject constructor(private val movieRepository: MovieRepository) :
    BaseViewModel() {

    private val moviesDataLiveData: MutableLiveData<ReturnResult<List<MoviesVO>>> by lazy { MutableLiveData<ReturnResult<List<MoviesVO>>>() }

    fun observeMovieList() = moviesDataLiveData

    fun getMovieList(pageNumber: Int) {
        movieRepository.getMoviesList(pageNumber)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Timber.d("getMovieList  : $it ")
                moviesDataLiveData.postValue(ReturnResult.PositiveResult(it))
            }, {
                Timber.e("Error in getMovieList  : $it ")
                moviesDataLiveData.postValue(it.convertToErrorResult())
            }).addToCompositeDisposable()
    }


}