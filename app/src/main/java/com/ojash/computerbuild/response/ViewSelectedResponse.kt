package com.ojash.computerbuild.response

import com.ojash.computerbuild.entity.Selected

data class ViewSelectedResponse(
    val success:Boolean?=null,
    val data:MutableList<Selected>?=null,


    )