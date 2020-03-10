package dev.ph0ndragx.insuranceadjusterapp.model.inspection

import java.util.*

data class Inspection(
    val sequence: Int,
    val arrangedDate: Date?,
    val inspectionDate: Date?,
    val description: String
)