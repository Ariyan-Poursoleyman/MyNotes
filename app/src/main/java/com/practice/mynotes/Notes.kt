package com.practice.mynotes
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity (tableName = "tbl_notes")
@Parcelize
data class Notes(
    @PrimaryKey(autoGenerate = true)
    var id : Int =0,
    var subject : String,
    var description : String?,
    val timestamp: Long = System.currentTimeMillis()
) : Parcelable
