package dev.ph0ndragx.insuranceadjusterapp.model

import com.google.android.gms.maps.model.LatLng
import java.util.*

class InspectionRequest(
    var number: String,
    var firstName: String,
    var lastName: String,
    var phoneNumber: String,
    var address: String,
    var lat: Double,
    var lng: Double,
    var status: Status,
    var appointment: Date? = null,
    val notes: MutableList<Note> = mutableListOf()
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

    fun copy(): InspectionRequest {
        return InspectionRequest(
            number,
            firstName,
            lastName,
            phoneNumber,
            address,
            lat,
            lng,
            status,
            appointment,
            notes.map { it.copy() }.toMutableList()
        )
    }

    fun addNote(note: Note) {
        notes.add(note.copy())
    }
}
