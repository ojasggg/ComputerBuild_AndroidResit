package com.ojash.computerbuild

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ojash.computerbuild.adapter.ItemAdapter
import com.ojash.computerbuild.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class DashBoardFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val dashBoardFragmentView= layoutInflater.inflate(R.layout.fragment_dash_board,container,false)
        recyclerView= dashBoardFragmentView.findViewById(R.id.recyclerview2)



        val repository = ItemRepository(requireContext()   )
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.getAllItems()
                print(response)
                if (response.success == true) {
                    val itemdatalist = response.data!!


                    for (items in itemdatalist) {
                        repository.insertDataToDB(items)
                    }
                    val dataList=repository.fetchDataFromDB()
                    println("****************************************************************")
                    println(itemdatalist)
                    withContext(Dispatchers.Main){
                        recyclerView.layoutManager= GridLayoutManager(requireContext(),2)
                        recyclerView.adapter=  ItemAdapter(dataList)
                    }
                }
            } catch (ex: Exception) {
                print(ex)
            }
        }


        return dashBoardFragmentView
    }
}