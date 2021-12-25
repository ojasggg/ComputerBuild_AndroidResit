package com.ojash.computerbuild.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ojash.computerbuild.entity.Item

@Dao
interface ItemDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApiData(item: Item)

    @Query("Select * from Item")
    suspend fun showAllItems():List<Item>
}