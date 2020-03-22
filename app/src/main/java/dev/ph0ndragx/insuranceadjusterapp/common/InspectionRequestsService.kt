package dev.ph0ndragx.insuranceadjusterapp.common

import dev.ph0ndragx.insuranceadjusterapp.model.Document
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import dev.ph0ndragx.insuranceadjusterapp.model.Note
import dev.ph0ndragx.insuranceadjusterapp.model.Status
import dev.ph0ndragx.insuranceadjusterapp.model.claim.Claim
import dev.ph0ndragx.insuranceadjusterapp.model.inspection.Inspection
import java.util.*

class InspectionRequestsService {

    private var store: MutableList<InspectionRequest> = mutableListOf(
        InspectionRequest(
            Claim(
                "T00001",
                Date()
            ),
            "T00001/2019/M/ZO/001",
            "Jan",
            "Kowalski",
            "+48 123 456 789",
            "ul. Rakowicka 18, 31-510 Kraków",
            50.068473,
            19.952604,
            Status.ACCEPTED,
            mutableListOf(
                Note("Hello World!", "Kermit", Date())
            ),
            mutableListOf(
                Document("test.pdf", "Kermit", Date()),
                Document("test2.pdf", "Kermit Jakis", Date())
            ),
            inspections = mutableListOf(
                Inspection(1, Date(), Date(), "Pierwsza wykonana bez problemu"),
                Inspection(2, Date(), Date(), "Druga wykonana bez problemu"),
                Inspection(3, Date(), Date(), "Trzecia też")
            )
        ),
        InspectionRequest(
            Claim(
                "T00002",
                Date()
            ),
            "T00002/2019/M/ZO/002",
            "Marek",
            "Kowal",
            "+48 123 456 789",
            "ul. aleja Ignacego Daszyńskiego 21-23, 31-537 Kraków",
            50.056376,
            19.952216,
            Status.ASSIGNED
        ),
        InspectionRequest(
            Claim(
                "T00003",
                Date()
            ),
            "T00003/2019/M/ZO/003",
            "Stefan",
            "Lis",
            "+48 123 456 789",
            "ul. Switezanki 5, 31-000 Kraków",
            50.062653,
            19.979610,
            Status.ACCEPTED
        ),
        InspectionRequest(
            Claim(
                "T00004",
                Date()
            ),
            "T00004/2019/M/ZO/004",
            "Marek",
            "Kowal",
            "+48 123 456 789",
            "ul. aleja Ignacego Daszyńskiego 21-23, 31-537 Kraków",
            50.056376,
            19.952216,
            Status.ASSIGNED
        )
    )

    fun getInspectionRequest(number: String): InspectionRequest? {
        return store.find { it.number == number }?.copy()
    }

    fun getInspectionRequests(filter: InspectionRequestsFilter? = null): List<InspectionRequest> {
        val requests = store.map { it.copy() }
        return if (filter != null) {
            requests.filter {
                filter.statuses.isEmpty() || filter.statuses.contains(it.status)
            }
        } else {
            requests
        }
    }

    fun accept(number: String) {
        val newRequests = store.toMutableList()
        newRequests.find { it.number == number }!!.accept()
        store = newRequests
    }

    fun reject(number: String) {
        val newRequests = store.toMutableList()
        newRequests.find { it.number == number }!!.reject()
        store = newRequests
    }

    fun addNote(number: String, note: Note) {
        val newRequests = store.toMutableList()
        newRequests.find { it.number == number }!!.addNote(note)
        store = newRequests
    }

    fun addDocument(number: String, document: Document) {
        val newRequests = store.toMutableList()
        newRequests.find { it.number == number }!!.addDocument(document)
        store = newRequests
    }

    fun simulateBackendAdd(): InspectionRequest {
        val newInspectionRequest = InspectionRequest(
            Claim(
                "T00005",
                Date()
            ),
            "T00005/2019/M/ZO/005",
            "Marek",
            "Kowal",
            "+48 123 456 789",
            "ul. aleja Ignacego Daszyńskiego 21-23, 31-537 Kraków",
            50.056376,
            19.952216,
            Status.ASSIGNED
        )
        store.add(newInspectionRequest)
        return newInspectionRequest
    }
}
