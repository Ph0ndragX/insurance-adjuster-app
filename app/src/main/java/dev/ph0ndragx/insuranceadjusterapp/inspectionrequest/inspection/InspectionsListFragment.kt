package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentInspectionListBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionRequestActivity
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel
import kotlinx.android.synthetic.main.activity_inspection_request.*

class InspectionsListFragment : Fragment() {

    private val model: InspectionViewModel by activityViewModels { AppViewModelFactory.instance}

    private var _binding: FragmentInspectionListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewAdapter: InspectionsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInspectionListBinding.inflate(inflater, container, false)

        viewAdapter = InspectionsRecyclerViewAdapter()

        with(binding.root as RecyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
        }

        model.inspections().observe(viewLifecycleOwner, Observer { requests ->
            viewAdapter.updateData(requests)
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
            hide()
        }
    }
}
