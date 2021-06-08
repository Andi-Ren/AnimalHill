package edu.uw.andir2.animalhill

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import edu.uw.andir2.animalhill.manager.AppSyncManager
import edu.uw.andir2.animalhill.manager.AppNotificationManager
import edu.uw.andir2.animalhill.model.AnimalRoomDatabase
import edu.uw.andir2.animalhill.model.RecordRoomDatabase
import edu.uw.andir2.animalhill.repository.DataRepoRoom

const val ANIMAL_APP_PREFS_KEY = "Animal App Prefs"


class AnimalHillApplication: Application() {
    val database by lazy { RecordRoomDatabase.getDatabase(this)}
    val animalDatabase by lazy {AnimalRoomDatabase.getDatabase(this)}
    lateinit var repository:DataRepoRoom
    lateinit var appNotificationManager: AppNotificationManager
    lateinit var appSyncManager: AppSyncManager

    //    val repository by lazy {
//        //RecordRepository(database.recordDao())
//        DataRepoRoom(database.recordDao())
//    }
    lateinit var preferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        this.preferences = getSharedPreferences(ANIMAL_APP_PREFS_KEY, Context.MODE_PRIVATE)
        this.appNotificationManager = AppNotificationManager(this)
        this.appSyncManager = AppSyncManager(this)
        repository = DataRepoRoom(database.recordDao(),animalDatabase.animalDao())
    }
}