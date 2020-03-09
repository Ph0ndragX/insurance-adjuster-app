package dev.ph0ndragx.insuranceadjusterapp.model

import dev.ph0ndragx.insuranceadjusterapp.R

enum class Status(val stringId: Int) {
    ASSIGNED(R.string.inspection_request_status_assigned),
    REJECTED(R.string.inspection_request_status_rejected),
    ACCEPTED(R.string.inspection_request_status_accepted)
}
