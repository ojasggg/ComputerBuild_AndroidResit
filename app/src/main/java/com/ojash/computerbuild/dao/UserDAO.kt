package com.ojash.computerbuild.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ojash.computerbuild.entity.User

@Dao
interface UserDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE) // id conflict nahos vanyera
    // this function insert data in Employeedb
    suspend fun insertApiData(user: User)

    @Query("Select * from User")
    suspend fun showAllUser():List<User>
}