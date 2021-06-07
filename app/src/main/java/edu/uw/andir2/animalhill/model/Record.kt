package edu.uw.andir2.animalhill.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_table")
data class Record(
    @PrimaryKey(autoGenerate = true) val recordID: Int,
    val startDateTime: Int, //will store timestamp as integer. Easier to convert this way
    val endDateTime: Int, //will store timestamp as integer. Easier to convert this way
    val finished: Boolean,
    val animalEarned: List<Animal>
)

