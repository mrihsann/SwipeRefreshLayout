package com.ihsanarslan.swiperefreshlayout.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Notes")
data class NoteDB(
    @PrimaryKey(autoGenerate = true)
    var id:Int=0,

    @ColumnInfo(name = "noteTitle")
    var title: String,

    @ColumnInfo(name = "noteContent")
    var content: String,

    @ColumnInfo(name = "noteColor")
    var color: Int,

    @ColumnInfo(name = "likedBoolen")
    var liked: Boolean

)