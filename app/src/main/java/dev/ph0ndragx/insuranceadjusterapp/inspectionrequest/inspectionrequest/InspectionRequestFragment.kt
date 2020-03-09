package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.inspectionrequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestFragmentRequestBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionRequestActivity
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionViewModel
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
import kotlinx.android.synthetic.main.activity_inspection_request.*
import java.text.DateFormat

class InspectionRequestFragment : Fragment(), OnMapReadyCallback {

    private val model: InspectionViewModel by activityViewModels { AppViewModelFactory.instance }

    private var _binding: ActivityInspectionRequestFragmentRequestBinding? = null
    private val binding get() = _binding!!

    private var map: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityInspectionRequestFragmentRequestBinding.inflate(inflater, container, false)

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
            binding.activityInspectionRequestFragmentRequestAppointment.text = if(request.appointment == null) {
                binding.root.resources.getString(R.string.inspection_request_not_appointed)
            } else {
                DateFormat.getDateInstance().format(request.appointment)
            }

            placeMarkerOnMap(request)
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment: SupportMapFragment = SupportMapFragment.newInstance();
        parentFragmentManager.beginTransaction().add(R.id.activity_inspection_request_fragment_request_map, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        map?.uiSettings?.isMapToolbarEnabled = false
        placeMarkerOnMap(binding.root.tag as InspectionRequest)
    }

    private fun placeMarkerOnMap(inspectionRequest: InspectionRequest) {
        map?.clear()
        map?.apply {
            addMarker(
                MarkerOptions()
                    .position(inspectionRequest.position())
                    .title(inspectionRequest.number)
            ).tag = inspectionRequest
        }
        map?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                inspectionRequest.position(),
                DEFAULT_ZOOM
            )
        )
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

    companion object {
        private const val DEFAULT_ZOOM: Float = 13f
    }
}
