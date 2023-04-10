package com.ihsanarslan.swiperefreshlayout.database

import androidx.room.*

@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: NoteDB)

    @Update
    suspend fun update(note: NoteDB)

    @Delete
    suspend fun delete(note: NoteDB)

    @Query("SELECT * from Notes")
    suspend fun getAllNotes(): List<NoteDB>

    @Query("SELECT * FROM Notes WHERE noteTitle = :title AND noteContent = :content AND noteColor = :color AND likedBoolen = :liked")
    suspend fun getNoteById(title: String, content:String, color: Int, liked: Boolean): List<NoteDB>

    @Query("DELETE FROM Notes")
    suspend fun deleteAllNotes()
}