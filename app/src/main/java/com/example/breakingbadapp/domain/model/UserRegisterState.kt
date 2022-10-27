package com.example.breakingbadapp.domain.model

import androidx.activity.result.IntentSenderRequest
import com.google.firebase.auth.FirebaseUser

data class UserRegisterState(
    val success : FirebaseUser? = null,
    val error: String? = null
)

data class UserRegisterStateGoogle(
    val success : IntentSenderRequest? = null,
    val error: String? = null
)
