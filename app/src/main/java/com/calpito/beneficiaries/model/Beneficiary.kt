package com.calpito.beneficiaries.model

data class Beneficiary(
    val lastName: String,
    val firstName: String,
    val designationCode: String,
    val beneType: String,
    val socialSecurityNumber: String,
    val dateOfBirth: String,
    val middleName: String,
    val phoneNumber: String,
    val beneficiaryAddress: BeneficiaryAddress,
)


