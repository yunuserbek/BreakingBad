package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.breakingbadapp.BreakingBadAdapter
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentFirstBinding
import com.example.breakingbadapp.viewmodel.BreakingBadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {
    lateinit var badAdapter: BreakingBadAdapter
    private val viewModel: BreakingBadViewModel by viewModels()
    lateinit var binding: FragmentFirstBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel._breakingList.observe(viewLifecycleOwner) { charecter ->
            badAdapter = BreakingBadAdapter(charecter)
            binding.charecterRv.layoutManager = LinearLayoutManager(context)
            binding.charecterRv.adapter = badAdapter



        }
    }


}