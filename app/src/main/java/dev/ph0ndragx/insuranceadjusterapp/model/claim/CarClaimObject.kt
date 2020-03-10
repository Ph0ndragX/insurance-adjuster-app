package dev.ph0ndragx.insuranceadjusterapp.model.claim

class CarClaimObject(
    val registrationNumber: String,
    val vin: String,
    val make: String,
    val model: String
) : ClaimObject()
