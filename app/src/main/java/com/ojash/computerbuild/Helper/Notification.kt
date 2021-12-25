package com.ojash.finalproject.Helper
import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build


import androidx.annotation.RequiresApi
import com.ojash.computerbuild.MainActivity


object Notification {
    @RequiresApi(Build.VERSION_CODES.O)
    fun givenotification(context: Context, message: String?) {
        val NOTIFICATION_ID = 234
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var CHANNEL_ID: String? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CHANNEL_ID = "my_channel_01"
            val name: CharSequence = "my_channel"
            val Description = "This is my channel"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = Description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)
        }
        val builder = Notification.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.bottom_bar)
            .setTicker("Custom Build")
            .setWhen(System.currentTimeMillis())
            .setAutoCancel(true).setContentTitle("Custom Build")
            .setContentText(message)
        val resultIntent = Intent(context, MainActivity::class.java)
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(context)
        stackBuilder.addParentStack(MainActivity::class.java)
        stackBuilder.addNextIntent(resultIntent)
        val resultPendingIntent: PendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(resultPendingIntent)
        notificationManager.notify(NOTIFICATION_ID, builder.build())

    }
}