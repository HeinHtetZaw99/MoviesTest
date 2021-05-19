package co.daniel.moviegasm.di

import android.content.Context
import androidx.room.Room
import co.daniel.moviegasm.datasources.cache.room.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(
    includes = [BaseAppModule::class, AppModule.Provider::class, ActivityModule::class, ViewModelModule::class, ComponentModule::class]
)
abstract class AppModule {

    @Module
    object Provider {


        @Provides
        @JvmStatic
        @Singleton
        fun getDataBase(context: Context): AppDatabase {
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
}