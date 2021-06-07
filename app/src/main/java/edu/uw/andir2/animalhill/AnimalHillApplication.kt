package edu.uw.andir2.animalhill

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import edu.uw.andir2.animalhill.model.Record
import edu.uw.andir2.animalhill.model.RecordRoomDatabase
import edu.uw.andir2.animalhill.repository.DataRepoRoom
import edu.uw.andir2.animalhill.repository.RecordRepository

const val ANIMAL_APP_PREFS_KEY = "Animal App Prefs"


class AnimalHillApplication: Application() {
    val database by lazy { RecordRoomDatabase.getDatabase(this) }
    lateinit var repository:DataRepoRoom
//    val repository by lazy {
//        //RecordRepository(database.recordDao())
//        DataRepoRoom(database.recordDao())
//    }
    lateinit var preferences: SharedPreferences

    override fun onCreate() {
        super.onCreate()
        this.preferences = getSharedPreferences(ANIMAL_APP_PREFS_KEY, Context.MODE_PRIVATE)
        repository = DataRepoRoom(database.recordDao())
    }
}