package edu.uw.andir2.animalhill.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "animal_table")
data class Animal(
    @PrimaryKey(autoGenerate = true) val animalID: Int? = null,
    @ColumnInfo(name = "specis_ID") val SpecisID: String, //will store timestamp as integer. Easier to convert this way
    @ColumnInfo(name = "animal_name") val animalName: String, //will store timestamp as integer. Easier to convert this way
    @ColumnInfo(name = "animal_dscr") val animalDscr: String,
    @ColumnInfo(name = "animal_img") val animalImg: String,
    @ColumnInfo(name = "obtained_datetime") val obtainedDateTime: Long
)
