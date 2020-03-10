package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import dev.ph0ndragx.insuranceadjusterapp.model.Status

class InspectionsFilterViewModel(private val filterChangedListener: FilterChangedListener) : ViewModel() {

    interface FilterChangedListener {
        fun onStatusSelected(status: Status)
        fun onStatusDeselected(status: Status)
    }

    val assignedStatusCheckbox: ObservableBoolean = ObservableBoolean(false)
    val rejectedStatusCheckbox: ObservableBoolean = ObservableBoolean(false)
    val acceptedStatusCheckbox: ObservableBoolean = ObservableBoolean(false)

    init {
        assignedStatusCheckbox.apply {
            addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (assignedStatusCheckbox.get()) {
                        filterChangedListener.onStatusSelected(Status.ASSIGNED)
                    } else {
                        filterChangedListener.onStatusDeselected(Status.ASSIGNED)
                    }
                }
            })
        }

        rejectedStatusCheckbox.apply {
            addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (rejectedStatusCheckbox.get()) {
                        filterChangedListener.onStatusSelected(Status.REJECTED)
                    } else {
                        filterChangedListener.onStatusDeselected(Status.REJECTED)
                    }
                }
            })
        }

        acceptedStatusCheckbox.apply {
            addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    if (acceptedStatusCheckbox.get()) {
                        filterChangedListener.onStatusSelected(Status.ACCEPTED)
                    } else {
                        filterChangedListener.onStatusDeselected(Status.ACCEPTED)
                    }
                }
            })
        }
    }
}
