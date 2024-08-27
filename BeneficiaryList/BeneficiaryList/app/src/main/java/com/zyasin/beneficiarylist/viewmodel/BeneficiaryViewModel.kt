package com.zyasin.beneficiarylist.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zyasin.beneficiarylist.model.Beneficiary
import com.zyasin.beneficiarylist.repository.BeneficiaryRepository

// ViewModel class for managing beneficiary data.
class BeneficiaryViewModel(application: Application) : AndroidViewModel(application) {

    private val beneficiaryRepository = BeneficiaryRepository(application)
    private val _beneficiaries = MutableLiveData<List<Beneficiary>>()
    val beneficiaries: LiveData<List<Beneficiary>> = _beneficiaries

    init {
        loadBeneficiaries()
    }

    // Initializes the data by loading it from the repository and assigning it to the LiveData.
    private fun loadBeneficiaries() {
        _beneficiaries.value = beneficiaryRepository.getBeneficiaries()
    }
}

