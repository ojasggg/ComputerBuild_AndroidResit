package com.ojash.computerbuild.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ojash.computerbuild.R
import com.ojash.computerbuild.entity.Selected
import com.ojash.computerbuild.repository.ItemRepository
import com.ojash.finalproject.Helper.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ItemAppliedAdapter(
    private val dataList: List<Selected>,
    private val context: Context,


    ): RecyclerView.Adapter<ItemAppliedAdapter.ItemSelectedViewHolder>() {

    class ItemSelectedViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val jobdesc: TextView =view.findViewById(R.id.tvAppliedTitle)
        val btndelete: TextView =view.findViewById(R.id.btnDeleteApplied)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemSelectedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.selected,parent,false)
        return ItemSelectedViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ItemSelectedViewHolder, position: Int) {
        val item=dataList[position]
        holder.jobdesc.text=item.itemDescription
        var id=item._id
        holder.btndelete.setOnClickListener {

            val builder = android.app.AlertDialog.Builder(context)
            builder.setTitle("Delete application?")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Yes") { _, _ ->
                if (id != null) {
                    deleteApplied(id)



                }
                Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
            }
            builder.setNegativeButton("No") { _, _ ->
                Toast.makeText(context, "Action cancelled", Toast.LENGTH_SHORT).show()
            }
            val alertDialog: android.app.AlertDialog = builder.create()
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun deleteApplied(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val repository = ItemRepository()
                val response = repository.deleteItem(id)
                if (response.success==true) {
                    Toast.makeText(
                        context,
                        "Deleted successfully", Toast.LENGTH_SHORT
                    ).show()
                    val d= Notification
                    d.givenotification(context,"Succesfully deleted")

                } else {
                    withContext(Dispatchers.Main) {
//                    Toast.makeText(
//                            context,
//                            "Cannot delete.", Toast.LENGTH_SHORT
//                    ).show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
//                Toast.makeText(
//                        context,
//                        "Error", Toast.LENGTH_SHORT
//                ).show()

                }
            }
        }
    }


    override fun getItemCount(): Int {
        return dataList.size
    }

}
