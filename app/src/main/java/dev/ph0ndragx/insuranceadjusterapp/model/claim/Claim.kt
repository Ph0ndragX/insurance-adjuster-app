package dev.ph0ndragx.insuranceadjusterapp.model.claim

import java.util.*

data class Claim(
    val number: String,
    val registrationDate: Date,
    val objects: List<ClaimObject>
) {
    fun copy(): Claim {
        return Claim(
            number,
            registrationDate,
            objects.toList()
        )
    }
}
