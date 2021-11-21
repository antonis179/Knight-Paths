package com.example.knight.features.knightmovement.db.knightpath


interface KnightPathRepository {

    suspend fun get(): List<PathEntity>?

    suspend fun insert(paths: List<PathEntity>)

    suspend fun deleteAll()

}