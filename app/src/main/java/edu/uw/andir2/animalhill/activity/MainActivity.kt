package edu.uw.andir2.animalhill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.databinding.ActivityMainBinding
import edu.uw.andir2.animalhill.fragment.ReminderFragmentDirections
import edu.uw.andir2.animalhill.fragment.TimePickerFragmentDirections


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val navController:NavController by lazy { findNavController(R.id.navHost)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
        }
        with(binding) {
            navController.setGraph(R.navigation.nav_graph)
            bottomAppBar.setOnMenuItemClickListener { item ->
                handleMenuItemPressed(item)
            }
        }

    }

    private fun handleMenuItemPressed(item: MenuItem): Boolean {
        Log.i("Item Selected", item.toString())
        binding.navHostContainer.visibility = View.VISIBLE
        when (item.itemId) {
            R.id.app_bar_farm -> binding.navHostContainer.visibility = View.GONE
            R.id.app_bar_timer -> navController.navigate(TimePickerFragmentDirections.actionGlobalTimePickerFragment())
            R.id.app_bar_reminder -> navController.navigate(ReminderFragmentDirections.actionGlobalReminderFragment())
            R.id.app_bar_settings -> {}
        }
        return true
    }
}