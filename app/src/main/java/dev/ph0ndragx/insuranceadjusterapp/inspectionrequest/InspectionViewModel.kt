package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ph0ndragx.insuranceadjusterapp.common.InspectionRequestsService
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import dev.ph0ndragx.insuranceadjusterapp.model.Note

class InspectionViewModel(private val service: InspectionRequestsService) : ViewModel() {

    private val inspectionRequest: MutableLiveData<InspectionRequest> = MutableLiveData()

    fun inspectionRequest(): LiveData<InspectionRequest> {
        return inspectionRequest
    }

    fun loadInspectionRequest(number: String) {
        inspectionRequest.value = service.getInspectionRequest(number)
    }

    fun addNote(inspection: InspectionRequest?, note: Note) {
        if (inspection == null) return
        service.addNote(inspection.number, note)
        loadInspectionRequest(inspection.number)
    }
}
