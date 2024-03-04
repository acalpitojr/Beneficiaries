package com.calpito.beneficiaries

import com.calpito.beneficiaries.interfaces.repository.RepositoryImpl
import org.junit.Test

import org.junit.Assert.*

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
    fun get_beneficiary() {
        val myBeneficiaries = RepositoryImpl.getBeneficiaryList()
        println(myBeneficiaries)
        assertEquals(4, 2 + 2)
    }
}