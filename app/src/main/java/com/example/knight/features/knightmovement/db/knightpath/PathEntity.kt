package com.example.knight.features.knightmovement.db.knightpath

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.knight.features.knightmovement.models.Path

@Entity(tableName = "paths")
data class PathEntity(
    @PrimaryKey
    var id: Int,
    val path: Path
)