package com.ojash.computerbuild.response

import com.ojash.computerbuild.entity.User

data class UserResponse (
    val success:Boolean?=null,
    val token: String?=null,
    val singleData: User?=null

)