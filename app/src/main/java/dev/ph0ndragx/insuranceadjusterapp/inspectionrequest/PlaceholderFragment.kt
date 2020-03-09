package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dev.ph0ndragx.insuranceadjusterapp.databinding.FragmentPlaceholderBinding

class PlaceholderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentPlaceholderBinding.inflate(inflater, container, false).root
    }
}