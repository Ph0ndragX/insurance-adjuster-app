package dev.ph0ndragx.insuranceadjusterapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.InspectionsViewModel

class AppViewModelFactory : ViewModelProvider.Factory {

    private val service: InspectionRequestsService = InspectionRequestsService()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InspectionsViewModel::class.java)) {
            return InspectionsViewModel(service) as T
        } else if (modelClass.isAssignableFrom(InspectionViewModel::class.java)) {
            return InspectionViewModel(service) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        val instance = AppViewModelFactory()
    }
}
