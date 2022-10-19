package com.example.breakingbadapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.breakingbadapp.adapter.BreakingBadAdapter
import com.example.breakingbadapp.databinding.FragmentFavoriteBinding
import com.example.breakingbadapp.viewmodel.BreakingBadViewModel
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : Fragment() {


    private lateinit var binding: FragmentFavoriteBinding
    private val viewModel: BreakingBadViewModel by viewModels()
    private var mInterstitialAd: InterstitialAd? = null
    private final var TAG = "MainActivity"

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
        bannerAds()
        characterAdapter = BreakingBadAdapter { article ->
            val action =
                FavoriteFragmentDirections
                    .actionNavigationFavoritesToNavigationDetail(article, true)
            findNavController().navigate(action)
            InterstitialAd()

        }
        binding.charecterRv.adapter = characterAdapter


        viewModel.getFavoriteArticles().observe(viewLifecycleOwner) { character ->
            characterAdapter.submitList(character)
            binding.charecterRv.adapter = characterAdapter


        }


    }

    fun bannerAds() {
        MobileAds.initialize(requireContext())
        val adView = AdView(requireContext())
        adView.adUnitId = "ca-app-pub-3940256099942544/6300978111"
        val adRequest = AdRequest.Builder().build()
        binding.adView.loadAd(adRequest)
        adView.adListener = object : AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
                Toast.makeText(requireContext(), "yüklendi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun InterstitialAd() {
        var adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            requireContext(),
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError?.toString())
                    mInterstitialAd = null
                }
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    mInterstitialAd = interstitialAd
                }
            })
        mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }
            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }
        }
        if (mInterstitialAd != null) {
            mInterstitialAd?.show(requireActivity())
        }
    }


}