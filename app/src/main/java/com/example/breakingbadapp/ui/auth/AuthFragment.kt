package com.example.breakingbadapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentAuthBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthFragment :Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private lateinit var oneTapClient: SignInClient
    private val viewModel: AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTapClient = Identity.getSignInClient(requireActivity())

        if (viewModel.checkCurrentUser()) {
            findNavController().navigate(R.id.action_authFragment_to_homeFragment)
        }

       with(binding) {
           viewPager.adapter = AuthViewPagerAdapter(childFragmentManager, lifecycle)
       }
    }
}