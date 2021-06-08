package edu.uw.andir2.animalhill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialFadeThrough
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.databinding.ActivityMainBinding
import edu.uw.andir2.animalhill.fragment.RecordListFragmentDirections
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

            fab.setOnClickListener {
                val materialFade = MaterialFadeThrough().apply { duration = 250L }
                TransitionManager.beginDelayedTransition(dial, materialFade)
                dial.visibility = if (dial.isVisible) View.GONE else View.VISIBLE
            }

            fabFarm.setOnClickListener { navigateToFarm() }

            fabTimer.setOnClickListener {
                navController.navigate(TimePickerFragmentDirections.actionGlobalTimePickerFragment())
                navHostContainer.visibility = View.VISIBLE
            }
        }

    }


    private fun handleMenuItemPressed(item: MenuItem): Boolean {
        Log.i("Item Selected", item.toString())

        when (item.itemId) {
            R.id.app_bar_record_list -> {
                navigateExceptFarm(RecordListFragmentDirections.actionGlobalRecordListFragment())
            }
            R.id.app_bar_animal_list -> {
                navigateExceptFarm(TimePickerFragmentDirections.actionGlobalTimePickerFragment())
            }
            R.id.app_bar_reminder -> {
                navigateExceptFarm(ReminderFragmentDirections.actionGlobalReminderFragment())
            }
        }
        return true
    }

    private fun navigateToFarm() {
        binding.navHostContainer.visibility = View.GONE
    }
    private fun navigateExceptFarm(dest : NavDirections) {
        navController.navigate(dest)
        binding.navHostContainer.visibility = View.VISIBLE
    }
}