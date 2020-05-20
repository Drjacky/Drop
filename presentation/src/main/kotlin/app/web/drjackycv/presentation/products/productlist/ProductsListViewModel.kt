package app.web.drjackycv.presentation.products.productlist

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.PagedList
import app.web.drjackycv.domain.base.FileResult
import app.web.drjackycv.domain.base.Listing
import app.web.drjackycv.domain.base.RecyclerItem
import app.web.drjackycv.domain.products.usecase.CalculateDesirableBeersParams
import app.web.drjackycv.domain.products.usecase.CalculateDesirableBeersUseCase
import app.web.drjackycv.domain.products.usecase.GetBeersListParams
import app.web.drjackycv.domain.products.usecase.GetBeersListUseCase
import app.web.drjackycv.presentation.base.viewmodel.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject

class ProductsListViewModel @Inject constructor(
    private val getBeersListUseCase: GetBeersListUseCase,
    private val calculateDesirableBeersUseCase: CalculateDesirableBeersUseCase
) : BaseViewModel() {

    private val _idsQuery = MutableLiveData<Pair<String, String>>()
    private val _ldBeersList: LiveData<Listing<RecyclerItem>> = map(_idsQuery) {
        getBeersListUseCase(GetBeersListParams(ids = it.first, types = it.second))
    }

    val ldBeersList: LiveData<PagedList<RecyclerItem>> = switchMap(_ldBeersList) {
        loading(false)
        it.pagedList
    }

    fun getProductsList(bufferedReader: BufferedReader) {
        loading(true)
        calculateDesirableBeersUseCase(CalculateDesirableBeersParams(bufferedReader))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loading(true) }
            .doAfterTerminate { loading(false) }
            .subscribe({ result ->
                when (result) {
                    is FileResult.Success -> {
                        _idsQuery.value = result.result
                    }
                    is FileResult.Failed -> handleFailure(result.exception) {
                        //getProductsList(bufferedReader)
                    }
                }
            }, { throwable ->
                handleFailure(throwable) { /*getProductsList(bufferedReader)*/ }
            })
            .addTo(compositeDisposable)


    }

    fun readRawTextFile(context: Context, resId: Int): BufferedReader {
        val inputStream: InputStream = context.resources.openRawResource(resId)
        val inputReader = InputStreamReader(inputStream)

        return BufferedReader(inputReader)
    }

}