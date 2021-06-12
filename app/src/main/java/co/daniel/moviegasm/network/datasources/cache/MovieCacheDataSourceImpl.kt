package co.daniel.moviegasm.network.datasources.cache

import android.util.Log
import androidx.lifecycle.LiveData
import co.daniel.moviegasm.network.datasources.MovieCacheDataSource
import co.daniel.moviegasm.network.datasources.cache.room.daos.MovieDao
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
        return movieDao.getAllMoviesByPageNumber(
            pageNumber * CACHE_PAGE_SIZE
        )
    }

    override fun getMoviesList(): LiveData<List<MoviesVO>> {
        Log.v("CacheDataSourceImpl","observing all movies list")
        return movieDao.getAllMovies()
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