package edu.uw.andir2.animalhill.fragment

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import edu.uw.andir2.animalhill.AnimalHillApplication
import edu.uw.andir2.animalhill.databinding.FragmentReminderBinding

const val NOTIFICATIONS_ENABLED_PREF_KEY = "notifications_enabled"




class ReminderFragment : Fragment() {
    private lateinit var binding: FragmentReminderBinding
    private val animalHillApp: AnimalHillApplication by lazy { context?.applicationContext as AnimalHillApplication }
    private val preferences by lazy { animalHillApp.preferences }

    private var reminderTimer: CountDownTimer? = null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = FragmentReminderBinding.inflate(layoutInflater).apply { setContentView(root) }
//        with(binding) {
//
//        }
//    }
override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    val binding = FragmentReminderBinding.inflate(inflater)
//
//    binding.switchNotificationsEnabled.isChecked = preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)
//    songNotificationManager.isNotificationsEnabled = preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)
//    if (preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY,false)){
//        refreshSongManager.startRefreshSongsPeriodically()
//    }
//
//
//    binding.switchNotificationsEnabled.setOnCheckedChangeListener { _, isChecked ->
//        songNotificationManager.isNotificationsEnabled = isChecked
//
//        if (isChecked){
//            refreshSongManager.startRefreshSongsPeriodically()
//        }
//        // Saving the value in preferences whether the switch is on or not
//        preferences.edit {
//            putBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, isChecked)
//        }
//
////            if (isChecked) {
////                Toast.makeText(this@SettingsFragment, "Notifications enabled", Toast.LENGTH_SHORT).show()
////            } else {
////                Toast.makeText(this@SettingsFragment, "Notifications are turned off", Toast.LENGTH_SHORT).show()
////            }
//    }


    return binding.root
}

}