package com.example.breakingbadapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.databinding.CharecterItemBinding
import com.example.breakingbadapp.ui.FirstFragmentDirections

class BreakingBadAdapter(val character: List<CharacterModelItem>) :
    RecyclerView.Adapter<BreakingBadAdapter.CharecterViewHolder>() {
    class CharecterViewHolder(val binding: CharecterItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharecterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CharecterItemBinding.inflate(layoutInflater, parent, false)
        return CharecterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharecterViewHolder, position: Int) {
        val charecterList = character[position]
        holder.binding.apply {
            actorName.text = charecterList.name
            characterName.text = charecterList.nickname
            Glide.with(holder.itemView.context)
                .load(charecterList.img)
                .into(holder.binding.characterImage);


        }
        holder.binding.root.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToDetailFragment(charecterList)
            Navigation.findNavController(it).navigate(action)
        }





    }

    override fun getItemCount(): Int {
        return character.size
    }
}

