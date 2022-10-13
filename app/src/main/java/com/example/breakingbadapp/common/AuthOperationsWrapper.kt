package com.example.breakingbadapp.common

import android.app.Activity
import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthOperationsWrapper @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    onSuccess()
                }
            }.addOnFailureListener {
                onFailure(it.message.orEmpty())
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    onSuccess()
                }
            }.addOnFailureListener {
                onFailure(it.message.orEmpty())
            }
    }

    private fun signInWithCredential(
        credential: PhoneAuthCredential,
        onSuccess: () -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        firebaseAuth.signInWithCredential(credential).addOnSuccessListener { authResult ->
            authResult.user?.let {
                onSuccess()
            }
        }.addOnFailureListener {
            onFailure(it.message.orEmpty())
        }
    }







}