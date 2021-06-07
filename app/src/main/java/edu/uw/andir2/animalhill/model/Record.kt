package edu.uw.andir2.animalhill.model

import android.icu.text.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//data class Record(
//    val startTime: DateFormat,
//    val endTime: DateFormat,
//    val obtainedAnimal: String,
//    val status: Status
//)

@Entity(tableName = "record_table")
class Record(
    @PrimaryKey(autoGenerate = true) val uid: Int? = null,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end_time") val endTime: String,
    @ColumnInfo(name = "status") val status: String,
)

enum class Status {
    SUCCESS, CANCELED
}
