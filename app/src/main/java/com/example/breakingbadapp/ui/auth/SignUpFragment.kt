package com.example.breakingbadapp.ui.auth

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentSignupBinding
import com.example.breakingbadapp.viewmodel.AuthViewModel
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    private lateinit var binding:FragmentSignupBinding
    private lateinit var oneTapClient: SignInClient

    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignupBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTapClient = Identity.getSignInClient(requireActivity())



           binding.btnSignUp.setOnClickListener {
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.signUpWithEmailAndPassword(email, password, {
                        //   findNavController().navigate(R.id.)
                        Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                    }, {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

                    })
                }
            }
        binding.btnGithub.setOnClickListener {

            viewModel.signInWithGithub(requireActivity(), {
                findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
        binding.btnTwitter.setOnClickListener {

            viewModel.signInWithTwitter(requireActivity(),
                onSuccess = {
                    findNavController().navigate(R.id.action_authFragment_to_homeFragment)
                    Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                })
        }
        binding.btnGoogle.setOnClickListener {

            viewModel.signInWithGoogle(requireActivity(), oneTapClient, {
                googleSignInIntentResultLauncher.launch(it)
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

            })
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






