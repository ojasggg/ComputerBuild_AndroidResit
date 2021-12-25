package com.ojash.computerbuild.response

import com.ojash.computerbuild.entity.User

data class UserGetResponse (
    val success: Boolean?=null,
    val data:MutableList<User>?=null,
)