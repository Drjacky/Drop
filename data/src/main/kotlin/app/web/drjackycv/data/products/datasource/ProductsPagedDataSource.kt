package app.web.drjackycv.data.products.datasource

import android.annotation.SuppressLint
import androidx.paging.PageKeyedDataSource
import app.web.drjackycv.domain.base.RecyclerItem
import app.web.drjackycv.domain.products.entity.Beer
import javax.inject.Inject

@SuppressLint("CheckResult")
class PhotoRemotePagedDataSource @Inject constructor(
    private val ids: String,
    private val types: String,
    private val productsRemoteDataSource: ProductsRemoteDataSource
) : PageKeyedDataSource<Int, RecyclerItem>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, RecyclerItem>
    ) {
        val currentPage = 1
        val nextPage = currentPage + 1

        productsRemoteDataSource.getBeersById(
            ids = ids,
            page = currentPage,
            perPage = params.requestedLoadSize,
            onSuccess = { responseBody ->
                val items = responseBody ?: emptyList()
                items.mapIndexed { i, item ->
                    item.apply {
                        (this as Beer).tagline = types[i].toString()
                    }
                }
                callback.onResult(items, null, nextPage)
            })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, RecyclerItem>) {
        val currentPage = params.key
        val nextPage = currentPage + 1

        productsRemoteDataSource.getBeersById(
            ids = ids,
            page = currentPage,
            perPage = params.requestedLoadSize,
            onSuccess = { responseBody ->
                val items = responseBody ?: emptyList()
                callback.onResult(items, nextPage)
            })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, RecyclerItem>) {}

}