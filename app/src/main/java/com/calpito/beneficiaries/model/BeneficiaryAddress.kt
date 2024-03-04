package com.calpito.beneficiaries.model

data class BeneficiaryAddress(
    val firstLineMailing: String,
    val scndLineMailing: Any?,
    val city: String,
    val zipCode: String,
    val stateCode: String,
    val country: String,
)
