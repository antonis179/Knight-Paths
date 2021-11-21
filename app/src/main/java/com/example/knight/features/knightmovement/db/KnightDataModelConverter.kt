package com.example.knight.features.knightmovement.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.knight.features.knightmovement.KnightDataModel
import com.example.knight.features.knightmovement.models.Path
import com.google.gson.Gson


@ProvidedTypeConverter
class KnightDataModelConverter {

    private val gson by lazy { Gson() }

    @TypeConverter
    fun jsonToKnight(model: String?): KnightDataModel? = gson.fromJson(model, KnightDataModel::class.java)

    @TypeConverter
    fun knightToJson(model: KnightDataModel?): String? = gson.toJson(model)

    @TypeConverter
    fun jsonToPath(model: String?): Path? = gson.fromJson(model, Path::class.java)

    @TypeConverter
    fun pathToJson(model: Path?): String? = gson.toJson(model)

}