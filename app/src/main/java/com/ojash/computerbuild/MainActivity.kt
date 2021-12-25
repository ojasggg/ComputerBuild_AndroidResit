package com.ojash.computerbuild

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var sensorManager: SensorManager? = null
    private var mProximitySensor: Sensor? = null
    private var gyroscopeSensor: Sensor? = null

    lateinit var proximitySensor: TextView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_ComputerBuild)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.fragmentContainerView)


        val bottomnavigationview=findViewById<BottomNavigationView>(R.id.bottomNavigation)

        bottomnavigationview.setupWithNavController(navController)
        proximitySensor = findViewById(R.id.proximitySensor)

        //Sensor Implementation
        sensorManager = applicationContext.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        mProximitySensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_PROXIMITY)
        gyroscopeSensor = sensorManager!!.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (mProximitySensor == null) {
            proximitySensor.text = "No Proximity Sensor!"
        } else {
            sensorManager!!.registerListener(
                proximitySensorEventListener,
                mProximitySensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        sensorManager!!.registerListener(gyroscopeSensorListener,gyroscopeSensor,SensorManager.SENSOR_DELAY_NORMAL)









    }


    fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {


    }

    fun onSensorChanged(event: SensorEvent?) {
        val values=event!!.values
        val xAxis=values[0]
        val yAxis=values[1]
        val zAxis=values[2]

        if (zAxis >0.5){
            Toast.makeText(
                this@MainActivity,
                "Accelerometer triggered",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private var proximitySensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent) {
            val params = this@MainActivity.window.attributes
            if (event.sensor.type == Sensor.TYPE_PROXIMITY) {

                if (event.values[0] == 0f) {
                    params.flags = params.flags or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    params.screenBrightness = 0f
                    window.attributes = params
                    Log.d("low Fragment", "low brightness")
                } else {
                    params.flags = params.flags or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    params.screenBrightness = -1f
                    window.attributes = params

                }
            }
        }
    }
    private var gyroscopeSensorListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

        }
        override fun onSensorChanged(event: SensorEvent) {
            val params = this@MainActivity.window.attributes
            if (event.sensor.type == Sensor.TYPE_GYROSCOPE) {
                if(event.values[2] > 0.5f) { // anticlockwise
                    //Toast.makeText(this@MainActivity,"message",Toast.LENGTH_SHORT).show()
                } else if(event.values[2] < -0.5f) { // clockwise
                    //Toast.makeText(this@MainActivity,"text",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

}
