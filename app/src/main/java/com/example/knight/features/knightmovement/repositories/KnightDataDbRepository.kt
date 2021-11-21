package com.example.knight.features.knightmovement.repositories

import com.example.knight.features.knightmovement.KnightDataModel
import com.example.knight.features.knightmovement.db.knightmodel.KnightModelEntity
import com.example.knight.features.knightmovement.db.knightmodel.KnightModelRepository
import com.example.knight.features.knightmovement.db.knightpath.KnightPathRepository
import com.example.knight.features.knightmovement.db.knightpath.PathEntity


interface KnightDataDbRepository {

    suspend fun retrieveKnightDataModel(): KnightDataModel?

    suspend fun updateKnightDataModel(model: KnightDataModel)

    suspend fun insertKnightDataModel(model: KnightDataModel)

}

class DefaultKnightDataDbRepository(
    private val knightModelDb: KnightModelRepository,
    private val knightPathDb: KnightPathRepository
) : KnightDataDbRepository {

    override suspend fun retrieveKnightDataModel(): KnightDataModel? = knightModelDb.get()?.model?.apply {
        knightPathDb.get()?.let { paths += it.map { entity -> entity.path } }
    }

    override suspend fun updateKnightDataModel(model: KnightDataModel) {
        knightPathDb.deleteAll()
        knightModelDb.update(KnightModelEntity(model = model.cloneWithoutPaths()))
        knightPathDb.insert(model.paths.mapIndexed { i, path -> PathEntity(i, path) })
    }

    override suspend fun insertKnightDataModel(model: KnightDataModel) {
        knightPathDb.deleteAll()
        knightModelDb.insert(KnightModelEntity(model = model.cloneWithoutPaths()))
        knightPathDb.insert(model.paths.mapIndexed { i, path -> PathEntity(i, path) })
    }

}