package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import dev.ph0ndragx.insuranceadjusterapp.common.InspectionRequestsService
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.model.InspectionRequestsState
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import dev.ph0ndragx.insuranceadjusterapp.model.Status

class InspectionsViewModel(private val service: InspectionRequestsService) : ViewModel(), InspectionsFilterViewModel.FilterChangedListener {

    private val state: MutableLiveData<InspectionRequestsState> = MutableLiveData(InspectionRequestsState())

    val filter: InspectionsFilterViewModel = InspectionsFilterViewModel(this)

    fun inspectionRequests(): LiveData<List<InspectionRequest>> {
        return Transformations.map(state) { it.inspectionRequests }
    }

    fun loadInspectionRequests() {
        state.value = InspectionRequestsState(
            service.getInspectionRequests(state.value!!.filter),
            state.value!!.filter
        )
    }

    fun accept(number: String) {
        service.accept(number)
        loadInspectionRequests()
    }

    fun reject(number: String) {
        service.reject(number)
        loadInspectionRequests()
    }

    override fun onStatusSelected(status: Status) {
        val newFilter = state.value!!.filter.withStatus(status)
        state.value = InspectionRequestsState(
            service.getInspectionRequests(newFilter),
            newFilter
        )
    }

    override fun onStatusDeselected(status: Status) {
        val newFilter = state.value!!.filter.withoutStatus(status)
        state.value = InspectionRequestsState(
            service.getInspectionRequests(newFilter),
            newFilter
        )
    }
}
