package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestsBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.list.InspectionRequestsListFragment

class InspectionRequestsActivity : AppCompatActivity() {

    private val model: InspectionsViewModel by viewModels { AppViewModelFactory.instance }

    private lateinit var binding: ActivityInspectionRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInspectionRequestsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.appBar)

        model.loadInspectionRequests()

        if (savedInstanceState == null) {
            InspectionRequestsListFragment.navigateTo(supportFragmentManager)
        }
    }
}
