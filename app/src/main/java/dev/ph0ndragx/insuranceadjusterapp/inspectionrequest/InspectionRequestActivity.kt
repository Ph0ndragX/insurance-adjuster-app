package dev.ph0ndragx.insuranceadjusterapp.inspectionrequest

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestBinding
import kotlinx.android.synthetic.main.activity_inspection_request.*

class InspectionRequestActivity: AppCompatActivity() {

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
        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager,
                fab
            )

        binding.viewPager.apply {
            adapter = sectionsPagerAdapter
            addOnPageChangeListener(sectionsPagerAdapter)
            sectionsPagerAdapter.onPageSelected(currentItem)
        }

        tabs.setupWithViewPager(binding.viewPager)
    }

    companion object {
        const val BUNDLE_ARG_INSPECTION_NUMBER = "dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionRequestActivity.INSPECTION_NUMBER"
    }
}
