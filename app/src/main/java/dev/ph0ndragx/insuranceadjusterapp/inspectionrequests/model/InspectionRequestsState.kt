package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.model

import dev.ph0ndragx.insuranceadjusterapp.common.InspectionRequestsFilter
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest

data class InspectionRequestsState(
    val inspectionRequests: List<InspectionRequest> = ArrayList(),
    val filter: InspectionRequestsFilter = InspectionRequestsFilter()
)
