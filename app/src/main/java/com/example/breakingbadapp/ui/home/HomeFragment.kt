package com.example.breakingbadapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.ui.adapter.BreakingBadAdapter
import com.example.breakingbadapp.domain.model.CharacterModelItem
import com.example.breakingbadapp.R
import com.example.breakingbadapp.common.wrappers.AuthOperationsWrapper
import com.example.breakingbadapp.databinding.FragmentFirstBinding
import com.example.breakingbadapp.viewmodel.BreakingBadViewModel
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), MenuProvider {
    //private val adapter: BreakingBadAdapter by lazy { BreakingBadAdapter() }
    private lateinit var characterAdapter: BreakingBadAdapter

    @Inject
    lateinit var authOperationsWrapper: AuthOperationsWrapper
    private val viewModel: BreakingBadViewModel by viewModels()
    private lateinit var binding: FragmentFirstBinding

    private var oldMyNotes = emptyList<CharacterModelItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFirstBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        characterAdapter = BreakingBadAdapter { article ->
            val action =
                HomeFragmentDirections.firstFragmentToDetailFragment(
                    article,
                    false
                )
            findNavController().navigate(action)
        }

        binding.charecterRv.adapter = characterAdapter

        viewModel._breakingList.observe(viewLifecycleOwner) { character ->
            loadNativeAds(onLoadedAd = {
                characterAdapter.setNativeAds(it)
                characterAdapter.submitList(addNullToArray(character))
                binding.charecterRv.adapter = characterAdapter
                oldMyNotes = character
                binding.countryLoading.visibility = View.GONE
            }, onAdFailedToLoad = {
                characterAdapter.submitList(character)

            })
        }

        viewModel.countryLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.countryLoading.visibility = View.VISIBLE
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

    private fun addNullToArray(data: List<CharacterModelItem>): List<CharacterModelItem?> {
        val newData = arrayListOf<CharacterModelItem?>()
        for (i in data.indices) {
            if (i % 3 == 0) {
                if (i != 0 && i != data.size - 1) newData.add(null)
            }
            newData.add(data[i])
        }
        return newData
    }

    private fun loadNativeAds(
        onLoadedAd: (NativeAd) -> Unit, onAdFailedToLoad: (String) -> Unit
    ) {
        val adLoader = AdLoader.Builder(requireContext(), "ca-app-pub-3940256099942544/2247696110")
            .forNativeAd { ad: NativeAd ->
                onLoadedAd(ad)
            }.withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    onAdFailedToLoad(adError.message)
                }
            }).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }



    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.signout, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        // Do stuff...
        return when (menuItem.itemId) {
            R.id.signout_menu -> {
                viewModel.signOut()
                findNavController().navigate(R.id.action_firstFragment_to_authFragment)
                true
            }
            else -> false
        }
    }

}