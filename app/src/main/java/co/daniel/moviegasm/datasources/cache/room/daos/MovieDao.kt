package co.daniel.moviegasm.datasources.cache.room.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import co.daniel.moviegasm.domain.MoviesVO

@Dao
interface MovieDao : BaseDAO<MoviesVO> {
    @Query("select * from movies limit :count")
    fun getAllMovies(count: Int): LiveData<List<MoviesVO>>

    @Query("select * from movies where id=:movieID")
    fun getMovieByID(movieID: String): LiveData<MoviesVO>
}