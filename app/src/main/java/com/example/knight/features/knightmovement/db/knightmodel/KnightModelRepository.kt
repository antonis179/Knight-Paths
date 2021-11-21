package com.example.knight.features.knightmovement.db.knightmodel


interface KnightModelRepository {

    suspend fun get(): KnightModelEntity?

    suspend fun insert(knight: KnightModelEntity)

    suspend fun update(knight: KnightModelEntity)

    suspend fun delete(knight: KnightModelEntity)

}