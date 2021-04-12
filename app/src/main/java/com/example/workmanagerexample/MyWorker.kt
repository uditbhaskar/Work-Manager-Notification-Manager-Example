package com.example.workmanagerexample

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.NonNull
import androidx.core.app.NotificationCompat
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

@Suppress("SameParameterValue")
class MyWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    companion object {
        const val KEY_RECEIVE = "RECEIVING DATA"
    }

    @NonNull
    override fun doWork(): Result {

        val data = inputData

        val desc = data.getString(MainActivity.KEY)



        desc?.let { displayNotification("Working on notifications.", it) }

        val data1 = Data.Builder()
                .putString(KEY_RECEIVE, "WORK DATA RECEIVED")
                .build()

        return Result.success(data1)
    }

    @SuppressLint("WrongConstant")
    private fun displayNotification(task: String, desc: String) {
        val manager: NotificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("TestingWorkManger", "TestingWorkManger", NotificationManager.IMPORTANCE_MAX)
            manager.createNotificationChannel(notificationChannel)
        }

        val builder = NotificationCompat.Builder(applicationContext, "TestingWorkManger")
                .setContentTitle(task)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.ic_launcher_round)

        manager.notify(1, builder.build())
    }

}