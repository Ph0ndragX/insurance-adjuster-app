package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.document

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentDocumentListItemBinding
import dev.ph0ndragx.insuranceadjusterapp.model.Document
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import java.text.DateFormat

class DocumentsRecyclerViewAdapter : RecyclerView.Adapter<DocumentsRecyclerViewAdapter.ViewHolder>() {

    private var documents: List<Document> = ArrayList()

    class ViewHolder(val binding: FragmentDocumentListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentDocumentListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = documents[position]

        holder.apply {
            binding.root.tag = item
            binding.author.text = item.author
            binding.filename.text = item.fileName
            binding.date.text = DateFormat.getDateInstance().format(item.date)
            binding.download.apply {
                setOnClickListener {
                    Snackbar.make(binding.root, binding.root.resources.getString(R.string.document_downloading), Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun getItemCount(): Int = documents.size

    fun updateData(inspectionRequest: InspectionRequest) {
        this.documents = inspectionRequest.documents
        notifyDataSetChanged()
    }
}
