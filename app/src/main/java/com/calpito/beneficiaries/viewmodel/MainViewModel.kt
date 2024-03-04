package com.calpito.beneficiaries.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.calpito.beneficiaries.interfaces.RepositoryInterface
import com.calpito.beneficiaries.model.Beneficiary
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: RepositoryInterface) : ViewModel() {

    //List for recyclerView
    private val _beneficiaryList = MutableLiveData<List<Beneficiary>>()
    val beneficiaryList: LiveData<List<Beneficiary>> = _beneficiaryList

    fun getBeneficiariesAndDisplay() {
        val benficiaries = repository.getBeneficiaryList()
        //we want to interpret the designation code, lets add to its string
        _beneficiaryList.value = benficiaries
    }


}