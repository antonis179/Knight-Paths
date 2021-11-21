package com.example.knight.features.knightmovement.db.knightpath

class KnightPathRepositoryImpl(private val dao: KnightPathDao): KnightPathRepository {

    override suspend fun get(): List<PathEntity>? = dao.get()
    override suspend fun insert(paths: List<PathEntity>) = dao.insert(paths)
    override suspend fun deleteAll() = dao.deleteAll()

}
