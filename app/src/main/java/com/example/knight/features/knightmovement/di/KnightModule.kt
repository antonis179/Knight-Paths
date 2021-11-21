package com.example.knight.features.knightmovement.di

import com.example.knight.db.AppDatabase
import com.example.knight.ui.delegateadapter.Data
import com.example.knight.ui.transformers.ViewDataTransformer
import com.example.knight.features.knightmovement.KnightDataModel
import com.example.knight.features.knightmovement.db.knightmodel.KnightModelDao
import com.example.knight.features.knightmovement.db.knightmodel.KnightModelRepository
import com.example.knight.features.knightmovement.db.knightmodel.KnightModelRepositoryImpl
import com.example.knight.features.knightmovement.db.knightpath.KnightPathDao
import com.example.knight.features.knightmovement.db.knightpath.KnightPathRepository
import com.example.knight.features.knightmovement.db.knightpath.KnightPathRepositoryImpl
import com.example.knight.features.knightmovement.repositories.BoardSizeRepository
import com.example.knight.features.knightmovement.repositories.DefaultBoardSizeRepository
import com.example.knight.features.knightmovement.repositories.DefaultKnightDataDbRepository
import com.example.knight.features.knightmovement.repositories.KnightDataDbRepository
import com.example.knight.features.knightmovement.transformers.KnightViewDataTransformer
import com.example.knight.features.knightmovement.usecases.BacktrackingPathCalculationUsecase
import com.example.knight.features.knightmovement.usecases.PathCalculationUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class KnightModule {

    @Provides
    @ViewModelScoped
    fun provideViewTransformer(): ViewDataTransformer<KnightDataModel, Data> = KnightViewDataTransformer()

    @Provides
    @ViewModelScoped
    fun provideBoardSizeRepo(): BoardSizeRepository = DefaultBoardSizeRepository()

    @Provides
    @ViewModelScoped
    fun provideKnightDataDbRepo(modelDb: KnightModelRepository, pathDb: KnightPathRepository): KnightDataDbRepository = DefaultKnightDataDbRepository(modelDb, pathDb)

    @Provides
    @ViewModelScoped
    fun providePathCalculation(): PathCalculationUsecase = BacktrackingPathCalculationUsecase()


    @Provides
    @ViewModelScoped
    fun provideKnightDao(appDatabase: AppDatabase): KnightModelDao = appDatabase.knightDao()

    @Provides
    @ViewModelScoped
    fun provideKnightRepository(dao: KnightModelDao): KnightModelRepository = KnightModelRepositoryImpl(dao)

    @Provides
    @ViewModelScoped
    fun provideKnightPathDao(appDatabase: AppDatabase): KnightPathDao = appDatabase.knightPathDao()

    @Provides
    @ViewModelScoped
    fun provideKnightPathRepository(dao: KnightPathDao): KnightPathRepository = KnightPathRepositoryImpl(dao)

}