package com.ojash.computerbuild

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.ojash.computerbuild.repository.ItemRepository
import com.ojash.finalproject.Helper.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ShowSelectedItem : AppCompatActivity() {

    private lateinit var itemTitle1: TextView
    private lateinit var itemType1: TextView
    private lateinit var itemPrice1: TextView
    private lateinit var itemCategory1: TextView

    private lateinit var itemCategoryInsert: TextView
    private lateinit var itemTypeInsert: TextView
    private lateinit var itemPriceInsert: TextView
    private lateinit var itemdescc: TextView
    private lateinit var btnApply: Button

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_selected_item)
        val intent= intent
        if (intent.extras!=null){
            val value=intent.getStringExtra("id")
            if (value != null) {
                getJobs(value)
            }
            Toast.makeText(this, "Selected $value", Toast.LENGTH_SHORT).show()
        }

        itemTitle1=findViewById(R.id.itemTitle1)
        itemType1=findViewById(R.id.itemType1)
        itemPrice1=findViewById(R.id.itemPrice1)
        itemCategory1=findViewById(R.id.itemCategory1)
        btnApply=findViewById(R.id.buttonApply)

        // value halni
        itemCategoryInsert=findViewById(R.id.itemCategoryInsert)
        itemTypeInsert=findViewById(R.id.itemTypeInsert)
        itemPriceInsert=findViewById(R.id.itemPriceInsert)
        itemdescc=findViewById(R.id.itemdescc)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun applyForJob(id:String, jobtitle:String){

        CoroutineScope(Dispatchers.IO).launch {
            val repository=ItemRepository(this@ShowSelectedItem)

            val response=repository.selectForItem(id)
            if (response.success==true){
                CoroutineScope(Dispatchers.Main).launch{
                    Toast.makeText(this@ShowSelectedItem, "Successfully selected $jobtitle", Toast.LENGTH_SHORT).show()


                }
                val b= Notification

                b.givenotification(this@ShowSelectedItem,"Successfully selected $jobtitle item.")
//                startActivity(Intent(this@ShowSelectedItem,HomeFragment::class.java).putExtra("id",id))

            }

        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getJobs(value:String){
        val repository= ItemRepository(this@ShowSelectedItem)


        try {
            CoroutineScope(Dispatchers.Main).launch{
                val response=repository.getSelectedItems(value)
                if(response!=null){
                    if (response.success==true){
                        itemTitle1.setText(response.data!!.itemDescription)
                        itemCategoryInsert.setText(response.data!!.itemCategory)
                        itemTypeInsert.setText(response.data!!.itemType)
                        itemPriceInsert.setText(response.data!!.itemPrice).toString()
                        itemdescc.setText(response.data!!.itemDescription).toString()
                        var jobtitle=response.data!!.itemDescription


                        btnApply.setOnClickListener {
                            val builder = android.app.AlertDialog.Builder(this@ShowSelectedItem)
                            builder.setTitle("Add Item to Cart?")
                            builder.setMessage("Are you sure you want to Add ${(response.data!!.itemDescription)} item to cart ??")
                            builder.setIcon(android.R.drawable.ic_dialog_alert)
                            builder.setPositiveButton("Yes") { _, _ ->
                                if (value != null) {
                                    if (jobtitle != null) {
                                        applyForJob(value,jobtitle)
                                    }

                                }
                                Toast.makeText(this@ShowSelectedItem, "Item added successfully", Toast.LENGTH_SHORT).show()
                            }
                            builder.setNegativeButton("No") { _, _ ->
                                Toast.makeText(this@ShowSelectedItem, "Action cancelled", Toast.LENGTH_SHORT).show()
                            }
                            val alertDialog: android.app.AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()

                        }

                    }

                }
                else{
                    Toast.makeText(this@ShowSelectedItem, "error", Toast.LENGTH_SHORT).show()
                    print("error")
                }

            }

        }catch (ex: Exception){
            print(ex)

        }





    }


}