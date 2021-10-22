package com.aldhykohar.mvvmnewsapp.data.db

import android.content.Context
import androidx.room.*
import com.aldhykohar.mvvmnewsapp.network.model.news.Articles


/**
 * Created by aldhykohar on 10/22/2021.
 */

@Database(
    entities = [Articles::class],
    version = 1
)

@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {

    abstract fun getArticleDao(): ArticleDao

    companion object {
        @Volatile
        private var instance: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            ArticleDatabase::class.java,
            "article_db.db"
        ).build()
    }
}