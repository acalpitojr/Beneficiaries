package com.calpito.beneficiaries.repository

import com.calpito.beneficiaries.interfaces.RepositoryInterface
import com.calpito.beneficiaries.model.Beneficiary
import com.calpito.beneficiaries.utils.Utilities.convertBirthdayFormat
import com.calpito.beneficiaries.utils.Utilities.interpretDesignationCode
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class RepositoryImpl @Inject constructor() : RepositoryInterface {
    //HARDCODED BENEFICIARY DATA
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


    /**
     * Parses a JSON string containing beneficiary information into a list of [Beneficiary] objects.
     * For each beneficiary, this function modifies the designation code to append a descriptive text
     * and formats the date of birth into a more user-friendly format.
     *
     * @return An [ArrayList] of [Beneficiary] objects with modified designation codes and formatted dates of birth.
     */
    override fun getBeneficiaryList(): ArrayList<Beneficiary> {
        var result: ArrayList<Beneficiary> = ArrayList()

        try {
            // Create a type token for a list of Beneficiary objects
            /*WE CURRENTLY HAVE TEST DATA, WHICH IS A HARDCODED JSON.
            * In a real application, we would typically get this list from an API call using Retrofit.
            * */
            val listType = object : TypeToken<List<Beneficiary>>() {}.type
            // Parse the JSON string into a list of Beneficiary objects
            val beneficiaries: List<Beneficiary> = Gson().fromJson(testBeneficiaryJson, listType)

            //lets make it more clear for the user.  Lets show in detail what the code means,
            //and lets format the dob to something more friendly
            result.addAll(beneficiaries.map {
                it.copy(
                    designationCode = "${it.designationCode}-${
                        interpretDesignationCode(
                            it.designationCode
                        )
                    }",
                    dateOfBirth = "${convertBirthdayFormat(it.dateOfBirth)}"
                )
            })


        } catch (e: Exception) {
            result = ArrayList()
        }

        return result
    }

}
