package edu.uw.andir2.animalhill.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimalDao {
    @Query("SELECT * FROM animal_table ORDER BY animalID DESC")
    fun getAllAnimals(): List<Animal>

    // ignores a new record if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAnimal(animal: Animal)

    @Query("DELETE FROM animal_table")
    suspend fun deleteAnimals()
}