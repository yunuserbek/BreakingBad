package com.example.breakingbadapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.databinding.CharecterItemBinding
import com.example.breakingbadapp.ui.FirstFragmentDirections
import com.squareup.picasso.Picasso

class BreakingBadAdapter :
    ListAdapter<CharacterModelItem, BreakingBadAdapter.CharecterViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharecterViewHolder {
        return CharecterViewHolder(
            CharecterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharecterViewHolder, position: Int) {
        var charecter = getItem(position)
        holder.bind(charecter)

    }


    class CharecterViewHolder(val binding: CharecterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: CharacterModelItem) {
            binding.characterName.text = character.name
            binding.actorName.text = character.nickname
            Picasso.get().load(character.img).into(binding.characterImage)
            binding.root.setOnClickListener {
                val action = FirstFragmentDirections.actionFirstFragmentToDetailFragment(character)
                Navigation.findNavController(it).navigate(action)
            }


        }


    }


    companion object DiffCallback : DiffUtil.ItemCallback<CharacterModelItem>() {
        override fun areItemsTheSame(
            oldItem: CharacterModelItem,
            newItem: CharacterModelItem
        ): Boolean {
            return oldItem.charId == newItem.charId
        }

        override fun areContentsTheSame(
            oldItem: CharacterModelItem,
            newItem: CharacterModelItem
        ): Boolean {
            return oldItem == newItem
        }

    }


}


