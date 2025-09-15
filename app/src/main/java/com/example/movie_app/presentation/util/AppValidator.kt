package com.example.movie_app.presentation.util

class  AppValidator
{

    companion object
    {
        fun validateEmail(email: String): String? {
            return if (email.isEmpty()) {
                "Email cannot be empty"
            } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                "Invalid email format"
            } else {
                null // No error
            }
        }

        fun validatePassword(password: String): String? {
            return if (password.isEmpty()) {
                "Password cannot be empty"
            } else if (password.length < 6) {
                "Password must be at least 6 characters"
            } else {
                null // No error
            }
        }
    }

}