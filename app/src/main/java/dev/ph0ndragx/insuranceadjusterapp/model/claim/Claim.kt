package dev.ph0ndragx.insuranceadjusterapp.model.claim

import java.util.*

data class Claim(
    val number: String,
    val registrationDate: Date
) {
    fun copy(): Claim {
        return Claim(
            number,
            registrationDate
        )
    }
}
