package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.map

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentInspectionRequestsMapBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.InspectionsViewModel
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.list.InspectionRequestsListFragment

class InspectionRequestsMapFragment : Fragment(), OnMapReadyCallback {

    private val model: InspectionsViewModel by activityViewModels()

    private var _binding: FragmentInspectionRequestsMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var locationProvider: FusedLocationProviderClient
    private var map: GoogleMap? = null
    private var selectedMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        locationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInspectionRequestsMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment: SupportMapFragment = SupportMapFragment.newInstance();
        parentFragmentManager.beginTransaction().add(R.id.fragment_inspection_requests_map_fragment, mapFragment).commit()
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.activity_inspection_requests_map_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.activity_inspection_requests_menu_action_list -> {
                parentFragmentManager.apply {
                    InspectionRequestsListFragment.navigateTo(requireActivity().supportFragmentManager)
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun checkPermissions(): Boolean {
        val askPermissions =
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED

        if (askPermissions) {
            requestPermissions(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_WIFI_STATE),
                LOCATION_PERMISSION_GRANTED
            )
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode) {
            LOCATION_PERMISSION_GRANTED -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    addUserLocationMarker()
                }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        map = p0
        map?.uiSettings?.isMapToolbarEnabled = false
        addMarkers()
    }

    private fun addMarkers() {
        map?.clear()
        addUserLocationMarker()
        addInspectionRequestsMarker()
    }

    private fun addInspectionRequestsMarker() {
        model.inspectionRequests().observe(this, Observer { requests ->
            map?.apply {
                for (inspectionRequest in requests) {
                    addMarker(
                        MarkerOptions()
                            .position(inspectionRequest.position())
                            .title(inspectionRequest.number)
                    ).tag = inspectionRequest
                }
            }
        })
    }

    private fun addUserLocationMarker() {
        if (!checkPermissions()) {
            return
        }

        try {
            locationProvider.lastLocation.apply {
                addOnSuccessListener {
                    if (it != null) {
                        val pos = LatLng(it.latitude, it.longitude)

                        val circleDrawable = resources.getDrawable(R.drawable.ic_home_black_24dp, context?.theme)
                        val icon = getMarkerIconFromDrawable(circleDrawable)

                        map?.moveCamera(CameraUpdateFactory.newLatLngZoom(pos, DEFAULT_ZOOM))
                        map?.addMarker(MarkerOptions().position(pos).icon(icon))
                    }
                }
            }
        } catch (exception: SecurityException) {
            Log.e("PERMISSION", "failed", exception)
        }
    }

    private fun getMarkerIconFromDrawable(drawable: Drawable): BitmapDescriptor {
        val canvas = Canvas()
        val bitmap: Bitmap = Bitmap.createBitmap(drawable.intrinsicWidth, drawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }

    companion object {
        private const val DEFAULT_ZOOM: Float = 13f
        private const val LOCATION_PERMISSION_GRANTED = 1
        private const val InspectionRequestsMapFragment_FRAGMENT_ID = "InspectionRequestsMapFragment_FRAGMENT_ID"

        fun navigateTo(fm: FragmentManager) {
            val transaction = fm.beginTransaction()
            val fragment = InspectionRequestsMapFragment()
            transaction.replace(R.id.activity_inspection_requests_fragment_container, fragment, InspectionRequestsMapFragment_FRAGMENT_ID)
            transaction.commit()
        }
    }
}
