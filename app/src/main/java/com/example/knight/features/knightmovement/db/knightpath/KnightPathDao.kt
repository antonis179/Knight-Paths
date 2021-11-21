package com.example.knight.features.knightmovement.db.knightpath

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface KnightPathDao {

    @Query("SELECT * FROM paths")
    suspend fun get(): List<PathEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(paths: List<PathEntity>)

    @Query("DELETE FROM paths")
    fun deleteAll()

}