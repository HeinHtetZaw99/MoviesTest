package co.daniel.moviegasm.network.datasources.cache.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import co.daniel.moviegasm.domain.MoviesVO

@Dao
interface MovieDao : BaseDAO<MoviesVO> {
    @Query("select * from movies limit :count")
    fun getAllMoviesByPageNumber(count: Int): LiveData<List<MoviesVO>>

    @Query("select * from movies")
    fun getAllMovies(): LiveData<List<MoviesVO>>

    @Query("select * from movies where id=:movieID")
    fun getMovieByID(movieID: String): LiveData<MoviesVO>
}