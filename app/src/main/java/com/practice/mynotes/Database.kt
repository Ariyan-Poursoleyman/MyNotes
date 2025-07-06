package com.practice.mynotes

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Database

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class Database : RoomDatabase() {

    abstract fun noteDAO(): NoteDAO

    companion object {
        @Volatile
        private var INSTANCE: com.practice.mynotes.Database? = null

        fun getInstance(context: Context): com.practice.mynotes.Database {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    com.practice.mynotes.Database::class.java,
                    "notes_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}