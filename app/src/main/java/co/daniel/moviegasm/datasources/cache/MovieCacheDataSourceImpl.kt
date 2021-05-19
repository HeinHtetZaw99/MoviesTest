package co.daniel.moviegasm.datasources.cache

import android.util.Log
import androidx.lifecycle.LiveData
import co.daniel.moviegasm.datasources.MovieCacheDataSource
import co.daniel.moviegasm.datasources.cache.room.daos.MovieDao
import co.daniel.moviegasm.domain.MoviesVO
import javax.inject.Inject

/**
 * Created by HeinHtetZaw on 5/19/21.
 */
const val CACHE_PAGE_SIZE = 10

class MovieCacheDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieCacheDataSource {

    override fun getMoviesList(pageNumber: Int): LiveData<List<MoviesVO>> {
        Log.v("CacheDataSourceImpl","loading for page : $pageNumber")
        return movieDao.getAllMovies(
            pageNumber * CACHE_PAGE_SIZE/*,
            (((pageNumber - 1) * CACHE_PAGE_SIZE)) + 1*/
        )
    }

    override fun getTempToken(): String {
        return ""
    }

    override fun getMovieByID(movieID: String): LiveData<MoviesVO> {
        return movieDao.getMovieByID(movieID)
    }

    override fun saveMovies(dataList: List<MoviesVO>) {
        movieDao.bulkInsert(dataList)
    }
}