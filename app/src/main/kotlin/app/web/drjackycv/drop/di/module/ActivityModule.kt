package app.web.drjackycv.drop.di.module

import app.web.drjackycv.drop.di.scope.PerView
import app.web.drjackycv.presentation.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @PerView
    @ContributesAndroidInjector
    abstract fun main(): MainActivity

}