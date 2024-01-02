package com.example.scribblenotes.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "notes")
@Parcelize

data class Note(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val notetitle:String,
    val notecontent:String
):Parcelable
