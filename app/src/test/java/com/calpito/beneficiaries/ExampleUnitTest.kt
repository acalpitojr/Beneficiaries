package com.calpito.beneficiaries

import com.calpito.beneficiaries.repository.RepositoryImpl
import com.calpito.beneficiaries.utils.Utilities.convertBirthdayFormat
import com.calpito.beneficiaries.utils.Utilities.interpretDesignationCode
import com.google.gson.Gson
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
        val repo = RepositoryImpl()
        val myBeneficiaries = repo.getBeneficiaryList()
        val gson = Gson()
        val jsonStr = gson.toJson(myBeneficiaries)
        val expectedJsonStr = """[{"lastName":"Smith","firstName":"John","designationCode":"P-Primary","beneType":"Spouse","socialSecurityNumber":"XXXXX3333","dateOfBirth":"04/20/1979","middleName":"D","phoneNumber":"3035555555","beneficiaryAddress":{"firstLineMailing":"8515 E Orchard Rd","city":"Greenwood Village","zipCode":"80111","stateCode":"CO","country":"US"}},{"lastName":"Smith","firstName":"Jane","designationCode":"C-Contingent","beneType":"Child","socialSecurityNumber":"XXXXX4664","dateOfBirth":"01/11/2012","middleName":"E","phoneNumber":"3034455555","beneficiaryAddress":{"firstLineMailing":"8515 E Orchard Rd","city":"Greenwood Village","zipCode":"80111","stateCode":"CO","country":"US"}},{"lastName":"Smith","firstName":"Mary","designationCode":"C-Contingent","beneType":"Child","socialSecurityNumber":"XXXXX3645","dateOfBirth":"02/12/2013","middleName":"E","phoneNumber":"2035557558","beneficiaryAddress":{"firstLineMailing":"8515 E Orchard Rd","city":"Greenwood Village","zipCode":"80111","stateCode":"CO","country":"US"}},{"lastName":"Smith","firstName":"David","designationCode":"C-Contingent","beneType":"Child","socialSecurityNumber":"XXXXX7652","dateOfBirth":"09/02/2013","middleName":"E","phoneNumber":"8094567777","beneficiaryAddress":{"firstLineMailing":"8515 E Orchard Rd","city":"Greenwood Village","zipCode":"80111","stateCode":"CO","country":"US"}},{"lastName":"Smith","firstName":"Peter","designationCode":"C-Contingent","beneType":"Child","socialSecurityNumber":"XXXXX8756","dateOfBirth":"10/05/2014","middleName":"E","phoneNumber":"8194587755","beneficiaryAddress":{"firstLineMailing":"8515 E Orchard Rd","city":"Greenwood Village","zipCode":"80111","stateCode":"CO","country":"US"}}]"""
        assertEquals(jsonStr, expectedJsonStr)
    }

    @Test
    fun birthday_format_correct(){
        /*- Input: "04201979"
        * - Output: "04/20/1979"*/
        val input = "04201979"
        val result = convertBirthdayFormat(input)
        val expected = "04/20/1979"
        assertEquals(expected, result)
    }

    @Test
    fun designation_code_test(){
       /*"P": Primary
        "C": Contingent*/
        val result = interpretDesignationCode("P")
        val expected = "Primary"

        assertEquals(expected, result)
    }

    @Test
    fun designation_code_test2(){
        /*"P": Primary
         "C": Contingent*/
        val result = interpretDesignationCode("C")
        val expected = "Contingent"

        assertEquals(expected, result)
    }
}