package com.example.breakingbadapp.common.wrappers

import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd

class AdsOperationsWrapper(private val context: Context) {

    fun loadNativeAds(
        onLoadedAd: (NativeAd) -> Unit,
        onAdFailedToLoad: (String) -> Unit
    ) {
        val adLoader = AdLoader.Builder(context, NATIVE_ADS_ID)
            .forNativeAd { ad: NativeAd ->
                onLoadedAd(ad)
            }
            .withAdListener(object : AdListener() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    onAdFailedToLoad(adError.message)
                }
            }).build()
        adLoader.loadAd(AdRequest.Builder().build())
    }

    companion object {
        private const val NATIVE_ADS_ID = "ca-app-pub-3940256099942544/2247696110"
     }
}