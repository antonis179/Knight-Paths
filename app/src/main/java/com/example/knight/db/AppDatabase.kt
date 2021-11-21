package com.example.knight.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.knight.features.knightmovement.db.KnightDataModelConverter
import com.example.knight.features.knightmovement.db.knightmodel.KnightModelDao
import com.example.knight.features.knightmovement.db.knightmodel.KnightModelEntity
import com.example.knight.features.knightmovement.db.knightpath.KnightPathDao
import com.example.knight.features.knightmovement.db.knightpath.PathEntity

@Database(
    entities = [
        KnightModelEntity::class,
        PathEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(KnightDataModelConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun knightDao(): KnightModelDao
    abstract fun knightPathDao(): KnightPathDao

    companion object {
        private const val APP_DATABASE = "app_db"

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, APP_DATABASE)
                .addTypeConverter(KnightDataModelConverter())
                .build()
        }
    }
}