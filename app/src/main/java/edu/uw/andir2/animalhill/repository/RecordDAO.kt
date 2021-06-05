package edu.uw.andir2.animalhill.repository

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.uw.andir2.animalhill.model.Record
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDAO {

    @Query("SELECT * FROM record_table ORDER BY startDateTime DESC")
    fun getAllRecords(): List<Record>
    //fun getAllRecords(): Flow<List<Record>>

    // ignores a new record if it's exactly the same as one already in the list.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(record: Record)

    // nope! You never get to delete your study data :P
    //@Query("DELETE FROM record_table")
    //suspend fun deleteAll()
}