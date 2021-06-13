package co.daniel.moviegasm.di

import android.content.Context
import androidx.room.Room
import co.daniel.moviegasm.di.modules.BaseAppModule
import co.daniel.moviegasm.network.datasources.cache.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(
    includes = [BaseAppModule::class]
)
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @JvmStatic
    @Singleton
    fun getDataBase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "movie_offline_data_base.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }


}