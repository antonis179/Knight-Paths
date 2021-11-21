package com.example.knight.features.knightmovement.db.knightmodel

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.knight.features.knightmovement.KnightDataModel

@Entity(tableName = "knight")
data class KnightModelEntity(
    @PrimaryKey
    val id: String = "knight",
    val model: KnightDataModel
)