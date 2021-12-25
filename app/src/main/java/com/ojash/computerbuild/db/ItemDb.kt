package com.ojash.computerbuild.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ojash.computerbuild.dao.ItemDAO
import com.ojash.computerbuild.entity.Item

@Database(
    entities = [(Item::class)],
    version = 1
)

abstract class ItemDb: RoomDatabase() {

    abstract fun getItemDAO(): ItemDAO
    companion object{
        @Volatile
        private var instance: ItemDb?=null




        fun getInstance(context: Context): ItemDb {
            if (instance==null){
                kotlin.synchronized(UserDb::class) {
                    instance = createDatabase(context)
                }


            }
            return instance!!
        }
        // creating database
        private fun createDatabase(context: Context): ItemDb? {
            return Room.databaseBuilder(
                context.applicationContext,
                ItemDb::class.java,
                "ItemDb"
            ).build()

        }
    }
}