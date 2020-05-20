package app.web.drjackycv.domain.products.repository

import app.web.drjackycv.domain.base.FileResult
import app.web.drjackycv.domain.base.Listing
import app.web.drjackycv.domain.base.RecyclerItem
import io.reactivex.Single
import java.io.BufferedReader

interface ProductsRepository {

    fun getBeersById(ids: String, types: String): Listing<RecyclerItem>

    fun calculateDesirableBeers(readFile: BufferedReader): Single<FileResult<Pair<String, String>>>

}