package com.example.j.broadcastlisteners

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ClipData
import android.content.ClipData.Item
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import java.util.*
import android.support.annotation.NonNull
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    lateinit var am: AlarmManager
    lateinit var tp:TimePicker
    lateinit var update_text:TextView
    lateinit var con: Context
    lateinit var btnStart: Button
    lateinit var btnStop:Button
    var hour:Int =0
    var min:Int = 0
    lateinit var pi:PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myStopIntent = Intent(this, Stop_Watch_Activity::class.java)
        con=this
        am = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            tp = findViewById<TimePicker>(R.id.timePicker)
        update_text = findViewById<TextView>(R.id.updateText)
        btnStart =  findViewById<Button>(R.id.startAlarm)
        btnStop = findViewById<Button>(R.id.stopAlarm)

        var calendar: Calendar = Calendar.getInstance()
        var myIntent:Intent = Intent(this,AlarmReceiver::class.java)
        var stopwIntent:Intent = Intent(this,Stop_Watch_Activity::class.java)
        var myBtmNav = findViewById<BottomNavigationView>(R.id.bottomBar)
        myBtmNav.setOnNavigationItemReselectedListener { item->
            when (item.itemId){
                R.id.Alarm->{
                    Toast.makeText(this,"Alarm Mode",Toast.LENGTH_SHORT).show()


                }
                R.id.stopWatch->{
                    startActivity(stopwIntent)


                }


            }



        }
        btnStart.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    calendar.set(Calendar.HOUR_OF_DAY,tp.hour)
                    calendar.set(Calendar.MINUTE,tp.minute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = tp.hour
                    min = tp.minute
                }
                else{
                    calendar.set(Calendar.HOUR_OF_DAY,tp.currentHour)
                    calendar.set(Calendar.MINUTE,tp.currentMinute)
                    calendar.set(Calendar.SECOND,0)
                    calendar.set(Calendar.MILLISECOND,0)
                    hour = tp.currentHour
                    min = tp.currentMinute
                }
                var hourStr = hour.toString()
                var minStr = min.toString()
                if(hour>12)
                {
                    hourStr = (hour - 12).toString()

                }
                if (min<10)
                {
                    minStr = "0$min"
                }
                update_text.text = "Alarm set to: $hourStr : $minStr"
                          pi = PendingIntent.getBroadcast(this@MainActivity,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT)
                    am.setExact(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,pi)
                myIntent.putExtra("extra","on")

            }
        })


        btnStop.setOnClickListener(object : View.OnClickListener{

            override fun onClick(p0: View?) {
                update_text.text = "Alarm Off"
                myIntent.putExtra("extra","off")
                pi = PendingIntent.getBroadcast(this@MainActivity,0,myIntent,PendingIntent.FLAG_UPDATE_CURRENT)
                am.cancel(pi)
                sendBroadcast(myIntent)
            }
        })
    }




}
