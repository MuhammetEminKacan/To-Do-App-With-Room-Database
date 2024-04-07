package com.emin.kacan.todolistapp.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.emin.kacan.todolistapp.model.Note
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface NoteDao {

    @Query("SELECT * FROM Note")
    fun GetAll() : Flowable<List<Note>>

    @Insert
    fun insert(note : Note) : Completable

    @Delete
    fun delete(note: Note) : Completable

    @Update
    fun update(note: Note) : Completable
}