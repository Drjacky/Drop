package app.web.drjackycv.drop.di.component

import app.web.drjackycv.drop.application.DropApplication
import app.web.drjackycv.drop.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        DataModule::class,
        ViewModelModule::class,
        ActivityModule::class,
        FragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<DropApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: DropApplication): Builder

        fun build(): AppComponent

    }

}