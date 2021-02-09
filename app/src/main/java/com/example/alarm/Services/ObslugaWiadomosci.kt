package com.example.alarm.Services

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import com.example.alarm.Model.WiadomoscSms
import com.example.alarm.R
import com.example.alarm.View.AlarmView

const val WIADOMOSC = "com.example.alarm.WIADOMOSC"


class ObslugaWiadomosci(private var context: Context) {
    fun obsluzWiadomosc(sms: WiadomoscSms){
        val i = Intent(context, AlarmView::class.java)
        i.putExtra(WIADOMOSC, sms.tresc)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(i)

    }

}