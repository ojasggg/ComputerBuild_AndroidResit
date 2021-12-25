package com.ojash.computerbuild.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @NonNull
    @PrimaryKey
    val _id: String="",
    val username:String?=null,
    val email:String?=null,
    val password:String?=null,
    val location:String?=null,
    val phonenumber:String?=null,
    val profile_pic:String?="asdf",
    val usertype:String="user"


)