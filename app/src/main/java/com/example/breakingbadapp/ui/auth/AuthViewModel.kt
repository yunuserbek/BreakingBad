package com.example.breakingbadapp.ui.auth

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.breakingbadapp.common.wrappers.AuthOperationsWrapper
import com.example.breakingbadapp.domain.model.UserRegisterState
import com.example.breakingbadapp.domain.model.UserRegisterStateGoogle
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authOperationsWrapper: AuthOperationsWrapper
) : ViewModel() {

    private val _userRegisterState = MutableLiveData<UserRegisterState>()
    val userRegisterState: LiveData<UserRegisterState> = _userRegisterState

    private val _userRegisterStateOnGoogle = MutableLiveData<UserRegisterStateGoogle>()
    val userRegisterStateOnGoogle: LiveData<UserRegisterStateGoogle> = _userRegisterStateOnGoogle

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
    ) {
        authOperationsWrapper.signInWithEmailAndPassword(email, password,
            onSuccess = { user ->
                _userRegisterState.postValue(
                    UserRegisterState(success = user)
                )
            },
            onFailure = { exception ->
                _userRegisterState.postValue(
                    UserRegisterState(error = exception)
                )
            }
        )
    }

    fun signUpWithEmailAndPassword(
        email: String,
        password: String,
    ) {
        authOperationsWrapper.signUpWithEmailAndPassword(email, password,
            onSuccess = { user ->
                // onSuccess
                _userRegisterState.postValue(
                    UserRegisterState(success = user)
                )
            },
            onFailure = { exception ->
                _userRegisterState.postValue(
                    UserRegisterState(error = exception)
                )
            }
        )
    }

    fun continueWithGitHub(
        activity: Activity,
    ) {
        authOperationsWrapper.signInWithGithub(activity,
            onSuccess = { user ->
                // onSuccess
                _userRegisterState.postValue(
                    UserRegisterState(success = user)
                )
            },
            onFailure = { exception ->
                _userRegisterState.postValue(
                    UserRegisterState(error = exception)
                )
            }
        )
    }

    fun continueWithTwitter(
        activity: Activity,
    ) {
        authOperationsWrapper.signInWithTwitter(activity,
            onSuccess = { user ->
                // onSuccess
                _userRegisterState.postValue(
                    UserRegisterState(success = user)
                )
            },
            onFailure = { exception ->
                _userRegisterState.postValue(
                    UserRegisterState(error = exception)
                )
            }
        )
    }


    fun continueWithGoogle(
        activity: Activity,
        oneTapClient: SignInClient,
    ) {
        authOperationsWrapper.signInWithGoogle(activity, oneTapClient,
            onSuccess = { intentSenderRequest ->
                // onSuccess
                _userRegisterStateOnGoogle.postValue(
                    UserRegisterStateGoogle(success = intentSenderRequest)
                )
            },
            onFailure = { exception ->
                _userRegisterStateOnGoogle.postValue(
                    UserRegisterStateGoogle(error = exception)
                )
            })
    }

    fun checkCurrentUser() : Boolean = authOperationsWrapper.checkCurrentUser()

}
