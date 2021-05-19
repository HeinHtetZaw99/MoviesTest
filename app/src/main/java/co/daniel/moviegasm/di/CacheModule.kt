package co.daniel.moviegasm.di


import dagger.Module

@Module(includes = [CacheModule.Provider::class])
abstract class CacheModule {
    /*@Binds
    abstract fun userDataCacheSourceImpl(
        userDataCacheSourceImpl : UserDataCacheSourceImpl
    ) : UserDataCacheSource

    @Binds
    abstract fun offlineOTPCacheSourceImpl(
        offlineOTPCacheSourceImpl : OfflineOTPCacheSourceImpl
    ) : OfflineOTPCacheSource
*/
    @Module
    object Provider {
        /* @Provides
         @JvmStatic
         @Singleton
         fun getOTPDao(appDatabase : AppDatabase) = appDatabase.getOTPDao()*/
    }

}
