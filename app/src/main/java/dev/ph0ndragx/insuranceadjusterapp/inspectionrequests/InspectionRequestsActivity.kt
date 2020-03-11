package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestsBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.list.InspectionRequestsListFragment
import kotlinx.android.synthetic.main.activity_inspection_requests.*
import kotlinx.android.synthetic.main.activity_inspection_requests_front_layer.*

class InspectionRequestsActivity : AppCompatActivity() {

    private val model: InspectionsViewModel by viewModels { AppViewModelFactory.instance }

    private lateinit var binding: ActivityInspectionRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInspectionRequestsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewmodel = model
        setContentView(binding.activityInspectionRequestsRoot)
        setSupportActionBar(binding.appBar)

        model.loadInspectionRequests()

        initControls()

        if (savedInstanceState == null) {
            InspectionRequestsListFragment.navigateTo(supportFragmentManager)
        }
    }

    private fun initControls() {
        setSupportActionBar(app_bar)

        NavigationIconClickListener(
            this@InspectionRequestsActivity,
            requests_front_layer,
            requests_front_layer_filter,
            requests_front_layer_up_arrow,
            requests_front_layer_text
        )

        model.inspectionRequests().observe(this, Observer {
            requests_front_layer_text.text =
                resources.getString(R.string.activity_inspection_requests_number, it.size)
        })
    }
}
