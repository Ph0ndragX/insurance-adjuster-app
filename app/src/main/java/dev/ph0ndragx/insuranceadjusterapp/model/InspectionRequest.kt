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
    var appointment: Date? = null
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
}
