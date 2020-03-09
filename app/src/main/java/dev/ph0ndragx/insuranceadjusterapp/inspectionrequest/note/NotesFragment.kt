package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestFragmentNoteListBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionRequestActivity
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel
import kotlinx.android.synthetic.main.activity_inspection_request.*

class NotesFragment : Fragment() {

    private val model: InspectionViewModel by activityViewModels { AppViewModelFactory.instance }

    private var _binding: ActivityInspectionRequestFragmentNoteListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: NotesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityInspectionRequestFragmentNoteListBinding.inflate(inflater, container, false)

        recyclerView = NotesRecyclerViewAdapter()

        with(binding.root as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerView
        }

        model.inspectionRequest().observe(viewLifecycleOwner, Observer {
            recyclerView.updateData(it)
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        val activity = requireActivity() as InspectionRequestActivity
        shareFab(activity.fab)
    }

    private fun shareFab(fab: FloatingActionButton) {
        fab.apply {
            show()
            setOnClickListener {
                val manager: FragmentManager? = parentFragmentManager
                if (manager != null) {
                    NewNoteDialogFragment.display(manager)
                }
            }
        }
    }
}
