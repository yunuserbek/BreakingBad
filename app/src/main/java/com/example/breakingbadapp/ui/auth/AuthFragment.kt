package com.example.breakingbadapp.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.R
import com.example.breakingbadapp.common.AuthOperationsWrapper
import com.example.breakingbadapp.databinding.FragmentAuthBinding
import com.example.breakingbadapp.databinding.FragmentDetailBinding
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class AuthFragment :Fragment() {
    private lateinit var binding: FragmentAuthBinding
    private lateinit var oneTapClient: SignInClient
    @Inject
    lateinit var authOperations: AuthOperationsWrapper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        oneTapClient = Identity.getSignInClient(requireActivity())
        authOperations.checkCurrentUser {
            findNavController().navigate(R.id.action_authFragment_to_firstFragment)
        }
       with(binding){
           val titleList = arrayListOf("Native provides","Additional Providers")
           viewPager.adapter =AuthViewPagerAdapter(childFragmentManager,lifecycle)

           TabLayoutMediator(tabLayout,viewPager){tab,position->
               tab.text =titleList[position]

           }.attach()
       }
    }
}