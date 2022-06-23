package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.breakingbadapp.BreakingBadAdapter
import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.databinding.FragmentFirstBinding
import com.example.breakingbadapp.viewmodel.BreakingBadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {
    private val adapter: BreakingBadAdapter by lazy { BreakingBadAdapter() }

    private val viewModel: BreakingBadViewModel by viewModels()
    lateinit var binding: FragmentFirstBinding

    var oldMyNotes = emptyList<CharacterModelItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel._breakingList.observe(viewLifecycleOwner) { charecter ->
            adapter.submitList(charecter)
            binding.charecterRv.adapter = adapter
            oldMyNotes = charecter
            binding.countryLoading.visibility = View.GONE

        }
        viewModel.countryLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.countryLoading.visibility = View.VISIBLE
                //binding.charecterRv.visibility =View.GONE

            } else {
                binding.countryLoading.visibility = View.GONE
            }
        }





        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterSearch(newText)
                return true
            }

        })
    }

    private fun filterSearch(newText: String?) {
        val newFilteredList = arrayListOf<CharacterModelItem>()
        for (i in oldMyNotes) {
            if (i.nickname!!.lowercase().contains(newText!!) || i.name!!.contains(newText)) {
                newFilteredList.add(i)
            }
        }
        adapter.submitList(newFilteredList)


    }




}