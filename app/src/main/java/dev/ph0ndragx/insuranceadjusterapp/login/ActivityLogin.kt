package dev.ph0ndragx.insuranceadjusterapp.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dev.ph0ndragx.insuranceadjusterapp.common.AppViewModelFactory
import dev.ph0ndragx.insuranceadjusterapp.databinding.ActivityLoginBinding
import dev.ph0ndragx.insuranceadjusterapp.inspectionrequests.InspectionRequestsActivity

class ActivityLogin: AppCompatActivity()  {

    private val model: LoginViewModel by viewModels { AppViewModelFactory.instance }

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.loginButton.setOnClickListener {
            model.loginUser(binding.login.text.toString(), binding.password.text.toString()).subscribe {
                startActivity(Intent(this, InspectionRequestsActivity::class.java))
            }
        }
    }
}