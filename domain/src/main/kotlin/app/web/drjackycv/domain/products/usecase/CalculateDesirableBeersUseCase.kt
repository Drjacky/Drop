package app.web.drjackycv.domain.products.usecase

import app.web.drjackycv.domain.base.FileResult
import app.web.drjackycv.domain.base.usecase.SingleUseCase
import app.web.drjackycv.domain.products.repository.ProductsRepository
import io.reactivex.Single
import java.io.BufferedReader
import javax.inject.Inject

class CalculateDesirableBeersUseCase @Inject constructor(
    private val productsRepository: ProductsRepository
) : SingleUseCase<FileResult<Pair<String, String>>, CalculateDesirableBeersParams> {

    override fun invoke(params: CalculateDesirableBeersParams): Single<FileResult<Pair<String, String>>> =
        productsRepository.calculateDesirableBeers(params.readFile)

}

inline class CalculateDesirableBeersParams(val readFile: BufferedReader)