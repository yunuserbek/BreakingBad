package com.example.breakingbadapp


import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.breakingbadapp.databinding.ActivityMainBinding
import com.example.breakingbadapp.ui.auth.AuthFragment
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.fragmentContainerView)

        navController.addOnDestinationChangedListener{_,destination,_->
            when(destination.id){
                R.id.homeFragment,R.id.favoriteFragment->{
                    binding.navView.visibility = View.VISIBLE
                    supportActionBar?.show()
                }


                R.id.splashFragment ->{
                    binding.navView.visibility = View.GONE
                    supportActionBar?.hide()
                }



            }

        }
        if (supportFragmentManager.findFragmentById(R.id.splashFragment) is AuthFragment) {    supportActionBar?.hide() }


        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment, R.id.favoriteFragment,R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}