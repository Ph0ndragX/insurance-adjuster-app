package dev.ph0ndragx.insuranceadjusterapp.common

import dev.ph0ndragx.insuranceadjusterapp.model.Status

data class InspectionRequestsFilter(val statuses: Set<Status> = HashSet()) {

    fun withStatus(status: Status) =
        InspectionRequestsFilter(
            statuses.plus(status)
        )
    fun withoutStatus(status: Status) =
        InspectionRequestsFilter(
            statuses.minus(status)
        )
}
