package com.ojash.computerbuild.api

import com.ojash.computerbuild.response.ItemGetResponse
import com.ojash.computerbuild.response.ItemResponse
import com.ojash.computerbuild.response.SelectedResponse
import com.ojash.computerbuild.response.ViewSelectedResponse
import retrofit2.Response
import retrofit2.http.*


interface ItemApi {

    @GET("/show/items")
    suspend fun getAllItems(
        @Header("Authorization") token: String  //authorized user le matra data fetch garna pauxa
    ): Response<ItemGetResponse>


    @GET("/updateSingle/items/{id}")
    suspend fun getSelectedItems(
        @Header("Authorization") token: String,
        @Path("id") id: String

    ): Response<ItemResponse>

    @POST("/item/selectedItem/{id}")
    suspend fun selectItems(
        @Header("Authorization") token: String,
        @Path("id") id:String
    ): Response<SelectedResponse>

    @GET("/showmy/selected")
    suspend fun showMySelected(
        @Header("Authorization") token :String
    ): Response<ViewSelectedResponse>

    @DELETE("/delete/selectedItem/{id}")
    suspend fun deleteItems(
        @Header("Authorization") token :String,
        @Path("id") id:String
    ): Response<SelectedResponse>






}