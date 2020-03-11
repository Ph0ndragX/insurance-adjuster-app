package dev.ph0ndragx.insuranceadjusterapp.login

import androidx.lifecycle.ViewModel
import dev.ph0ndragx.insuranceadjusterapp.common.SecurityService
import io.reactivex.rxjava3.core.Observable

class LoginViewModel(private val service: SecurityService) : ViewModel() {

    fun loginUser(username: String, password: String): Observable<Any> {
        return Observable.fromCallable {
            service.login(username, password)
        }
    }
}