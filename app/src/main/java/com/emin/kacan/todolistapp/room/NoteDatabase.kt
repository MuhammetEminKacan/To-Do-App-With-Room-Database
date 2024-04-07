package com.emin.kacan.todolistapp.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.emin.kacan.todolistapp.model.Note

@Database(entities = [Note ::class], version = 1)
abstract class NoteDatabase : RoomDatabase(){
    abstract fun noteDao() : NoteDao
}