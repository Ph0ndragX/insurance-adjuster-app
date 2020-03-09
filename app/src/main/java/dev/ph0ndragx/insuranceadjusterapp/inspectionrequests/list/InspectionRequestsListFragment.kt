package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentInspectionRequestsListBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.InspectionsViewModel
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.map.InspectionRequestsMapFragment
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest

class InspectionRequestsListFragment : Fragment(), InspectionRequestsRecyclerViewAdapter.InspectionRequestOnClickHandlers {

    private val model: InspectionsViewModel by activityViewModels()

    private var _binding: FragmentInspectionRequestsListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewAdapter: InspectionRequestsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInspectionRequestsListBinding.inflate(inflater, container, false)

        viewAdapter = InspectionRequestsRecyclerViewAdapter(this)

        binding.fragmentInspectionRequestsListRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
        }

        model.inspectionRequests().observe(viewLifecycleOwner, Observer { requests ->
            viewAdapter.updateData(requests)
        })

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_inspection_requests_list_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.activity_inspection_requests_menu_action_map -> {
                parentFragmentManager.apply {
                    InspectionRequestsMapFragment.navigateTo(requireActivity().supportFragmentManager)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDetails(inspectionRequest: InspectionRequest) {
        Snackbar.make(requireView(), "Przejście na szczegóły zlecenia", Snackbar.LENGTH_LONG).show()
    }

    override fun onAccept(inspectionRequest: InspectionRequest) {
        model.accept(inspectionRequest.number)
    }

    override fun onReject(inspectionRequest: InspectionRequest) {
        model.reject(inspectionRequest.number)
    }

    companion object {
        private const val InspectionRequestsListFragment_FRAGMENT_ID = "InspectionRequestsListFragment_FRAGMENT_ID"

        fun navigateTo(fm: FragmentManager) {
            val transaction = fm.beginTransaction()
            val fragment = InspectionRequestsListFragment()
            transaction.replace(R.id.activity_inspection_requests_fragment_container, fragment, InspectionRequestsListFragment_FRAGMENT_ID)
            transaction.commit()
        }
    }
}