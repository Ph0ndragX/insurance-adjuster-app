package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspectionrequest

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestFragmentRequestClaimObjectsListCarItemBinding
import dev.ph0ndragx.insuranceadjusterapp.model.claim.CarClaimObject
import dev.ph0ndragx.insuranceadjusterapp.model.claim.ClaimObject

class InspectionRequestClaimObjectsRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var claimObjects: List<ClaimObject> = emptyList()

    class CarViewHolder(val binding: ActivityInspectionRequestFragmentRequestClaimObjectsListCarItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            CAR_VIEW_TYPE -> {
                val binding = ActivityInspectionRequestFragmentRequestClaimObjectsListCarItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                CarViewHolder(binding)
            }
            else -> null
        }!!
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            CAR_VIEW_TYPE -> {
                val viewHolder = holder as CarViewHolder
                val claimObject = claimObjects[position] as CarClaimObject

                viewHolder.binding.carRegistrationNumber.text = claimObject.registrationNumber
                viewHolder.binding.carVin.text = claimObject.vin
                viewHolder.binding.carMake.text = claimObject.make
                viewHolder.binding.carModel.text = claimObject.model
            }
        }
    }

    override fun getItemCount() = claimObjects.size

    override fun getItemViewType(position: Int): Int {
        return when (claimObjects[position]) {
            is CarClaimObject -> CAR_VIEW_TYPE
            else -> super.getItemViewType(position)
        }
    }

    fun updateData(claimObjects: List<ClaimObject>) {
        this.claimObjects = claimObjects
        notifyDataSetChanged()
    }

    companion object {
        const val CAR_VIEW_TYPE = 1
    }
}