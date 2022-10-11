package com.example.breakingbadapp.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.BreakingBadAdapter
import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.R
import com.example.breakingbadapp.common.AuthOperationsWrapper
import com.example.breakingbadapp.databinding.FragmentFirstBinding
import com.example.breakingbadapp.viewmodel.BreakingBadViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FirstFragment : Fragment() {
    //private val adapter: BreakingBadAdapter by lazy { BreakingBadAdapter() }
    private lateinit var characterAdapter: BreakingBadAdapter
    @Inject
    lateinit var authOperationsWrapper: AuthOperationsWrapper
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

        setHasOptionsMenu(true)
        characterAdapter = BreakingBadAdapter { article ->
            val action =
                FirstFragmentDirections.actionFirstFragmentToDetailFragment(article, false)
            findNavController().navigate(action)
        }

        binding.charecterRv.adapter = characterAdapter

        viewModel._breakingList.observe(viewLifecycleOwner) { charecter ->
            characterAdapter.submitList(charecter)
            binding.charecterRv.adapter = characterAdapter
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
        characterAdapter.submitList(newFilteredList)


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.signout, menu)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {

            R.id.singout_menu -> {
                authOperationsWrapper.signOut()
                findNavController().navigate(R.id.action_firstFragment_to_authFragment)
                true
            }
            else -> {

                super.onOptionsItemSelected(item)
            }
        }
    }

}