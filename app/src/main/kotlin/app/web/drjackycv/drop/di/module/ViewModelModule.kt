package app.web.drjackycv.drop.di.module

import androidx.lifecycle.ViewModelProvider
import app.web.drjackycv.drop.di.viewmodel.ViewModelFactory
import app.web.drjackycv.drop.di.viewmodel.ViewModelKey
import app.web.drjackycv.presentation.base.viewmodel.BaseViewModel
import app.web.drjackycv.presentation.products.productlist.ProductsListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun factory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ProductsListViewModel::class)
    abstract fun productsList(vm: ProductsListViewModel): BaseViewModel

}