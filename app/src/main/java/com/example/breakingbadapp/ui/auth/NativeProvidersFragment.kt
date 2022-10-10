package com.example.breakingbadapp.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.R
import com.example.breakingbadapp.common.AuthOperationsWrapper
import com.example.breakingbadapp.databinding.FragmentNativeProvidersBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class NativeProvidersFragment : Fragment() {
    private lateinit var binding:FragmentNativeProvidersBinding
    @Inject
    lateinit var authOperationsWrapper: AuthOperationsWrapper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNativeProvidersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()){
                    authOperationsWrapper.signUpWithEmailAndPassword(email,password,{
                     //   findNavController().navigate(R.id.)
                        Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_authFragment_to_firstFragment)
                    }, {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()

                    })
                }
            }
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (email.isNotEmpty() && password.isNotEmpty()){
                    authOperationsWrapper.signInWithEmailAndPassword(email,password,{
                        Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_authFragment_to_firstFragment)

                    },{
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    })
                }
            }
            btnPhone.setOnClickListener {
                val phoneNumber = etPhoneNumber.text.toString()

                if (phoneNumber.isNotEmpty()) {
                    authOperationsWrapper.sendVerificationCode(phoneNumber, {
                        Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                    }, {
                      //  findNavController().navigate(R.id.action_authFragment_to_firstFragment)
                        Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                    }, {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    })
                }
            }

            btnVerifyCode.setOnClickListener {
                val verifyCode = etVerifyCode.text.toString()

                if (verifyCode.isNotEmpty()) {
                    authOperationsWrapper.verifyCode(verifyCode, {
                        findNavController().navigate(R.id.action_authFragment_to_firstFragment)
                        Toast.makeText(requireContext(), "successful", Toast.LENGTH_SHORT).show()
                    }, {
                        Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
                    })
                }
            }
        }
    }

}