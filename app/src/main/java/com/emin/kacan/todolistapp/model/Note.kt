package com.emin.kacan.todolistapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Note(
  @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id : Int = 0,

 @ColumnInfo(name = "baslik")  var baslik:String,

 @ColumnInfo(name = "icerik")  var icerik:String
) : Serializable {

}