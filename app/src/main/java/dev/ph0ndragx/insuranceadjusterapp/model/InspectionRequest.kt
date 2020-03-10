package dev.ph0ndragx.insuranceadjusterapp.model

import com.google.android.gms.maps.model.LatLng
import dev.ph0ndragx.insuranceadjusterapp.model.claim.Claim
import dev.ph0ndragx.insuranceadjusterapp.model.inspection.Inspection
import java.util.*

data class InspectionRequest(
    var claim: Claim,
    var number: String,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var address: String,
    var lat: Double,
    var lng: Double,
    var status: Status,
    val notes: MutableList<Note> = mutableListOf(),
    val documents: MutableList<Document> = mutableListOf(),
    val inspections: MutableList<Inspection> = mutableListOf()
) {
    fun canBeRejected() = status == Status.ASSIGNED

    fun canBeAccepted() = status == Status.ASSIGNED

    fun accept() {
        status = Status.ACCEPTED
    }

    fun reject() {
        status = Status.REJECTED
    }

    fun position() = LatLng(lat, lng)

    fun appointmentDate(): Date? {
        return if (inspections.isEmpty()) {
            null
        } else {
            inspections.sortBy { it.sequence }
            inspections[0].arrangedDate
        }
    }

    fun copy(): InspectionRequest {
        return InspectionRequest(
            claim.copy(),
            number,
            firstName,
            lastName,
            phoneNumber,
            address,
            lat,
            lng,
            status,
            notes.map { it.copy() }.toMutableList(),
            documents.map { it.copy() }.toMutableList(),
            inspections.map { it.copy() }.toMutableList()
        )
    }

    fun addNote(note: Note) {
        notes.add(note.copy())
    }

    fun addDocument(document: Document) {
        documents.add(document.copy())
    }
}
