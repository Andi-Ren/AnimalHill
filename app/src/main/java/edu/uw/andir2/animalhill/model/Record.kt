package edu.uw.andir2.animalhill.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo

@Entity(tableName = "record_table")
data class Record(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "start_time") val startTime: Long, //will store timestamp as integer. Easier to convert this way
    @ColumnInfo(name = "end_time") val endTime: Long, //will store timestamp as integer. Easier to convert this way
    @ColumnInfo(name = "status") val status: Boolean,
    @ColumnInfo(name = "obtained_animal") val obtainedAnimal: String
)


//enum class Status {
//    SUCCESS, CANCELED
//}
