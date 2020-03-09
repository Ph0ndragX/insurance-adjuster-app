package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ph0ndragx.insuranceadjusterapp.common.InspectionRequestsService
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest

class InspectionsViewModel(private val service: InspectionRequestsService) : ViewModel() {

    private val inspectionRequests: MutableLiveData<List<InspectionRequest>> = MutableLiveData(listOf())

    fun inspectionRequests(): LiveData<List<InspectionRequest>> {
        return inspectionRequests
    }

    fun loadInspectionRequests() {
        inspectionRequests.value = service.getInspectionRequests()
    }

    fun accept(number: String) {
        service.accept(number)
        loadInspectionRequests()
    }

    fun reject(number: String) {
        service.reject(number)
        loadInspectionRequests()
    }
}
