package com.ojash.computerbuild.api

import com.ojash.computerbuild.entity.User
import com.ojash.computerbuild.response.UserGetResponse
import com.ojash.computerbuild.response.UserResponse
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("/user/register")
    suspend fun registerUser(
        @Body user: User
    ):Response<UserResponse>

    @GET("/user/get")
    suspend fun getAllUsers(
        @Header("Authorization") token: String  //authorized user le matra data fetch garna pauxa
    ): Response<UserGetResponse>

    @GET("/updateSingle/user")
    suspend fun getUpdatedUsers(
        @Header("Authorization") token: String
    ):Response<UserResponse>

    @GET("/updateSingle/userwith/{id}")
    suspend fun getUpdatedUser(
        @Header("Authorization") token: String,
        @Path("id") id: String

        ):Response<UserResponse>

    @GET("/get/loginUser")
    suspend fun getLoginUser(
        @Header("Authorization") token: String

    ):Response<UserResponse>


//    Invoke
@FormUrlEncoded
@POST("/user/login")
suspend fun login(
    @Field("username") username: String,
    @Field("password") password: String
): Response<UserResponse>


    @PUT("/user/update")
    suspend fun editUser(
        @Header("Authorization") token: String,  //authorized user le matra data fetch garna pauxa
    @Body user:User
    ): Response<UserResponse>


}