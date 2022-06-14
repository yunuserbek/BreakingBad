package com.example.breakingbadapp

import android.view.KeyCharacterMap.load
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.databinding.CharecterItemBinding

class BreakingBadAdapter(val charecter: List<CharacterModelItem>) :
    RecyclerView.Adapter<BreakingBadAdapter.CharecterViewHolder>() {
    class CharecterViewHolder(val binding: CharecterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharecterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CharecterItemBinding.inflate(layoutInflater, parent, false)
        return CharecterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharecterViewHolder, position: Int) {
        val charecterList = charecter[position]
        holder.binding.actorName.text = charecterList.name
        holder.binding.characterName.text = charecterList.nickname
        Glide.with(holder.itemView.context)
            .load(charecterList.img)
            .into(holder.binding.characterImage);

    }

    override fun getItemCount(): Int {
        return charecter.size
    }
}

