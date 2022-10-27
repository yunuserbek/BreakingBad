package com.example.breakingbadapp.common.wrappers

import android.app.Activity
import android.content.IntentSender
import androidx.activity.result.IntentSenderRequest
import com.example.breakingbadapp.R
import com.example.breakingbadapp.common.util.ResourcesProvider
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.OAuthProvider
import javax.inject.Inject

class AuthOperationsWrapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val firebaseAuth: FirebaseAuth
    ) {
    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    onSuccess(it)
                }
            }.addOnFailureListener {
                onFailure(it.message.orEmpty())
            }
    }

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        onSuccess: (FirebaseUser) -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    onSuccess(it)
                }
            }.addOnFailureListener {
                onFailure(it.message.orEmpty())
            }
    }

    fun signInWithGithub(
        activity: Activity,
        onSuccess: (FirebaseUser) -> Unit = {},
        onFailure: (String) -> Unit = {}
    ) {
        val provider = OAuthProvider.newBuilder("github.com")
        provider.addCustomParameter("login", "")

        firebaseAuth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    onSuccess(it)
                }
            }.addOnFailureListener {
                onFailure(it.message.orEmpty())
            }
    }

    fun signInWithTwitter(
        activity: Activity,
        onSuccess: (FirebaseUser) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val provider = OAuthProvider.newBuilder("twitter.com")
        provider.addCustomParameter("lang", "")

        firebaseAuth.startActivityForSignInWithProvider(activity, provider.build())
            .addOnSuccessListener { authResult ->
                authResult.user?.let {
                    onSuccess(it)
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
                .setServerClientId(resourcesProvider.getString(R.string.default_web_client_id))
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

    fun checkCurrentUser() : Boolean =
        firebaseAuth.currentUser != null
}
