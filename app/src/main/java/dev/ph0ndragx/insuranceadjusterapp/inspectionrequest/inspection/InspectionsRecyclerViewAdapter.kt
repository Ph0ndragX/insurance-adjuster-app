package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentInspectionListItemBinding
import dev.ph0ndragx.insuranceadjusterapp.model.inspection.Inspection
import java.text.DateFormat

class InspectionsRecyclerViewAdapter : RecyclerView.Adapter<InspectionsRecyclerViewAdapter.ViewHolder>() {

    private var inspections: List<Inspection> = emptyList()

    class ViewHolder(val binding: FragmentInspectionListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentInspectionListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val inspection = inspections[position]

        holder.binding.root.tag = inspection

        holder.binding.arrangedDate.text =
            if (inspection.arrangedDate == null ) "" else DateFormat.getDateInstance().format(inspection.arrangedDate)
        holder.binding.date.text =
            if (inspection.inspectionDate == null ) "" else DateFormat.getDateInstance().format(inspection.inspectionDate)
        holder.binding.description.text = inspection.description
    }

    override fun getItemCount() = inspections.size

    fun updateData(inspections: List<Inspection>) {
        this.inspections = inspections
        notifyDataSetChanged()
    }
}