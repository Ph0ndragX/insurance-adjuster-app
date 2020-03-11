package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.note


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentNoteListItemBinding
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import dev.ph0ndragx.insuranceadjusterapp.model.Note
import java.text.DateFormat

class NotesRecyclerViewAdapter : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    private var notes: List<Note> = emptyList()

    class ViewHolder(val binding: FragmentNoteListItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = FragmentNoteListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = notes[position]

        holder.apply {
            binding.root.tag = item
            binding.author.text = item.author
            binding.date.text = DateFormat.getDateInstance().format(item.date)
            binding.content.text = item.content
        }
    }

    override fun getItemCount(): Int = notes.size

    fun updateData(inspectionRequest: InspectionRequest) {
        this.notes = inspectionRequest.notes
        notifyDataSetChanged()
    }
}
