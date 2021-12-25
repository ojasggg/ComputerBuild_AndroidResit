package com.ojash.computerbuild

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ojash.computerbuild.adapter.ItemAppliedAdapter
import com.ojash.computerbuild.repository.ItemRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception


class HomeFragment : Fragment() {

    private var sensorManager: SensorManager? = null
    private var mProximitySensor: Sensor? = null
    private var gyroscopeSensor: Sensor? = null

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val homeFragmentView = layoutInflater.inflate(R.layout.fragment_home, container, false)
        recyclerView = homeFragmentView.findViewById(R.id.recyclerview1)


        return homeFragmentView
    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun getData() {


        val repository = ItemRepository(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = repository.showmySelected()
                if (response.success == true) {
                    val itemdatalist = response.data!!


//
                    println("****************************************************************")
                    println(itemdatalist)
                    withContext(Dispatchers.Main){
                        recyclerView.layoutManager= LinearLayoutManager(requireContext())
                        recyclerView.adapter=  ItemAppliedAdapter(itemdatalist,requireContext())
                    }
                }
            } catch (ex: Exception) {
                print(ex)
            }
        }

    }
}