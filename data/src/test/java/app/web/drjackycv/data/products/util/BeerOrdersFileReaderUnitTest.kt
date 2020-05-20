package app.web.drjackycv.data.products.util

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

@RunWith(MockitoJUnitRunner::class)
class BeerOrdersFileReaderUnitTest {

    private lateinit var beerOrdersFileReader: BeerOrdersFileReader
    private val loader = Thread.currentThread().contextClassLoader
    private val resourceName = "code_challenge_richer.txt"
    private val resourceStream: InputStream = loader!!.getResourceAsStream(resourceName)
    private val readFile = BufferedReader(InputStreamReader(resourceStream, "UTF-8"))

    @Before
    fun setUp() {
        beerOrdersFileReader = BeerOrdersFileReader(readFile)
    }

    @Test
    fun `read provided input and check the answer`() {
        var result: String = ""
        val pairLists = beerOrdersFileReader.readQuestion(readFile)
        beerOrdersFileReader.apply {
            result = pairLists.generateResult()
        }

        Assert.assertEquals("C B C B C", result)
    }

}