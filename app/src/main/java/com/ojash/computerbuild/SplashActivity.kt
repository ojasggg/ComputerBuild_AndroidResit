package com.ojash.computerbuild

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.ojash.computerbuild.api.Servicebuilder
import com.ojash.computerbuild.repository.UserRepository
import com.ojash.finalproject.Helper.Notification
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        CoroutineScope(Dispatchers.IO).launch {
            login()

//            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
//            putExtra(atvEmailLog,toString());
//            putExtra(atvPasswordLog,toString());
//            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun login() {
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)
        val username = sharedPref.getString("username", "")
        val password = sharedPref.getString("password", "")
        withContext(Dispatchers.IO) {
            try {

                val repository = UserRepository(this@SplashActivity)
                val response = repository.login(username!!, password!!)
                if (response.success == true) {
                    Servicebuilder.token = "Bearer ${response.token}"

                    startActivity(
                        Intent(
                            this@SplashActivity,
                            MainActivity::class.java
                        )
                    )
                    val a= Notification

                    a.givenotification(this@SplashActivity,"Welcome Back")
                    finish()
                } else {
                    withContext(Dispatchers.Main) {
                        startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    }
                }
            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                }
            }
        }
    }

}