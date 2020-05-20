package app.web.drjackycv.data.products.util

import androidx.annotation.VisibleForTesting
import app.web.drjackycv.data.products.entity.Beer
import app.web.drjackycv.data.products.entity.BeerOfCustomer
import app.web.drjackycv.data.products.entity.Customer
import app.web.drjackycv.domain.base.Failure
import app.web.drjackycv.domain.base.FileResult
import io.reactivex.Single
import java.io.BufferedReader
import java.io.FileNotFoundException

class BeerOrdersFileReader constructor(
    private val readFile: BufferedReader
) {
    private var result: String = ""
    val orders: Single<FileResult<Pair<String, String>>>
        get() {
            return readFile()
        }

    private fun readFile(): Single<FileResult<Pair<String, String>>> {
        try {
            result = readQuestion(readFile).generateResult()
        } catch (exception: Throwable) {
            return when (exception) {
                is Failure.FileReaderException -> Single.just(FileResult.Failed(exception))
                is NumberFormatException -> {
                    Single.just(FileResult.Failed(Failure.FileReaderException(exception.message + "")))
                }
                is FileNotFoundException -> Single.just(
                    FileResult.Failed(FileNotFoundException(exception.message))
                )
                else -> Single.just(FileResult.Failed(exception))
            }
        } finally {
            readFile.close()
        }

        return Single.just(FileResult.Success(decorateIds(result)))
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun readQuestion(readFile: BufferedReader): Single<Pair<Int, List<Customer>>> {
        var keeper: String? = null
        var numberOfBeers: Int? = null
        val result = mutableListOf<Customer>()
        readFile.useLines { lines ->
            lines.forEachIndexed { index, s ->
                if (index == 0) {
                    s.trim().toIntOrNull()?.let { numberOfBeers = it }
                        ?: run { return Single.error(IllegalArgumentException()) }
                } else {
                    result += Customer(
                        s.split(" ")
                            .foldIndexed(mutableSetOf<Beer>(), { i, acc: MutableSet<Beer>, s ->
                                if (i % 2 == 0) {
                                    keeper = s
                                } else {
                                    when (s) {
                                        "B" -> keeper?.trim()?.toIntOrNull()
                                            ?.takeIf { 0 < it && it <= numberOfBeers!! }
                                            ?.let { code -> acc.add(Beer.B(code)) }
                                            ?: run { return Single.error(IllegalArgumentException()) }
                                        "C" -> keeper?.trim()?.toIntOrNull()
                                            ?.takeIf { 0 < it && it <= numberOfBeers!! }
                                            ?.let { code -> acc.add(Beer.C(code)) }
                                            ?: run { return Single.error(IllegalArgumentException()) }
                                        else -> return Single.error(IllegalArgumentException())
                                    }
                                }
                                acc
                            })
                    )
                }
            }
            return Single.just(Pair(numberOfBeers!!, result))
        }
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun Single<Pair<Int, List<Customer>>>.generateResult(): String =
        flatMap { p ->
            val aggregated = Array<MutableList<BeerOfCustomer>>(p.first) { mutableListOf() }
            (1..p.first).forEach { type ->
                p.second.forEach { customer ->
                    customer.preferences.forEach { beer ->
                        if (beer.type == type) {
                            aggregated[type - 1].add(BeerOfCustomer(beer, customer))
                        }
                    }
                }
            }

            val sorted = aggregated
                .mapIndexed { index, list: List<BeerOfCustomer> ->
                    Pair(index, list.sortedBy { if (it.beer is Beer.B) 1 else 0 })
                }
                .sortedBy { (_, list) -> list.totalPreferences }
                .map { (index, list) ->
                    println("$$$$$: ${index + 1}: ${list.map { it.beer }
                        .joinToString(", ")}: ${list.totalPreferences}")
                    Pair(index, list)
                }

            val result = Array<Beer?>(p.first) { null }
            sorted
                .forEach { (index, list: List<BeerOfCustomer>) ->
                    result[index] = decideBeer(index, list)
                }

            p.second.forEachIndexed { index, customer ->
                customer.preferences.forEach {
                    if ((it is Beer.B && result[it.type - 1] is Beer.B) ||
                        (it is Beer.C && result[it.type - 1] is Beer.B) ||
                        (it is Beer.C && result[it.type - 1] is Beer.C)
                    ) {
                        customer.happy = true
                    }
                }
            }

            return@flatMap Single.just(
                if (p.second.fold(true) { acc, customer -> acc && customer.happy }) {
                    result.joinToString(" ")
                } else {
                    "No solution exists"
                }
            )
        }.blockingGet()

    private fun BeerOfCustomer.makeHappy(): Beer {
        customer.happy = true
        return beer
    }

    private fun BeerOfCustomer.makeHappyWith(beer: Beer): Beer {
        customer.happy = true
        return beer
    }

    private val BeerOfCustomer.totalPreference
        get() = customer.preferences.size

    private val List<BeerOfCustomer>.totalPreferences
        get() = fold(0) { acc, bearOfCustomer -> bearOfCustomer.totalPreference + acc }

    private fun decideBeer(type: Int, list: List<BeerOfCustomer>): Beer? = list
        .takeIf { it.size == 1 && it.totalPreferences > 2 }
        ?.let { it.firstOrNull()?.makeHappyWith(Beer.C(type)) }
        ?: run {
            list
                .firstOrNull { !it.customer.happy }
                ?.let { it.makeHappy() }
                ?: run {
                    list
                        .firstOrNull { it.customer.happy }
                        ?.let { it.makeHappyWith(Beer.C(type)) }
                }
        }

    private fun decorateIds(chars: String): Pair<String, String> {
        var ids = ""
        val charsWithoutSpace = chars.replace(" ", "")

        for (i in 1..charsWithoutSpace.count()) {
            ids += i.toString()
            if (i != charsWithoutSpace.count())
                ids += "|"
        }

        return ids to charsWithoutSpace
    }

}