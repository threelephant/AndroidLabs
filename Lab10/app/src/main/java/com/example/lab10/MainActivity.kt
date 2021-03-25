package com.example.lab10

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var myService: TimeService? = null
    var isBound = false
    var receiver: BroadcastReceiver? = null

    private val myConnection = object : ServiceConnection {

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as TimeService.MyBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                val counter = intent?.getIntExtra("counter", 0)
                val textCounter = findViewById<TextView>(R.id.textCounter)
                textCounter.text = counter.toString()
            }
        }

        val filter = IntentFilter(BROADCAST_TIME_EVENT)
        registerReceiver(receiver, filter)
    }

    override fun onDestroy() {
        unregisterReceiver(receiver)
        super.onDestroy()
    }

    fun buttonStartService(view: View) {
        val initialCount = findViewById<EditText>(R.id.initial)
            .text.toString().toInt()
        val interval = findViewById<EditText>(R.id.interval)
            .text.toString().toInt()

        findViewById<TextView>(R.id.textCounter).text = initialCount.toString()

        val intent = Intent(this, TimeService::class.java)
        intent.putExtra("initialCount", initialCount)
        intent.putExtra("interval", interval)

        startService(intent)
        bindService(Intent(this, TimeService::class.java),
            myConnection, Context.BIND_AUTO_CREATE)
    }

    fun buttonStopService(view: View) {
        stopService(Intent(this, TimeService::class.java))
        unbindService(myConnection)
    }

    fun buttonGetValue(view: View) {
        if (isBound) {
            findViewById<TextView>(R.id.textCounter).text =
                myService!!.getCounter().toString()
        }
    }
}