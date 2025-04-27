package com.example.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "directories")
data class Directory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val label: String = "no label",
)
