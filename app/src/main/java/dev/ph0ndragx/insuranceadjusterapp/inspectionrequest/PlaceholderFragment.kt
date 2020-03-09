package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentPlaceholderBinding
import kotlinx.android.synthetic.main.activity_inspection_request.*

class PlaceholderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentPlaceholderBinding.inflate(inflater, container, false).root
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