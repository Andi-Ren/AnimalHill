package edu.uw.andir2.animalhill.manager

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

private const val APP_SYNC_WORK_TAG = "APP_SYNC_WORK_TAG" // tag to help track notification


class AppSyncManager(context: Context) {
    private val workManager: WorkManager = WorkManager.getInstance(context)

    //refresh songs after 3 seconds this function is executed
    fun pushReminder() {
        val request = OneTimeWorkRequestBuilder<AppSyncWorker>()
            .setInitialDelay(3, TimeUnit.SECONDS) // Delay 5 seconds when refreshing
            .setConstraints(
                Constraints.Builder() //note: it's the work constraints, not constraint layout
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build()
            )
            .addTag(APP_SYNC_WORK_TAG)
            .build()

        workManager.enqueue(request)
    }

    //refresh App every 15 min only if no notification enqueued
    fun startRefreshSongsPeriodically() {
        if (isSongSyncRunning()) {
            return
        }

        // alarm manager for exact time
        val request = PeriodicWorkRequestBuilder<AppSyncWorker>(7, TimeUnit.DAYS)
            //make sure using the constraint class instead of constraints layout
            .setInitialDelay(5, TimeUnit.SECONDS)
            .setConstraints(
                Constraints.Builder()
                    //.setRequiresCharging(true) //only refresh when user is charging
                    .setRequiredNetworkType(NetworkType.CONNECTED) //only run when network in connected
                    .build()
            )
            .addTag(APP_SYNC_WORK_TAG)
            .build()

        workManager.enqueue(request)

    }

    //Another work manager that refresh song every 2 days
    fun startRefreshSongsPeriodically2() {
        if (isSongSyncRunning()) {
            return
        }

        val request = PeriodicWorkRequestBuilder<AppSyncWorker>(2, TimeUnit.DAYS)
            //make sure using the constraint class instead of constraints layout
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .setRequiredNetworkType(NetworkType.CONNECTED) //only run when network in connected
                    .build()
            )
            .addTag(APP_SYNC_WORK_TAG)
            .build()

        workManager.enqueue(request)

    }

    // stop pushing notifications
    fun stopPeriodicallyRefreshing() {
        workManager.cancelAllWorkByTag(APP_SYNC_WORK_TAG)
    }

    private fun isSongSyncRunning(): Boolean {
        //get all tasks under this tag. If any work is running or enqueued, return true
        return workManager.getWorkInfosByTag(APP_SYNC_WORK_TAG).get().any {
            when(it.state) {
                WorkInfo.State.RUNNING,
                WorkInfo.State.ENQUEUED -> true
                else -> false
            }
        }
    }
}