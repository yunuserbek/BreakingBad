package com.example.breakingbadapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentNativeProvidersBinding

class NativeProvidersFragment : Fragment() {
    private lateinit var binding:FragmentNativeProvidersBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentNativeProvidersBinding.inflate(layoutInflater)
        return binding.root
    }

}