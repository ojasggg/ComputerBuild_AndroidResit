package com.ojash.computerbuild

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.ojash.computerbuild.entity.User
import com.ojash.computerbuild.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class UpdateProfile : AppCompatActivity() {
    private lateinit var etUpdateUsername: EditText
    private lateinit var etUpdateLastName: EditText
    private lateinit var etUpdateAge: EditText
    private lateinit var etUpdatePhone: EditText
    private lateinit var etUpdateAddress: EditText
    private lateinit var etUpdateUserbio: EditText
    private lateinit var tvUpdateEmail: TextView
    private lateinit var btnApplyEdit: Button
    public  lateinit var id:String
    public lateinit var dataList:List<User>



    override fun onCreate(savedInstanceState: Bundle?) {   /// to get updated data
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)

        etUpdateUserbio = findViewById(R.id.etUpdateUserbio)
        etUpdatePhone = findViewById(R.id.etUpdatePhone)
        etUpdateAge = findViewById(R.id.etUpdateAge)
        etUpdateAddress = findViewById(R.id.etUpdateAddress)
        tvUpdateEmail = findViewById(R.id.tvUpdateEmail)
//        etUpdateUserbio = findViewById(R.id.tvUpdateUserbio)
        btnApplyEdit = findViewById(R.id.btnApplyEdit)

        etUpdateUsername = findViewById(R.id.etUpdateFirstName)
        etUpdateLastName=findViewById(R.id.etUpdateLastName)
//        imgUpdateProf = findViewById(R.id.imgUpdateProf)





        val repository = UserRepository(this@UpdateProfile)
        try {
            CoroutineScope(Dispatchers.Main).launch {



                val response = repository.getLoginUser()
                id=response.singleData!!._id
                if (response.success == true) {
                    val employee = response.singleData!!
                    etUpdateUsername.setText(employee.username)



                }


//
//                        withContext(Dispatchers.Main) {
//                            recyclerView.adapter = EmployeeAdapter(dataList)
//                            recyclerView.layoutManager = LinearLayoutManager(requireContext())
//                        }
            }

        } catch (ex: Exception) {
            print(ex)
        }

        btnApplyEdit.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {



                val repository = UserRepository()
                val username = etUpdateUsername.text.toString()
                val address = etUpdateAddress.text.toString()
                val phonenumber = etUpdatePhone.text.toString()


                val response = repository.editUser(
                    User(
                        _id = id,
                        username = username,
                        phonenumber = phonenumber,
                        location = address,

                    )
                )


                if (response.success == true) {

                    Toast.makeText(
                        this@UpdateProfile,
                        response.singleData!!.toString(), Toast.LENGTH_SHORT
                    ).show()


                } else {
                    withContext(Dispatchers.Main) {
                    }
                }


            }

        }
    }
}