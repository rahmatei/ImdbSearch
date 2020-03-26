package com.example.imdb.Util

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.imdb.Pojo.Entity.MovieEntity
import com.example.imdb.Pojo.Entity.ProfileEntity
import com.example.imdb.Repository.Dao.MovieDAO

@Database(entities = [MovieEntity::class,ProfileEntity::class], version = 1)
abstract class DataBase : RoomDatabase() {

    abstract fun getMovieDAO(): MovieDAO

    companion object {
        @Volatile
        private var INSTANCE: DataBase? = null

        fun getDatabase(application: Application): DataBase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    DataBase::class.java,
                    "Movie_database1"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}