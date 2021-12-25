package com.ojash.computerbuild.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ojash.computerbuild.R
import com.ojash.computerbuild.ShowSelectedItem
import com.ojash.computerbuild.entity.Item

class ItemAdapter(
    private val dataList: List<Item>,


    ): RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {

        val itemDescription: TextView =view.findViewById(R.id.tvItemDescription)
        val imageView: ImageView =view.findViewById(R.id.imageView_dashboard)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dashboarddesign,parent,false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item=dataList[position]
        holder.itemDescription.text=item.itemDescription
        var id=item._id
        holder.imageView.setOnClickListener {
            val intent= Intent(holder.itemView.context, ShowSelectedItem::class.java)
            intent.putExtra("id", id)
            holder.imageView.context.startActivity(intent)
        }
    }



    override fun getItemCount(): Int {
        return dataList.size
    }

}