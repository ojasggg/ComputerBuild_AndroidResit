package com.ojash.computerbuild.repository

import android.content.Context
import com.ojash.computerbuild.api.ItemApi
import com.ojash.computerbuild.api.MyApiRequest
import com.ojash.computerbuild.api.Servicebuilder
import com.ojash.computerbuild.db.ItemDb
import com.ojash.computerbuild.entity.Item
import com.ojash.computerbuild.response.ItemGetResponse
import com.ojash.computerbuild.response.ItemResponse
import com.ojash.computerbuild.response.SelectedResponse
import com.ojash.computerbuild.response.ViewSelectedResponse

class ItemRepository(val context: Context? = null) : MyApiRequest() {

    private val itemApi =
        Servicebuilder.buildService(ItemApi::class.java)



    suspend fun getAllItems():ItemGetResponse{
        return apiRequest {
            itemApi.getAllItems(Servicebuilder.token!!)
        }
    }

    suspend fun getSelectedItems(id:String):ItemResponse{
        return apiRequest {
            itemApi.getSelectedItems(Servicebuilder.token!!,id)
        }
    }

    suspend fun deleteItem(id:String):SelectedResponse{
        return apiRequest {
            itemApi.deleteItems(Servicebuilder.token!!,id)
        }
    }


    suspend fun selectForItem(id:String): SelectedResponse {
        return apiRequest {
            itemApi.selectItems(Servicebuilder.token!!,id)
        }

    }

    suspend fun showmySelected():ViewSelectedResponse{
        return apiRequest {
            itemApi.showMySelected(Servicebuilder.token!!)
        }
    }


    suspend fun fetchDataFromDB():List<Item>{

        val itemDAO= ItemDb.getInstance(context!!).getItemDAO()
        return itemDAO.showAllItems()
    }

    suspend fun insertDataToDB(item: Item){

        val itemDAO= ItemDb.getInstance(context!!).getItemDAO()
        itemDAO.insertApiData(item)
    }


}