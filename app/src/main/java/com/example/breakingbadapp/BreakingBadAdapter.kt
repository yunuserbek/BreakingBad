package com.example.breakingbadapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.breakingbadapp.Model.CharacterModelItem
import com.example.breakingbadapp.databinding.CharecterItemBinding
import com.example.breakingbadapp.ui.FavoriteFragmentDirections
import com.example.breakingbadapp.ui.FirstFragmentDirections

class BreakingBadAdapter(private val onItemClicked: (CharacterModelItem)->Unit) :
    ListAdapter<CharacterModelItem, BreakingBadAdapter.CharecterViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharecterViewHolder {

        return CharecterViewHolder(
            CharecterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharecterViewHolder, position: Int) {
        val charecter = getItem(position)

        holder.bind(charecter)
        holder.itemView.setOnClickListener {
            onItemClicked(charecter)
        }

    }


    class CharecterViewHolder(val binding: CharecterItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(character: CharacterModelItem) = with(binding) {
            val context = binding.root.context
            characterName.text = character.name
            actorName.text = character.nickname
            // Picasso.get().load(character.img).into(binding.characterImage)
            Glide.with(context).load(character.img).into(characterImage)
          //  root.setOnClickListener {
          //      val action =
          //          FirstFragmentDirections.actionFirstFragmentToDetailFragment(character, true)
          //      Navigation.findNavController(it).navigate(action)
//
          //  }
        }
    }


    companion object DiffCallback : DiffUtil.ItemCallback<CharacterModelItem>() {
        override fun areItemsTheSame(
            oldItem: CharacterModelItem,
            newItem: CharacterModelItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterModelItem,
            newItem: CharacterModelItem
        ): Boolean {
            return oldItem == newItem
        }

    }


}


