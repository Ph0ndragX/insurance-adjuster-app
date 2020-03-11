package dev.ph0ndragx.insuranceadjusterapp.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.InspectionsViewModel
import dev.ph0ndragx.insuranceadjusterapp.login.LoginViewModel

class AppViewModelFactory : ViewModelProvider.Factory {

    private val securityService: SecurityService = SecurityService()
    private val inspectionRequestsService: InspectionRequestsService = InspectionRequestsService()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InspectionsViewModel::class.java)) {
            return InspectionsViewModel(inspectionRequestsService) as T
        } else if (modelClass.isAssignableFrom(InspectionViewModel::class.java)) {
            return InspectionViewModel(inspectionRequestsService, securityService) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(securityService) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {
        val instance = AppViewModelFactory()
    }
}
