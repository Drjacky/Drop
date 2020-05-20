package app.web.drjackycv.drop.di.module

import android.app.Application
import android.content.Context
import android.content.res.Resources
import app.web.drjackycv.drop.application.DropApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideApplication(application: DropApplication): Application = application

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application

    @Provides
    @Singleton
    fun resources(application: Application): Resources = application.resources

}