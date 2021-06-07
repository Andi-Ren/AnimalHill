package edu.uw.andir2.animalhill.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.ReminderFragmentDirections
import edu.uw.andir2.animalhill.databinding.ActivityMainBinding
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

        }
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findViewById<View>(R.id.navHost).visibility = View.VISIBLE
        when (item.itemId) {
            R.id.app_bar_farm -> {findViewById<View>(R.id.navHost).visibility = View.GONE}
            R.id.app_bar_timer -> navController.navigate(TimePickerFragmentDirections.actionGlobalTimePickerFragment())
            R.id.app_bar_reminder -> navController.navigate(ReminderFragmentDirections.actionGlobalReminderFragment())
            R.id.app_bar_settings -> {}
        }
        return true
    }
}