package com.example.hungrygo.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.hungrygo.DataUtils
import com.example.hungrygo.R
import com.example.hungrygo.app.model.Item_Orders
import com.example.hungrygo.app.model.Item_Orders.Companion.Get_item_Orders_res
import com.example.hungrygo.app.model.appUser_restaurant
import com.example.hungrygo.app.model.appUser_restaurant.Companion.getusers_res
import com.example.hungrygo.splash
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener

class MyForegroundService : Service() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForegroundService()
        Log.d("dd", "Service created")

        getusers_res(EventListener { value, error ->
            if (error != null) {
                Log.e(ContentValues.TAG, "Listen failed.", error)
                return@EventListener
            }
            if (value != null) {
                val items = value.toObjects(appUser_restaurant::class.java)
                val newlist = mutableListOf<appUser_restaurant>()
                for (i in items) {
                    if (i.order == true)
                        newlist.add(i)
                }
                for (i in newlist) {
                    Get_item_Orders_res(i.id!!, EventListener { value, error ->
                        if (error != null) {
                            Log.e(ContentValues.TAG, "Error fetching item orders.", error)
                            return@EventListener
                        }

                        if (value != null) {
                            for (dc in value.documentChanges) {
                                when (dc.type) {
                                    DocumentChange.Type.ADDED -> {
                                        val userId = Firebase.auth.currentUser?.uid
                                        if (DataUtils.appuser_Delivery?.id == userId && !isAppInForeground() && userId != null) {
                                            sendNotification(getString(R.string.new_order), dc.document.data["location"].toString())
                                        }
                                    }
                                    DocumentChange.Type.MODIFIED -> Log.d(TAG, "Modified order: ${dc.document.data}")
                                    DocumentChange.Type.REMOVED -> Log.d(TAG, "Removed order: ${dc.document.data}")
                                }
                            }
                        } else {
                            Log.d(ContentValues.TAG, "Current data: null")
                        }
                    })

                }

            } else {
                Log.d(ContentValues.TAG, "Current data: null")
            }
        })



    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "firestore_service_channel"
            val channelName = "Firestore Service Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("ForegroundServiceType")
    private fun startForegroundService() {
        val channelId = "firestore_service_channel"
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.app_name))
            .setContentText(getString(R.string.notifications_enabled))
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val serviceType = ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION // Adjust type as needed
            startForeground(1, notification, serviceType)
        } else {
            startForeground(1, notification)
        }
    }


    private fun isAppInForeground(): Boolean {
        val app = applicationContext as language_app
        return app.isInForeground
    }

    private fun sendNotification(title: String, message: String) {
        val channelId = "firestore_channel_id"
        val channelName = "Firestore Channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, channelName, importance)
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        val intent = Intent(this, splash::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.delivery_res)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true) // Ensure auto-cancel is set

        notificationManager.notify(0, builder.build())
    }



    override fun onBind(intent: Intent): IBinder? {
        return null
    }

}