package ru.skillbranch.skillarticles

import org.junit.Test

import org.junit.Assert.*
import ru.skillbranch.skillarticles.extensions.indexesOf

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun indexesOfString() {
        val result = "lorem ipsum sum".indexesOf("sum")
        val expected = listOf(8, 12)
        assertEquals(result, expected)
    }
}
