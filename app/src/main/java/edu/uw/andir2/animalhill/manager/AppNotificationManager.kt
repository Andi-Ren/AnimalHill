package edu.uw.andir2.animalhill.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.WorkManager
import edu.uw.andir2.animalhill.AnimalHillApplication
import edu.uw.andir2.animalhill.R
import edu.uw.andir2.animalhill.fragment.TimePickerFragment
import kotlin.random.Random

private const val REMINDER_CHANNEL_ID = "New Reminder on Study"


class AppNotificationManager(private val context: Context) {
    private val animalHillApp: AnimalHillApplication by lazy { context?.applicationContext as AnimalHillApplication }
    private val notificationManager = NotificationManagerCompat.from(context)
    var isNotificationsEnabled = true

    init {
        // Initialize all channels
        initNotificationChannels()
    }

    fun publishNewReminderNotification() {
        if (!isNotificationsEnabled) {
            return
        }

        // Define the intent or action you want when user taps on notification
        val intent = Intent(context, TimePickerFragment::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT) // dont forget to add PendingIntent.FLAG_UPDATE_CURRENT to send data over


        // Build information you want the notification to show
        val builder = NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)    // channel id from creating the channel
            .setSmallIcon(R.drawable.sheep)
            .setContentTitle("Your Animals Want More Friends")
            .setContentText("Come study to bring more friends to farm")
            .setContentIntent(pendingIntent)    // sets the action when user clicks on notification
            .setAutoCancel(true)    // This will dismiss the notification tap
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Tell the OS to publish the notification using the info
        with(notificationManager) {
            // notificationId is a unique int for each notification that you must define
            val notificationId = Random.nextInt()
            notify(notificationId, builder.build())
        }
    }

    private fun initNotificationChannels() {
        initNewReminderChannel()
        //initPromotionChannel() If there's another channel, go here
    }

    private fun initNewReminderChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Info about the channel
            val name = "Regular Study Reminder"
            val descriptionText = "a regular reminder to hook user into studying"
            val importance = NotificationManager.IMPORTANCE_DEFAULT

            // Create channel object
            val channel = NotificationChannel(REMINDER_CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            // Tell the Android OS to create a channel
            notificationManager.createNotificationChannel(channel)
        }
    }

//    private fun initPromotionChannel() {
//
//    }

}