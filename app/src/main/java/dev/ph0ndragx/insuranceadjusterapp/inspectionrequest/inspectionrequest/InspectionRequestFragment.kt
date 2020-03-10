package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspectionrequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestFragmentRequestBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionRequestActivity
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel
import kotlinx.android.synthetic.main.activity_inspection_request.*
import java.text.DateFormat

class InspectionRequestFragment : Fragment() {

    private val model: InspectionViewModel by activityViewModels { AppViewModelFactory.instance }

    private var _binding: ActivityInspectionRequestFragmentRequestBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewAdapter: InspectionRequestClaimObjectsRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityInspectionRequestFragmentRequestBinding.inflate(inflater, container, false)

        viewAdapter = InspectionRequestClaimObjectsRecyclerViewAdapter()

        binding.activityInspectionRequestFragmentRequestObjectsRecyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = viewAdapter
        }

        model.inspectionRequest().observe(viewLifecycleOwner, Observer { request ->
            binding.root.tag = request
            binding.activityInspectionRequestFragmentRequestNumber.text = request.number
            binding.activityInspectionRequestFragmentRequestName.text = binding.root.resources.getString(
                R.string.inspection_request_List_item_card_name,
                request.firstName,
                request.lastName
            )
            binding.activityInspectionRequestFragmentRequestPhoneNumber.text = request.phoneNumber
            binding.activityInspectionRequestFragmentRequestAddress.text = request.address
            binding.activityInspectionRequestFragmentRequestStatus.text = binding.root.resources.getString(request.status.stringId)

            binding.activityInspectionRequestFragmentRequestClaimNumber.text = request.claim.number
            binding.activityInspectionRequestFragmentRequestClaimRegistrationDate.text = DateFormat.getDateInstance().format(request.claim.registrationDate)

            viewAdapter.updateData(request.claim.objects)
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
