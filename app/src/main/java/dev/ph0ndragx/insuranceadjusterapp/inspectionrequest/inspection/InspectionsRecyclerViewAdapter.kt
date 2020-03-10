package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestFragmentInspectionListItemBinding
import dev.ph0ndragx.insuranceadjusterapp.model.inspection.Inspection
import java.text.DateFormat

class InspectionsRecyclerViewAdapter : RecyclerView.Adapter<InspectionsRecyclerViewAdapter.ViewHolder>() {

    private var inspections: List<Inspection> = emptyList()

    class ViewHolder(val binding: ActivityInspectionRequestFragmentInspectionListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ActivityInspectionRequestFragmentInspectionListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inspection = inspections[position]

        holder.binding.root.tag = inspection

        holder.binding.activityInspectionRequestFragmentInspectionListSequence.text = inspection.sequence.toString()
        holder.binding.activityInspectionRequestFragmentInspectionListArrangedDate.text =
            if (inspection.arrangedDate == null ) "" else DateFormat.getDateInstance().format(inspection.arrangedDate)
        holder.binding.activityInspectionRequestFragmentInspectionListInspectionDate.text =
            if (inspection.inspectionDate == null ) "" else DateFormat.getDateInstance().format(inspection.inspectionDate)
        holder.binding.activityInspectionRequestFragmentInspectionListInspectionDescription.text = inspection.description
    }

    override fun getItemCount() = inspections.size

    fun updateData(inspections: List<Inspection>) {
        this.inspections = inspections
        notifyDataSetChanged()
    }
}