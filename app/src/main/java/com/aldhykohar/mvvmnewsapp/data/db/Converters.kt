package com.aldhykohar.mvvmnewsapp.data.db

import androidx.room.TypeConverter
import com.aldhykohar.mvvmnewsapp.network.model.news.Source


/**
 * Created by aldhykohar on 10/22/2021.
 */
class Converters {

    @TypeConverter
    fun fromSource(source: Source): String {
        return source.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}