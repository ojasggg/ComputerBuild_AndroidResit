package com.ojash.computerbuild.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ojash.computerbuild.dao.UserDAO
import com.ojash.computerbuild.entity.User

@Database(
    entities = [(User::class)],
    version = 1
)

abstract class UserDb: RoomDatabase() {

    abstract fun getUserDAO(): UserDAO
    companion object{
        @Volatile
        private var instance: UserDb?=null

        fun getInstance(context: Context): UserDb {
            if (instance==null){
                kotlin.synchronized(UserDb::class) {
                    instance = createDatabase(context)
                }

            }
            return instance!!
        }
        // creating database
        private fun createDatabase(context: Context): UserDb? {
            return Room.databaseBuilder(
                context.applicationContext,
                UserDb::class.java,
                "UserDb"
            ).build()

        }
    }
}