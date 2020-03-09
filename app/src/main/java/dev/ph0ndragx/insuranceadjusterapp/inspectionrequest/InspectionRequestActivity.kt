package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayoutMediator
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestBinding

class InspectionRequestActivity: AppCompatActivity() {

    interface FabFragment {
        fun shareFab(fab: FloatingActionButton)
    }

    private val model: InspectionViewModel by viewModels { AppViewModelFactory.instance }

    private lateinit var binding: ActivityInspectionRequestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInspectionRequestBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.activityInspectionAppBar)

        initTabs()

        model.inspectionRequest().observe(this, Observer {
            supportActionBar?.title = it.number
        })

        val inspectionNumber = intent?.extras?.getString(BUNDLE_ARG_INSPECTION_NUMBER)!!
        model.loadInspectionRequest(inspectionNumber)
    }

    private fun initTabs() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)

        binding.viewPager.apply {
            adapter = sectionsPagerAdapter
        }

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = binding.root.resources.getString(sectionsPagerAdapter.getTitle(position))
        }.attach()
    }

    companion object {
        const val BUNDLE_ARG_INSPECTION_NUMBER = "dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionRequestActivity.INSPECTION_NUMBER"
    }
}
