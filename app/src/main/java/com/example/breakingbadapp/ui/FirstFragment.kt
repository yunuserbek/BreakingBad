package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingbadapp.BreakingBadAdapter
import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.databinding.FragmentFirstBinding
import com.example.breakingbadapp.viewmodel.BreakingBadViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {
    lateinit var badAdapter: BreakingBadAdapter
    private val viewModel: BreakingBadViewModel by viewModels()
    lateinit var binding: FragmentFirstBinding
    var oldMyNotes = emptyList<CharacterModelItem>()

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
            oldMyNotes = charecter


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
        for (i in oldMyNotes){
            if (i.nickname!!.contains(newText!!)||i.name!!.contains(newText)){
                newFilteredList.add(i)
            }

        }
        badAdapter.filtering(newFilteredList)


    }


}