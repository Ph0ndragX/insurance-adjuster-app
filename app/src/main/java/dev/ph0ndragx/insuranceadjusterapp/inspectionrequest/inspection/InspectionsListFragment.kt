package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestFragmentInspectionListBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel

class InspectionsListFragment : Fragment() {

    private val model: InspectionViewModel by activityViewModels { AppViewModelFactory.instance}

    private var _binding: ActivityInspectionRequestFragmentInspectionListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewAdapter: InspectionsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityInspectionRequestFragmentInspectionListBinding.inflate(inflater, container, false)

        viewAdapter = InspectionsRecyclerViewAdapter()

        binding.activityInspectionRequestFragmentInspectionListRecyclerView.apply {
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

}
