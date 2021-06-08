package edu.uw.andir2.animalhill.manager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import edu.uw.andir2.animalhill.AnimalHillApplication
import edu.uw.andir2.animalhill.repository.DataRepoRoom
import java.lang.Exception

class AppSyncWorker (
    private val context: Context,
    workerParameters: WorkerParameters
): CoroutineWorker(context, workerParameters) {
    private lateinit var repository: DataRepoRoom
    //private lateinit var animalHillApp: AnimalHillApplication

    private val application by lazy { context.applicationContext as AnimalHillApplication }
    private val animalHillApp: AnimalHillApplication by lazy { application as AnimalHillApplication }
    private val appNotificationManager by lazy { animalHillApp.appNotificationManager }

    //push notification
    override suspend fun doWork(): Result {
        repository = animalHillApp.repository
        return try {
            Log.i("AppSyncWorker", "syncing contents now")
            appNotificationManager.publishNewReminderNotification()
            Result.success()
        }catch(ex: Exception){
            return Result.failure()
        }
    }
}