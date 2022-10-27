package com.example.breakingbadapp.ui.detail

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.breakingbadapp.domain.model.CharacterModelItem
import com.example.breakingbadapp.R
import com.example.breakingbadapp.databinding.FragmentDetailBinding
import com.example.breakingbadapp.viewmodel.BreakingBadViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailFragment : Fragment(), MenuProvider {
    private val viewModel: BreakingBadViewModel by viewModels()
    private lateinit var binding:FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    lateinit var character: CharacterModelItem
    private var isFavorite = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        isFavorite = args.favorite
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        character = args.charecter
        isFavorite = args.favorite
      bind()
    }

    private fun bind(){
        binding.actorName.text = args.charecter.name
        binding.characterName.text = args.charecter.nickname
        Glide.with(requireContext())
            .load(args.charecter.img)
            .into(binding.characterImage)
    }

    private fun setIcon(item: MenuItem) {
       item.icon =
           if (isFavorite)
               ContextCompat.getDrawable(this.requireContext(), R.drawable.ic_baseline_favorite)
            else ContextCompat.getDrawable(this.requireContext(),
               R.drawable.ic_baseline_favorite_border_24
           )
   }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.detail_action_bar, menu)
        setIcon(menu.getItem(0))
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.favorite -> {
                if (isFavorite) {
                    viewModel.removeArticleFromFavorites(character)
                } else {
                    viewModel.addArticleToFavorites(character)
                }
                isFavorite = !isFavorite
                setIcon(menuItem)
                true
            }
            else -> {
                findNavController().navigateUp()
                false
            }
        }
    }


}