package com.ojash.computerbuild.response

import com.ojash.computerbuild.entity.Item

data class ItemGetResponse(
    val success: Boolean?=null,
    val data:MutableList<Item>?=null,

    )
