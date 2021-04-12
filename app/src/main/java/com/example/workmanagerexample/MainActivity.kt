package com.example.workmanagerexample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val KEY = "KEY TASK DESC"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val textView = findViewById<TextView>(R.id.textView)

        val data = Data.Builder()
                .putString(KEY, "Sending Work data here")
                .build()

        val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .build()


        val request = OneTimeWorkRequestBuilder<MyWorker>()
                .setInputData(data)
                .setConstraints(constraints)
                .build()

        button.setOnClickListener(View.OnClickListener {
            WorkManager.getInstance(applicationContext).enqueue(request)
        })

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(request.id)
                .observe(this, Observer<WorkInfo> { z ->
                    z?.let {
                        if (z.state.isFinished) {
                            val outputData = z.outputData
                            val outputString = outputData.getString(MyWorker.KEY_RECEIVE)

                            textView.append(outputString+"\n")
                        }
                    }
                    textView.append(z.state.name + "\n")
                })

    }


}