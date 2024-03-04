package com.calpito.beneficiaries.interfaces

import com.calpito.beneficiaries.model.Beneficiary

interface RepositoryInterface {
    fun getBeneficiaryList() : ArrayList<Beneficiary>
}