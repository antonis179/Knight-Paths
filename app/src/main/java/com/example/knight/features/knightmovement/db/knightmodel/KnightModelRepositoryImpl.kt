package com.example.knight.features.knightmovement.db.knightmodel

class KnightModelRepositoryImpl(private val modelDao: KnightModelDao): KnightModelRepository {

    override suspend fun get(): KnightModelEntity? = modelDao.get()
    override suspend fun insert(knight: KnightModelEntity) = modelDao.insert(knight)
    override suspend fun update(knight: KnightModelEntity) = modelDao.update(knight)
    override suspend fun delete(knight: KnightModelEntity) = modelDao.delete(knight)

}
