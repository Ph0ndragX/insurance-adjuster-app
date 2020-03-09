package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentInspectionRequestsListItemBinding
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import java.text.DateFormat

class InspectionRequestsRecyclerViewAdapter(
    private val onClickHandlers: InspectionRequestOnClickHandlers
) : RecyclerView.Adapter<InspectionRequestsRecyclerViewAdapter.ViewHolder>() {

    private var inspectionRequests: List<InspectionRequest> = emptyList()

    interface InspectionRequestOnClickHandlers {
        fun onDetails(inspectionRequest: InspectionRequest)
        fun onAccept(inspectionRequest: InspectionRequest)
        fun onReject(inspectionRequest: InspectionRequest)
    }

    class ViewHolder(val binding: FragmentInspectionRequestsListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentInspectionRequestsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        binding.root.setOnClickListener {
            onClickHandlers.onDetails(binding.root.tag as InspectionRequest)
        }

        binding.accept.setOnClickListener {
            onClickHandlers.onAccept(binding.root.tag as InspectionRequest)
        }

        binding.reject.setOnClickListener {
            onClickHandlers.onReject(binding.root.tag as InspectionRequest)
        }

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inspectionRequest = inspectionRequests[position]

        holder.binding.root.tag = inspectionRequest

        holder.binding.number.text = inspectionRequest.number
        holder.binding.name.text = holder.binding.name.resources.getString(
            R.string.inspection_request_List_item_card_name,
            inspectionRequest.firstName,
            inspectionRequest.lastName
        )
        holder.binding.phoneNumber.text = inspectionRequest.phoneNumber
        holder.binding.address.text = inspectionRequest.address
        holder.binding.status.text = holder.binding.status.resources.getString(inspectionRequest.status.stringId)
        holder.binding.appointment.text = if(inspectionRequest.appointment == null) {
            holder.binding.appointment.resources.getString(R.string.inspection_request_not_appointed)
        } else {
            DateFormat.getDateInstance().format(inspectionRequest.appointment)
        }

        holder.binding.accept.visibility = if (inspectionRequest.canBeAccepted()) View.VISIBLE else View.GONE
        holder.binding.reject.visibility = if (inspectionRequest.canBeRejected()) View.VISIBLE else View.GONE
    }

    override fun getItemCount() = inspectionRequests.size

    fun updateData(inspectionRequests: List<InspectionRequest>) {
        this.inspectionRequests = inspectionRequests
        notifyDataSetChanged()
    }
}