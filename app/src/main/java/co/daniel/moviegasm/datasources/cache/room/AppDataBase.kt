package co.daniel.moviegasm.datasources.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import co.daniel.moviegasm.domain.MoviesVO
import co.daniel.moviegasm.datasources.cache.room.daos.MovieDao

@Database(entities = [MoviesVO::class] , version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDao() : MovieDao
}