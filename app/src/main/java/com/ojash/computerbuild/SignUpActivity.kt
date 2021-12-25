package com.ojash.computerbuild

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.ojash.computerbuild.entity.User
import com.ojash.computerbuild.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {

    private lateinit var Username: EditText
    private lateinit var Phonenumber: EditText
    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var Location: EditText
    private lateinit var btnSignIn: Button
    private lateinit var ConformPassword: EditText
    private lateinit var btnSignup: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        Username=findViewById(R.id.etUsername)
        Location=findViewById(R.id.etLocation)
        Phonenumber=findViewById(R.id.etPhonenumber)
        Email=findViewById(R.id.etEmail)
        btnSignIn = findViewById(R.id.btnSignIn)

        Password=findViewById(R.id.etPassword)
        ConformPassword=findViewById(R.id.etConformPassword)
        btnSignup=findViewById(R.id.btnSignup)

        btnSignup.setOnClickListener {
            registerUser()


        }

        btnSignIn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            CoroutineScope(Dispatchers.IO).launch {
                withContext(Dispatchers.Main) {
                    val builder = AlertDialog.Builder(this@SignUpActivity)
                    builder.setTitle("Registration successfull")
                    builder.setMessage("Please login and fill your details.")
                    builder.setIcon(android.R.drawable.btn_star)
                    builder.setPositiveButton("Close") { _, _ ->
                        startActivity(
                            Intent(
                                this@SignUpActivity,
                                LoginActivity::class.java
                            )
                        )

                    }
                    val alertDialog: AlertDialog = builder.create()
                    alertDialog.setCancelable(false)
                    alertDialog.show()


                }
            }

        }
    }



    private fun registerUser() {
        val username=Username.text.toString()
        val phonenumber=Phonenumber.text.toString()
        val email=Email.text.toString()
        val password=Password.text.toString()
        val location=Location.text.toString()
        val conformpassword=ConformPassword.text.toString()

        if (password!=conformpassword){
            Password.error="Password wrong "
            Password.requestFocus()
            return


        }
        else{

            val user = User(username =username, password = password,email=email,phonenumber = phonenumber,location = location )


            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val repository = UserRepository(this@SignUpActivity)
                    val response = repository.registerUser(user)
                    if(response.success == true){
                        withContext(Dispatchers.Main){
                            val builder = AlertDialog.Builder(this@SignUpActivity)
                            builder.setTitle("Registration successfull")
                            builder.setMessage("Please login and fill your details.")
                            builder.setIcon(android.R.drawable.btn_star)
                            builder.setPositiveButton("Close") { _, _ ->
                                startActivity(
                                    Intent(this@SignUpActivity,
                                        LoginActivity::class.java

                                    )
                                )
                            }
                            val alertDialog: AlertDialog = builder.create()
                            alertDialog.setCancelable(false)
                            alertDialog.show()


                        }
                    }
                } catch (ex: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignUpActivity, ex.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }

        }

    }
}