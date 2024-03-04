package com.calpito.beneficiaries.interfaces.repository

import com.calpito.beneficiaries.interfaces.RepositoryInterface
import com.calpito.beneficiaries.model.Beneficiary
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object RepositoryImpl : RepositoryInterface {
    //HARDCODED BENEFICARY DATA
    val testBeneficiaryJson = """[
  {
    "lastName": "Smith",
    "firstName": "John",
    "designationCode": "P",
    "beneType": "Spouse",
    "socialSecurityNumber": "XXXXX3333",
    "dateOfBirth": "04201979",
    "middleName": "D",
    "phoneNumber": "3035555555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Jane",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX4664",
    "dateOfBirth": "01112012",
    "middleName": "E",
    "phoneNumber": "3034455555",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Mary",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX3645",
    "dateOfBirth": "02122013",
    "middleName": "E",
    "phoneNumber": "2035557558",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "David",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX7652",
    "dateOfBirth": "09022013",
    "middleName": "E",
    "phoneNumber": "8094567777",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  },
  {
    "lastName": "Smith",
    "firstName": "Peter",
    "designationCode": "C",
    "beneType": "Child",
    "socialSecurityNumber": "XXXXX8756",
    "dateOfBirth": "10052014",
    "middleName": "E",
    "phoneNumber": "8194587755",
    "beneficiaryAddress": {
      "firstLineMailing": "8515 E Orchard Rd",
      "scndLineMailing": null,
      "city": "Greenwood Village",
      "zipCode": "80111",
      "stateCode": "CO",
      "country": "US"
    }
  }
]"""


    /*WE CURRENTLY HAVE TEST DATA, WHICH IS A HARDCODED JSON.
        * In a real application, we would typically get this list from an API call using Retrofit.
        * */
    override fun getBeneficiaryList(): ArrayList<Beneficiary> {
        val result: ArrayList<Beneficiary> = ArrayList()

        try {
            // Create a type token for a list of Beneficiary objects
            val listType = object : TypeToken<List<Beneficiary>>() {}.type
            // Parse the JSON string into a list of Beneficiary objects
            val beneficiaries: List<Beneficiary> = Gson().fromJson(testBeneficiaryJson, listType)
            result.addAll(beneficiaries)
        } catch (e: Exception) {
            TODO("Not yet implemented")
            result = ArrayList()
        }

        return result
    }
}