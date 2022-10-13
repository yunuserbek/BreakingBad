package com.example.breakingbadapp.viewmodel

import android.app.Activity
import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class AuthViewModel@Inject constructor(private val firebaseAuth: FirebaseAuth) :ViewModel(){
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

    fun signInWithGithub(
        activity: Activity, onSuccess: () -> Unit = {}, onFailure: (String) -> Unit = {}
    ) {
        val provider = OAuthProvider.newBuilder("github.com")
        provider.addCustomParameter("login", "")

        firebaseAuth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    onSuccess()
                }
            }.addOnFailureListener {
                onFailure(it.message.orEmpty())
            }
    }
    fun signInWithTwitter(
        activity: Activity, onSuccess: () -> Unit, onFailure: (String) -> Unit
    ) {
        val provider = OAuthProvider.newBuilder("twitter.com")
        provider.addCustomParameter("lang", "")

        firebaseAuth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    onSuccess()
                }
            }.addOnFailureListener {
                onFailure(it.message.orEmpty())
            }
    }

    fun signInWithGoogle(
        activity: Activity,
        oneTapClient: SignInClient,
        onSuccess: (IntentSenderRequest) -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {

        val signInRequest = BeginSignInRequest.builder().setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId("327723893396-027qt4jp54l7o014efednnc1avn7gtmj.apps.googleusercontent.com")
                .setFilterByAuthorizedAccounts(false).build()
        ).build()

        oneTapClient.beginSignIn(signInRequest).addOnSuccessListener(activity) { result ->
            try {
                val intentSenderRequest =
                    IntentSenderRequest.Builder(result.pendingIntent.intentSender).build()
                onSuccess(intentSenderRequest)
            } catch (e: IntentSender.SendIntentException) {
                onFailure("Couldn't start One Tap UI: ${e.message}")
            }
        }.addOnFailureListener {
            onFailure(it.message.orEmpty())
        }
    }

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




    fun checkCurrentUser(currentUser: (FirebaseUser) -> Unit = {}) {
        firebaseAuth.currentUser?.let {
            currentUser(it)
        }
    }
}
