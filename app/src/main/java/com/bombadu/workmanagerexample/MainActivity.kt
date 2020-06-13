package com.bombadu.workmanagerexample

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val request = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        button.setOnClickListener {
            WorkManager.getInstance(
                applicationContext
            ).enqueue(request)
        }

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(request.id)
            .observe(this, Observer { workInfo ->
                val status = workInfo!!.state.name
                textView.append("""
                    $status
                    
                    """.trimIndent()
                )
            })
    }
}