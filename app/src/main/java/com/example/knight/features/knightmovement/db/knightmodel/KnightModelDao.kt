package com.example.knight.features.knightmovement.db.knightmodel

import androidx.room.*

@Dao
interface KnightModelDao {

    @Query("SELECT * FROM knight WHERE id = 'knight'")
    suspend fun get(): KnightModelEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(knight: KnightModelEntity)

    @Update
    suspend fun update(knight: KnightModelEntity)

    @Delete
    suspend fun delete(daos: KnightModelEntity)

}