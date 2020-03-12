package dev.ph0ndragx.insuranceadjusterapp.inspectionrequests

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.Observer
import dev.ph0ndragx.insuranceadjusterapp.R
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityInspectionRequestsBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequest.InspectionRequestActivity
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.list.InspectionRequestsListFragment
import dev.ph0ndragx.insuranceadjusterapp.model.InspectionRequest
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

        if (savedInstanceState == null && resources.configuration.smallestScreenWidthDp < 600) {
            InspectionRequestsListFragment.navigateTo(supportFragmentManager)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.activity_inspection_requests_menu_action_simulate_add -> {
                createNotificationChannel()

                val inspection: InspectionRequest = model.simulateBackendAdd()

                val resultIntent = Intent(this, InspectionRequestActivity::class.java)
                resultIntent.putExtra(InspectionRequestActivity.BUNDLE_ARG_INSPECTION_NUMBER, inspection.number)

                val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(this).run {
                    addNextIntentWithParentStack(resultIntent)
                    getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
                }

                val builder = NotificationCompat.Builder(this, applicationContext.getString(R.string.notification_new_inspection_request_channel_id))
                    .setSmallIcon(R.drawable.ic_notifications_white_24dp)
                    .setContentTitle(applicationContext.getString(R.string.notification_new_inspection_request_title))
                    .setContentText(applicationContext.getString(R.string.notification_new_inspection_request_text, inspection.number))
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(resultPendingIntent)
                    .setAutoCancel(true)

                with(NotificationManagerCompat.from(this)) {
                    notify(SystemClock.uptimeMillis().toInt(), builder.build())
                }

                true
            }
            else -> super.onOptionsItemSelected(item)
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = applicationContext.getString(R.string.notification_new_inspection_request_channel_desc)
            val descriptionText = applicationContext.getString(R.string.notification_new_inspection_request_channel_desc)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(applicationContext.getString(R.string.notification_new_inspection_request_channel_id), name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
