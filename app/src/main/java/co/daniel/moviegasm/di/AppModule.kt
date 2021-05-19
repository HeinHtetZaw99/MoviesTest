package co.daniel.moviegasm.di

import dagger.Module

@Module(
    includes = [BaseAppModule::class, AppModule.Provider::class, ActivityModule::class, ViewModelModule::class, ComponentModule::class]
)
abstract class AppModule {

    @Module
    object Provider {


        /* @Provides
         @JvmStatic
         @Singleton
         fun getDataBase(context : Context) : AppDatabase {
             return Room.databaseBuilder(context.applicationContext , AppDatabase::class.java , "app_offline_data_base.db")
                 .allowMainThreadQueries()
                 .fallbackToDestructiveMigration()
                 .build()
         }*/

    }
}