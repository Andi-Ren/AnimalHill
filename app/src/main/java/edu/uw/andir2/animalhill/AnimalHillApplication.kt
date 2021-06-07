package edu.uw.andir2.animalhill

import android.app.Application
import edu.uw.andir2.animalhill.model.Record
import edu.uw.andir2.animalhill.model.RecordRoomDatabase
import edu.uw.andir2.animalhill.repository.RecordRepository

class AnimalHillApplication: Application() {
    val database by lazy { RecordRoomDatabase.getDatabase(this) }
    val repository by lazy {
        RecordRepository(database.recordDao())
    }


}