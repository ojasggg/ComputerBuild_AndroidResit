package com.ojash.computerbuild

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ojash.computerbuild.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyProfileFragment: Fragment(){

    private lateinit var tvUserName: TextView
    private lateinit var tvLastName: TextView
    private lateinit var tvProfileUserbio: TextView
    private lateinit var tvProfilephone: TextView
    private lateinit var tvProfileAge: TextView
    private lateinit var tvProfileAddress: TextView
    private lateinit var tvProfileExperience: TextView
    private lateinit var tvProfileProjects: TextView

    private lateinit var tvEmail: TextView
    private lateinit var btnEditProfile: TextView

    private lateinit var profileIMG: ImageView
    private lateinit var imgLogout: ImageView
    lateinit var id:String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)
        val profileFragmentView= layoutInflater.inflate(R.layout.fragment_my_profile,container,false)

        tvUserName = profileFragmentView.findViewById(R.id.tvFirstName)

        tvProfileUserbio = profileFragmentView.findViewById(R.id.tvProfileUserbio)
        tvProfilephone = profileFragmentView.findViewById(R.id.tvProfilephone)
        tvProfileAge = profileFragmentView.findViewById(R.id.tvProfileAge)
        tvProfileAddress = profileFragmentView.findViewById(R.id.tvProfileAddress)
        tvProfileExperience = profileFragmentView.findViewById(R.id.tvProfileExperience)
        tvProfileProjects = profileFragmentView.findViewById(R.id.tvProfileProjects)
        tvEmail = profileFragmentView.findViewById(R.id.tvemailProfile)
        btnEditProfile = profileFragmentView.findViewById(R.id.btnEditProfile)
        profileIMG=profileFragmentView.findViewById(R.id.profileIMG)
        imgLogout=profileFragmentView.findViewById(R.id.imgLogout)
        getData()



        imgLogout.setOnClickListener {
//            logout()
            Toast.makeText(activity, "Logged out successfully", Toast.LENGTH_LONG).show()

            startActivity(
                Intent(
                    activity,
                    LoginActivity::class.java
                )
            )
            val sharedPref = requireContext().getSharedPreferences("MyPref", AppCompatActivity.MODE_PRIVATE)

            val editor = sharedPref.edit()
            editor.putString("username", "")
            editor.putString("password", "")
            editor.apply()
        }

        btnEditProfile.setOnClickListener {
            startActivity(
                Intent(
                    context,
                    UpdateProfile::class.java
                )
            )
        }



        return profileFragmentView
    }

    override fun onResume() {
        getData()
        super.onResume()
    }

    private fun getData() {

        CoroutineScope(Dispatchers.Main).launch {
            val repository= UserRepository()

//
            val response = repository.getUpdatedUser()
//            id=response.singleData!!._id

            if (response.success == true) {
                val data = response.singleData!!
                tvUserName.text = data.username
//                tvUserName.setText(data.username)
//                tvProfileUserbio.setText(data.userbio)

            } else {
//
                withContext(Dispatchers.Main) {
//

                    Toast.makeText(
                        context,
                        "Welcome", Toast.LENGTH_LONG
                    ).show()
                }
//
//
            }
        }
    }
}