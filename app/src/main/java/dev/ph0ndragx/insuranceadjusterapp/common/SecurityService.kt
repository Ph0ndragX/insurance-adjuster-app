package dev.ph0ndragx.insuranceadjusterapp.common

class SecurityService {
    private var username: String = ""

    fun login(username: String, password: String) {
        this.username = username
    }

    fun login(): String {
        return username
    }
}