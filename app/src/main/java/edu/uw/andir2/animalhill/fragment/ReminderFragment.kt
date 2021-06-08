package edu.uw.andir2.animalhill.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.edit
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import edu.uw.andir2.animalhill.AnimalHillApplication
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.databinding.FragmentReminderBinding
import edu.uw.andir2.animalhill.manager.AppSyncManager
import edu.uw.andir2.animalhill.manager.AppNotificationManager

const val NOTIFICATIONS_ENABLED_PREF_KEY = "notifications_enabled"
const val NOTIFICATION_WEEK_KEY = "notifications_week"
const val NOTIFICATION_TIME_KEY = "notifications_time"






class ReminderFragment : Fragment() {
    private lateinit var binding: FragmentReminderBinding
    private val animalHillApp: AnimalHillApplication by lazy { context?.applicationContext as AnimalHillApplication }
    private val appNotificationManager: AppNotificationManager by lazy { animalHillApp.appNotificationManager }
    private val appSyncManager: AppSyncManager by lazy { animalHillApp.appSyncManager }
    private val preferences by lazy { animalHillApp.preferences }
    private var reminderTimer: CountDownTimer? = null
    private var hasChange = false
    var notificationWeek = "FFFFFFF".toCharArray()//set default week value before checking with preference
    private var notificationTime = listOf<String>("18","00")
    var ifNotification = false

@SuppressLint("ResourceAsColor")
override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
): View? {
    val binding = FragmentReminderBinding.inflate(inflater)

    with(binding){
        //retrieve data saved on disk to see if buttons has previously been checked and stuff
        ifNotification = preferences.getBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, false)
        switchNotificationsEnabled.isChecked = ifNotification
        appNotificationManager.isNotificationsEnabled = ifNotification
        if (ifNotification) {
            appSyncManager.startRefreshSongsPeriodically()
        }

        //read previously saved notification preference, default no days and 20:00. Note both are in array
        var maybeNotificationWeek = preferences.getString(NOTIFICATION_WEEK_KEY, "FFFFFFF")
        if(maybeNotificationWeek != null){
            notificationWeek = maybeNotificationWeek.toCharArray()
        }
        var maybeNotificationTime = preferences.getString(NOTIFICATION_TIME_KEY, "20:00")?.split(":")
        if(maybeNotificationTime != null){
            notificationTime = maybeNotificationTime
        }

        //set save button to false because nothing to change when it's been initialized
        ifChange(binding,hasChange)

        //set to confirmed color if previously been set
        if (notificationWeek[0] == 'T'){
            btnMonday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
        }
        if (notificationWeek[1] == 'T'){
            btnTuesday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
        }
        if (notificationWeek[2] == 'T'){
            btnWednesday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
        }
        if (notificationWeek[3] == 'T'){
            btnThursday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
        }
        if (notificationWeek[4] == 'T'){
            btnFriday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
        }
        if (notificationWeek[5] == 'T'){
            btnSaturday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
        }
        if (notificationWeek[6] == 'T'){
            btnSunday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
        }

        btnMonday.setOnClickListener{
            if(notificationWeek[0] == 'F'){
                changeDayHelper(binding, 0,'T',false)
            } else {
                changeDayHelper(binding,0,'F',false)
            }
            hasChange = true
        }

        btnTuesday.setOnClickListener{
            if(notificationWeek[1] == 'F'){
                changeDayHelper(binding,1,'T',false)
            } else {
                changeDayHelper(binding,1,'F',false)
            }
        }

        btnWednesday.setOnClickListener{
            if(notificationWeek[2] == 'F'){
                changeDayHelper(binding,2,'T',false)
            } else {
                changeDayHelper(binding,2,'F',false)
            }
            hasChange = true
        }

        btnThursday.setOnClickListener{
            if(notificationWeek[3] == 'F'){
                changeDayHelper(binding,3,'T',false)
            } else {
                changeDayHelper(binding,3,'F',false)
            }
            hasChange = true
        }

        btnFriday.setOnClickListener{
            if(notificationWeek[4] == 'F'){
                changeDayHelper(binding,4,'T',false)
            } else {
                changeDayHelper(binding,4,'F',false)
            }
            hasChange = true
        }

        btnSaturday.setOnClickListener{
            if(notificationWeek[5] == 'F'){
                changeDayHelper(binding,5,'T',false)
            } else {
                changeDayHelper(binding,5,'F',false)
            }
            hasChange = true
        }

        btnSunday.setOnClickListener{
            if(notificationWeek[6] == 'F'){
                changeDayHelper(binding,6,'T',false)
            } else {
                changeDayHelper(binding,6,'F',false)
            }
            hasChange = true
        }

        btnTest.setOnClickListener {
            appSyncManager.pushReminder()
        }


        //for every day, change color and set status
        binding.switchNotificationsEnabled.setOnCheckedChangeListener { _, isChecked ->
            appNotificationManager.isNotificationsEnabled = isChecked
            ifNotification = isChecked
            //turn on if made week changes and just turned on notification switch
            ifChange(binding,hasChange)

            //if checked run based on set week and time, else cancel all queued notiification
            if (isChecked) {
                appSyncManager.startRefreshSongsPeriodically()
            } else {
                appSyncManager.stopPeriodicallyRefreshing()
            }
            // Saving the value in preferences whether the switch is on or not
            preferences.edit {
                putBoolean(NOTIFICATIONS_ENABLED_PREF_KEY, isChecked)
            }

//            if (isChecked) {
//                Toast.makeText(this@SettingsFragment, "Notifications enabled", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(this@SettingsFragment, "Notifications are turned off", Toast.LENGTH_SHORT).show()
//            }
        }
    }

    return binding.root
}

    //enable save button only if things has been changed
    @SuppressLint("ResourceAsColor")
    private fun ifChange(binding: FragmentReminderBinding,hasChange: Boolean) {
        if(hasChange && ifNotification){
            binding.btnSave.isEnabled = true
            binding.btnSave.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.cyan,null))
        } else {
            binding.btnSave.isEnabled = false
            binding.btnSave.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.disabled_gray,null))
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun changeDayHelper(binding: FragmentReminderBinding,index:Int, desiredVal:Char, ifInitialise:Boolean) {
        notificationWeek[index] = desiredVal
        if (index == 0) {
            if (desiredVal == 'T') {
                binding.btnMonday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
            } else {
                binding.btnMonday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.light_orange,null))
            }
        } else if (index == 1) {
            if (desiredVal == 'T') {
                binding.btnTuesday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
            } else {
                binding.btnTuesday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.light_orange,null))
            }
        } else if (index == 2) {
            if (desiredVal == 'T') {
                binding.btnWednesday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
            } else {
                binding.btnWednesday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.light_orange,null))
            }

        } else if (index == 3) {
            if (desiredVal == 'T') {
                binding.btnThursday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
            } else {
                binding.btnThursday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.light_orange,null))
            }

        } else if (index == 4) {
            if (desiredVal == 'T') {
                binding.btnFriday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
            } else {
                binding.btnFriday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.light_orange,null))
            }

        } else if (index == 5) {
            if (desiredVal == 'T') {
                binding.btnSaturday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.dark_orange,null))
            } else {
                binding.btnSaturday.setBackgroundColor(ResourcesCompat.getColor(resources,R.color.light_orange,null))
            }

        } else if (index == 6) {
            if (desiredVal == 'T') {
                binding.btnSunday.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.dark_orange,
                        null
                    )
                )
            } else {
                binding.btnSunday.setBackgroundColor(
                    ResourcesCompat.getColor(
                        resources,
                        R.color.light_orange,
                        null
                    )
                )
            }
        }
        if (!ifInitialise) {
            hasChange = true
            ifChange(binding,hasChange)

        }
    }
}