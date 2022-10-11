package com.example.breakingbadapp.ui.auth

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.R
import com.example.breakingbadapp.common.AuthOperationsWrapper
import com.example.breakingbadapp.databinding.FragmentAdditionalProvidersBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AdditionalProvidersFragment : Fragment() {
    private lateinit var binding:FragmentAdditionalProvidersBinding
    private lateinit var oneTapClient: SignInClient
    @Inject
    lateinit var authOperationsWrapper: AuthOperationsWrapper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAdditionalProvidersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTapClient = Identity.getSignInClient(requireActivity())
        binding.btnGithub.setOnClickListener {

            authOperationsWrapper.signInWithGithub(requireActivity(), {
                findNavController().navigate(R.id.action_authFragment_to_firstFragment)
                Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
            }, {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            })
        }
        binding.btnTwitter.setOnClickListener {

            authOperationsWrapper.signInWithTwitter(requireActivity(),
                onSuccess = {
                    findNavController().navigate(R.id.action_authFragment_to_firstFragment)
                    Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                })
        }



       binding.btnGoogle.setOnClickListener {

            authOperationsWrapper.signInWithGoogle(requireActivity(), oneTapClient, {
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
                    findNavController().navigate(R.id.action_authFragment_to_firstFragment)
                }
            }
        }
}