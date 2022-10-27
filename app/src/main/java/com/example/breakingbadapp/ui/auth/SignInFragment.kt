package com.example.breakingbadapp.ui.auth

import android.app.Activity
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentSigninBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SignInFragment : Fragment() {
    private lateinit var binding: FragmentSigninBinding
    private lateinit var oneTapClient: SignInClient

    @Inject
    lateinit var firebaseAuth: FirebaseAuth
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSigninBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        oneTapClient = Identity.getSignInClient(requireActivity())
        with(binding) {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (Patterns.EMAIL_ADDRESS.matcher(email)
                        .matches() && email.isNotEmpty() && password.isNotEmpty()
                ) {
                    viewModel.signInWithEmailAndPassword(email, password)
                }
            }

            btnGithub.setOnClickListener {
                viewModel.continueWithGitHub(requireActivity())
            }

            btnTwitter.setOnClickListener {
                viewModel.continueWithTwitter(requireActivity())
            }

            btnGoogle.setOnClickListener {
                viewModel.continueWithGoogle(requireActivity(), oneTapClient)
            }
        }
        observeStates()
    }


    private fun observeStates() {
        viewModel.userRegisterState.observe(viewLifecycleOwner) { result ->
            result.success?.let {
                Snackbar.make(requireView(), "Welcome ${it.email}", Snackbar.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_authFragment_to_homeFragment)
            }
            result.error?.let {
                println(it)
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        }

        viewModel.userRegisterStateOnGoogle.observe(viewLifecycleOwner) { result ->
            result.success?.let {
                googleSignInIntentResultLauncher.launch(it)
            }
            result.error?.let {
                println(it)
                Snackbar.make(requireView(), it, Snackbar.LENGTH_LONG).show()
            }
        }
    }

    private val googleSignInIntentResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result != null && result.resultCode == Activity.RESULT_OK) {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                idToken?.let {
                    findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                }
            }


        }

}