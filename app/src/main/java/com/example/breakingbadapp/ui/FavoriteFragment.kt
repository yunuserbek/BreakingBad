package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.BreakingBadAdapter
import com.example.breakingbadapp.databinding.FragmentFavoriteBinding
import com.example.breakingbadapp.viewmodel.BreakingBadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: BreakingBadViewModel by viewModels()

    // private val adapter: BreakingBadAdapter by lazy { BreakingBadAdapter() }
    private lateinit var characterAdapter: BreakingBadAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterAdapter = BreakingBadAdapter { article ->
            val action =
                FavoriteFragmentDirections
                    .actionNavigationFavoritesToNavigationDetail(article, true)
            findNavController().navigate(action)
        }
        binding.charecterRv.adapter = characterAdapter


        viewModel.getFavoriteArticles().observe(viewLifecycleOwner) { character ->
            characterAdapter.submitList(character)
            binding.charecterRv.adapter = characterAdapter


        }


    }

}