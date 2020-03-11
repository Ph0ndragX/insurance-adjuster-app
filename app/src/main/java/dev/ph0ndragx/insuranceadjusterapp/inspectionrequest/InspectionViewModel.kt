package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.ph0ndragx.insuranceadjusterapp.common.InspectionRequestsService
import dev.ph0ndragx.insuranceadjusterapp.common.SecurityService
import dev.ph0ndragx.insuranceadjusterapp.model.Document
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import dev.ph0ndragx.insuranceadjusterapp.model.Note
import dev.ph0ndragx.insuranceadjusterapp.model.inspection.Inspection

class InspectionViewModel(
    private val service: InspectionRequestsService,
    private val security: SecurityService
) : ViewModel() {

    private val inspectionRequest: MutableLiveData<InspectionRequest> = MutableLiveData()

    fun user(): String {
        return security.login()
    }

    fun inspectionRequest(): LiveData<InspectionRequest> {
        return inspectionRequest
    }

    fun inspections(): LiveData<List<Inspection>> {
        return Transformations.map(inspectionRequest) {
            it.inspections
        }
    }

    fun loadInspectionRequest(number: String) {
        inspectionRequest.value = service.getInspectionRequest(number)
    }

    fun addNote(inspection: InspectionRequest, note: Note) {
        service.addNote(inspection.number, note)
        loadInspectionRequest(inspection.number)
    }

    fun addDocument(inspection: InspectionRequest, document: Document) {
        service.addDocument(inspection.number, document)
        loadInspectionRequest(inspection.number)
    }
}
