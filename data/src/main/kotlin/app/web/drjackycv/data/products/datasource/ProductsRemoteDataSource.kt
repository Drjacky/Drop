package app.web.drjackycv.data.products.datasource

import app.web.drjackycv.data.network.BaseRemoteDataSource
import app.web.drjackycv.data.products.remote.ProductsApi
import app.web.drjackycv.domain.base.RecyclerItem
import javax.inject.Inject

class ProductsRemoteDataSource @Inject constructor(
    private val productsApi: ProductsApi
) : BaseRemoteDataSource() {

    fun getBeersById(
        ids: String,
        page: Int = 1,
        perPage: Int,
        onSuccess: (List<RecyclerItem>?) -> Unit
    ) {
        val request = productsApi.getBeers(
            ids = ids,
            page = page,
            perPage = perPage
        )
        syncRequest(request, onSuccess)
    }

}