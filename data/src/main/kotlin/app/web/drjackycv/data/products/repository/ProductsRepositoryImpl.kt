package app.web.drjackycv.data.products.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import app.web.drjackycv.data.products.datasource.ProductsRemoteDataSource
import app.web.drjackycv.data.products.datasource.ProductsRemoteDataSourceFactory
import app.web.drjackycv.data.products.util.BeerOrdersFileReader
import app.web.drjackycv.domain.base.FileResult
import app.web.drjackycv.domain.base.Listing
import app.web.drjackycv.domain.base.RecyclerItem
import app.web.drjackycv.domain.products.repository.ProductsRepository
import io.reactivex.Single
import java.io.BufferedReader
import java.util.concurrent.Executor
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val productsRemoteDataSource: ProductsRemoteDataSource,
    private val networkExecutor: Executor
) : ProductsRepository {

    override fun getBeersById(ids: String, types: String): Listing<RecyclerItem> {
        val factory = productRemoteDataSourceFactory(ids, types)
        val config = pagedListConfig()
        val livePagedList: LiveData<PagedList<RecyclerItem>> = LivePagedListBuilder(factory, config)
            .setFetchExecutor(networkExecutor)
            .build()

        return Listing(livePagedList)
    }

    override fun calculateDesirableBeers(readFile: BufferedReader): Single<FileResult<Pair<String, String>>> {
        return BeerOrdersFileReader(readFile = readFile).orders
    }

    private fun pagedListConfig(pageSize: Int = 5): PagedList.Config {
        return PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(pageSize * 2)
            .setPageSize(pageSize)
            .build()
    }

    private fun productRemoteDataSourceFactory(ids: String, types: String) =
        ProductsRemoteDataSourceFactory(ids, types, productsRemoteDataSource)

}