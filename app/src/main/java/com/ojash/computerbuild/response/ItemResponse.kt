package com.ojash.computerbuild.response

import com.ojash.computerbuild.entity.Item

data class ItemResponse(
    val success:Boolean?=null,
    val token: String?=null,
    val data: Item?=null

)