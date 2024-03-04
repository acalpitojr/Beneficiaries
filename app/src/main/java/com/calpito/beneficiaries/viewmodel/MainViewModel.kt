package com.calpito.beneficiaries.viewmodel

import androidx.lifecycle.ViewModel
import com.calpito.beneficiaries.interfaces.RepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val repository: RepositoryInterface): ViewModel() {
}