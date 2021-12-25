package com.ojash.computerbuild.repository

import android.content.Context
import com.ojash.computerbuild.api.MyApiRequest
import com.ojash.computerbuild.api.Servicebuilder
import com.ojash.computerbuild.api.UserApi
import com.ojash.computerbuild.db.UserDb
import com.ojash.computerbuild.entity.User
import com.ojash.computerbuild.response.UserGetResponse
import com.ojash.computerbuild.response.UserResponse

class UserRepository (val context: Context? = null) : MyApiRequest() {

    private val userApi =
        Servicebuilder.buildService(UserApi::class.java)

    //Register user
    suspend fun registerUser(user: User): UserResponse {
        return apiRequest {
            userApi.registerUser(user)
        }
    }


    suspend fun login(username: String, password: String): UserResponse {
        return apiRequest {
            userApi.login(username, password)
        }
    }

    suspend fun getAllUser():UserGetResponse{
        return apiRequest {
            userApi.getAllUsers(Servicebuilder.token!!)
        }
    }

    suspend fun getUpdatedUser():UserResponse{
        return apiRequest {
            userApi.getUpdatedUsers(Servicebuilder.token!!)
        }
    }

    suspend fun getLoginUser():UserResponse{
        return apiRequest {
            userApi.getLoginUser(Servicebuilder.token!!)
        }
    }



    suspend fun editUser(ur:User):UserResponse{
        return apiRequest {
            userApi.editUser(Servicebuilder.token!!,ur)
        }
    }


    suspend fun getUpdateUser(id: String):UserResponse{
        return apiRequest {
            userApi.getUpdatedUser(Servicebuilder.token!!,id)
        }
    }

    suspend fun fetchDataFromDB():List<User>{

        val userDao= UserDb.getInstance(context!!).getUserDAO()
        return userDao.showAllUser()
    }

    suspend fun insertDataToDB(user: User){

        val employeeDao= UserDb.getInstance(context!!).getUserDAO()
        employeeDao.insertApiData(user)
    }

}