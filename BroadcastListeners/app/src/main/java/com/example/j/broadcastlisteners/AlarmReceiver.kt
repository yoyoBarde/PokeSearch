package com.example.j.broadcastlisteners

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by Romeo on 2/11/2018.
 */
class AlarmReceiver:BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        var getResult:String = intent!!.getStringExtra("extra")

        var services = Intent(context, RingtonService::class.java)
                services.putExtra("extra",getResult)
                context!!.startService(services)

    }

}