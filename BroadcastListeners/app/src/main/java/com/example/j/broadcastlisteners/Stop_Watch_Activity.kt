package com.example.j.broadcastlisteners

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.example.j.broadcastlisteners.R.mipmap.ic_play
import org.w3c.dom.Text

class Stop_Watch_Activity : AppCompatActivity() {
    lateinit var addView: View
    lateinit var mTimeView: TextView
    lateinit var inflater: LayoutInflater
    lateinit var btnStart: ImageButton
    lateinit var btnSplit: ImageButton
    lateinit var btnReset: ImageButton
    lateinit var textTimer: TextView
    lateinit var startText: TextView
    lateinit var mHandler: Handler
    lateinit var container: LinearLayout

    var timeinmillisec: Long = 0L
    var updateTime: Long = 0L
    var startTime: Long = 0L
    var buffTime: Long = 0L
    var sec: Int = 0
    var min: Int = 0
    var millisec: Int = 0


    private val updateTimerThread = object : Runnable {
        override fun run() {
            timeinmillisec = SystemClock.uptimeMillis() - startTime
            updateTime = buffTime + timeinmillisec
            sec = (updateTime / 1000).toInt()
            min = sec / 60
            sec %= 60
            millisec = (updateTime % 1000).toInt()
            textTimer.text = ("" + min + ":"
                    + String.format("%02d", sec) + ":"
                    + String.format("%3d", millisec))
            mHandler.postDelayed(this, 0)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stop_watch)
        mHandler = Handler()
        startText= findViewById<TextView>(R.id.startTV)
        btnStart = findViewById<ImageButton>(R.id.startBtn)
        btnSplit = findViewById<ImageButton>(R.id.splitBtn)
        btnReset = findViewById<ImageButton>(R.id.resetBtn)
        textTimer = findViewById<TextView>(R.id.timerValue)
        container = findViewById<LinearLayout>(R.id.linear)

        var playRes = R.drawable.ic_play
        var pauseRes = R.drawable.ic_pause
        var currentHolder= playRes
        btnStart.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                when(currentHolder){
                    playRes->{
                        btnStart.setImageResource(pauseRes)
                        startTime = SystemClock.uptimeMillis()
                        mHandler.postDelayed(updateTimerThread, 0)
                        currentHolder=pauseRes
                        startText.setText("PAUSE")
                }
                    pauseRes->{
                            btnStart.setImageResource(playRes)
                            buffTime += timeinmillisec
                            mHandler.removeCallbacks(updateTimerThread)
                            currentHolder=playRes
                        startText.setText("RESUME")


                        }

                }
            }
        })

        btnReset.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                btnStart.setImageResource(R.drawable.ic_play)
                buffTime += timeinmillisec
                mHandler.removeCallbacks(updateTimerThread)
                timeinmillisec = 0L
                startTime = 0L
                buffTime = 0L
                updateTime = 0L
                sec = 0
                min = 0
                millisec = 0
                textTimer.text = "00:00:00"
                startText.setText("START")
                container.removeAllViews()
            }
        })
        btnSplit.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                inflater = baseContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                 addView = inflater.inflate(R.layout.activity_time_list, null)
                mTimeView= addView.findViewById(R.id.textContent)
                mTimeView.text = textTimer.text.toString()
                container.addView(addView)
            }
        })
    }
    fun toast(){

        Toast.makeText(this,"gwapoyoyo",Toast.LENGTH_SHORT).show()
    }
}
