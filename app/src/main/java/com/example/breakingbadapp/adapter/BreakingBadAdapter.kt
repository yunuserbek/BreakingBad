package com.example.breakingbadapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.databinding.CharecterItemBinding
import com.example.breakingbadapp.databinding.NativeAdLayoutBinding
import com.example.breakingbadapp.enum
import com.google.android.gms.ads.nativead.NativeAd


class BreakingBadAdapter(private val onItemClicked: (CharacterModelItem) -> Unit) :
    ListAdapter<CharacterModelItem, RecyclerView.ViewHolder>(DiffCallback) {
    private var nativeAd: NativeAd? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            enum.ITEM_NATIVE_ADS -> {
                NativeAdsViewHolder(
                    NativeAdLayoutBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            else -> {
                CharecterViewHolder(
                    CharecterItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = getItem(position)
        when (holder) {
            is CharecterViewHolder -> {
                holder.bind(item)
                holder.itemView.setOnClickListener {
                    onItemClicked(item)
                }
            }
            is NativeAdsViewHolder -> {
                holder.bind()
            }
        }
    }


    class CharecterViewHolder(val binding: CharecterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterModelItem) = with(binding) {
            val context = binding.root.context
            characterName.text = character.name
            actorName.text = character.nickname
            Glide.with(context).load(character.img).into(characterImage)

        }
    }

    inner class NativeAdsViewHolder(private var binding: NativeAdLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {

            with(binding) {
                nativeAd?.let {
                    nativeAdView.mediaView = adMedia
                    nativeAdView.headlineView = adHeadline
                    nativeAdView.bodyView = adBody
                    nativeAdView.callToActionView = adBtnAction
                    nativeAdView.iconView = adAppIcon
                    nativeAdView.priceView = adPrice
                    nativeAdView.starRatingView = adStars
                    nativeAdView.storeView = adStore
                    nativeAdView.advertiserView = adAdvertiser
                    nativeAdView.mediaView?.setMediaContent(it.mediaContent!!)
                    nativeAdView.mediaView?.setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                    (nativeAdView.headlineView as TextView).text = it.headline
                    (nativeAdView.bodyView as TextView).text = it.body
                    (nativeAdView.callToActionView as Button).text = it.callToAction
                    (nativeAdView.iconView as ImageView).setImageDrawable(it.icon?.drawable)
                    (nativeAdView.priceView as TextView).text = it.price
                    (nativeAdView.storeView as TextView).text = it.store
                    (nativeAdView.starRatingView as RatingBar).rating = it.starRating!!.toFloat()
                    (nativeAdView.advertiserView as TextView).text = it.advertiser
                    nativeAdView.setNativeAd(it)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            null -> enum.ITEM_NATIVE_ADS
            else -> enum.ITEM_TODO
        }
    }

    fun setNativeAds(nativeAd: NativeAd) {
        this.nativeAd = nativeAd
    }

    companion object DiffCallback : DiffUtil.ItemCallback<CharacterModelItem>() {
        override fun areItemsTheSame(oldItem: CharacterModelItem, newItem: CharacterModelItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: CharacterModelItem, newItem: CharacterModelItem) =
            oldItem == newItem
    }

}


