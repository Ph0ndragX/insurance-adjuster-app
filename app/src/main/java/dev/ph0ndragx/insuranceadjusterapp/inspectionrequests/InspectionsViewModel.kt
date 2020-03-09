package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import dev.ph0ndragx.insuranceadjusterapp.model.Status

class InspectionsViewModel : ViewModel() {

    private val inspectionRequests: MutableLiveData<List<InspectionRequest>> = MutableLiveData(listOf())

    fun inspectionRequests(): LiveData<List<InspectionRequest>> {
        return inspectionRequests
    }

    fun loadInspectionRequests() {
        inspectionRequests.value = listOf(
            InspectionRequest(
                "T00001/2019/M/ZO/001",
                "Jan",
                "Kowalski",
                "+48 123 456 789",
                "ul. Rakowicka 18, 31-510 Kraków",
                50.068473,
                19.952604,
                Status.REJECTED
            ),
            InspectionRequest(
                "T00001/2019/M/ZO/002",
                "Marek",
                "Kowal",
                "+48 123 456 789",
                "ul. aleja Ignacego Daszyńskiego 21-23, 31-537 Kraków",
                50.056376,
                19.952216,
                Status.ASSIGNED
            ),
            InspectionRequest(
                "T00001/2019/M/ZO/003",
                "Stefan",
                "Lis",
                "+48 123 456 789",
                "ul. Switezanki 5, 31-000 Kraków",
                50.062653,
                19.979610,
                Status.ACCEPTED
            ),
            InspectionRequest(
                "T00001/2019/M/ZO/004",
                "Marek",
                "Kowal",
                "+48 123 456 789",
                "ul. aleja Ignacego Daszyńskiego 21-23, 31-537 Kraków",
                50.056376,
                19.952216,
                Status.ASSIGNED
            ),
            InspectionRequest(
                "T00001/2019/M/ZO/005",
                "Marek",
                "Kowal",
                "+48 123 456 789",
                "ul. aleja Ignacego Daszyńskiego 21-23, 31-537 Kraków",
                50.056376,
                19.952216,
                Status.ASSIGNED
            )
        )
    }

    fun accept(number: String) {
        val newRequests = inspectionRequests.value!!.toList()
        newRequests.find { it.number == number }!!.accept()
        inspectionRequests.value = newRequests
    }

    fun reject(number: String) {
        val newRequests = inspectionRequests.value!!.toList()
        newRequests.find { it.number == number }!!.reject()
        inspectionRequests.value = newRequests
    }
}
