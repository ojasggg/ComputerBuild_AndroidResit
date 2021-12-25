package com.ojash.computerbuild

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import com.ojash.computerbuild.api.Servicebuilder
import com.ojash.computerbuild.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    private lateinit var linear1: LinearLayout
    private lateinit var animationDrawable: AnimationDrawable
    private lateinit var signup: TextView
    private lateinit var username: AutoCompleteTextView
    private lateinit var password: AutoCompleteTextView
    private lateinit var login: Button
    private var sensorManager: SensorManager? = null
    private var gyroscopeSensor: Sensor? = null
    private var lightSensor: Sensor? = null
    lateinit var notificationManager: NotificationManager


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
//        supportActionBar!!.hide()

        signup = findViewById(R.id.tvForSignup)
        username = findViewById(R.id.etUsername)
        password = findViewById(R.id.etPassword)
        login = findViewById(R.id.btnLogin)


        sensorManager =
            applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        gyroscopeSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        lightSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorManager!!.registerListener(
            gyroscopeSensorListener,
            gyroscopeSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
        sensorManager!!.registerListener(
            LightListener,
            lightSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )

        signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)

        }

        login.setOnClickListener {
            login()

        }




        linear1 = findViewById(R.id.linear)


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun login() {
        val username = username.text.toString()
        val password = password.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = UserRepository(this@LoginActivity)
                val response = repository.login(username, password)
                if (response.success == true) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Successfully Logged IN",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    // Save token
                    Servicebuilder.token = "Bearer ${response.token}"
                    //Save username and password in shared preferences
                    // saveUsernamePassword()
                    val noti = com.ojash.finalproject.Helper.Notification
                    noti.givenotification(this@LoginActivity, "Succesfully Loggedin")




                    startActivity(
                        Intent(
                            this@LoginActivity,
                            MainActivity::class.java
                        )
                    )
                    saveSharedPref()


                    finish()
                }
                else
                {
                    withContext(Dispatchers.Main) {
                        val snack =
                            Snackbar.make(
                                linear1,
                                "Invalid credentials",
                                Snackbar.LENGTH_LONG
                            )
                        snack.setAction("OK", View.OnClickListener {
                            snack.dismiss()
                        })
                        snack.show()
                    }
                }

            } catch (ex: Exception) {
                withContext(Dispatchers.Main) {
                    Log.d("Error", ex.toString())

                    Toast.makeText(
                        this@LoginActivity,
                        ex.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }


    }

    private fun saveSharedPref() {

        val username = username.text.toString()
        val password = password.text.toString()
        val sharedPref = getSharedPreferences("MyPref", MODE_PRIVATE)

        val editor = sharedPref.edit()
        editor.putString("username", username)
        editor.putString("password", password)
        editor.apply()

    }

    fun vibratePhone() {
        val vibrator = this.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }


    //Gyroscope
    private var gyroscopeSensorListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent) {
            val params = this@LoginActivity.window.attributes
            if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                if (event.values[2] > 0.5f) { // anticlockwise
                    val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                    startActivity(intent)
                } else if (event.values[2] < -0.5f) { // clockwise

                }
            }
        }
    }

    // Start can modify system settings panel to let user change the write
    // settings permission.
    private fun changeWriteSettingsPermission(context: Context) {
        val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
        context.startActivity(intent)
    }



    // This function only take effect in real physical android device,
    // it can not take effect in android emulator.
    private fun changeScreenBrightness(
        context: Context,
        screenBrightnessValue: Int
    ) {   // Change the screen brightness change mode to manual.
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        // Apply the screen brightness value to the system, this will change
        // the value in Settings ---> Display ---> Brightness level.
        // It will also change the screen brightness for the device.
        Settings.System.putInt(
            context.contentResolver, Settings.System.SCREEN_BRIGHTNESS, screenBrightnessValue
        )

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun hasWriteSettingsPermission(context: Context): Boolean {
        var ret = true
        // Get the result from below code.
        ret = Settings.System.canWrite(context)
        return ret
    }

    //light sensor

    private var LightListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.M)
        override fun onSensorChanged(event: SensorEvent) {
            val params = this@LoginActivity.window.attributes
            if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                if (event.values[2] > 50) { // anticlockwise

                    // If do not have then open the Can modify system settings panel.
                    // Check whether has the write settings permission or not.
                    val settingsCanWrite = hasWriteSettingsPermission(this@LoginActivity)
                    if (!settingsCanWrite) {
                        changeWriteSettingsPermission(this@LoginActivity)
                    } else {
                        changeScreenBrightness(this@LoginActivity, 1)
                    }
                } else if (event.values[2] < 120) { // clockwise
                    val settingsCanWrite = hasWriteSettingsPermission(this@LoginActivity)
                    if (!settingsCanWrite) {
                        changeWriteSettingsPermission(this@LoginActivity)
                    } else {
                        changeScreenBrightness(this@LoginActivity, 255)
                    }
                }
            }
        }
    }



}