package edu.uw.andir2.animalhill.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import edu.uw.andir2.animalhill.model.Record

// Annotates class to be a Room Database with a table (entity) of the Record class
@Database(entities = arrayOf(Record::class), version = 1, exportSchema = false)
public abstract class AnimalHillRoomDatabase : RoomDatabase() {
    abstract fun recordDao(): RecordDAO

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AnimalHillRoomDatabase? = null

        fun getDatabase(context: Context): AnimalHillRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimalHillRoomDatabase::class.java,
                    "animalHill_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}