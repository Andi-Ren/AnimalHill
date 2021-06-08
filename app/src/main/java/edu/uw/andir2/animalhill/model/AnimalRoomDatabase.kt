package edu.uw.andir2.animalhill.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Animal::class), version = 1, exportSchema = false)
abstract class AnimalRoomDatabase : RoomDatabase()  {
    abstract fun animalDao(): AnimalDao
    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AnimalRoomDatabase? = null

        fun getDatabase(context: Context): AnimalRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnimalRoomDatabase::class.java,
                    "animal_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}