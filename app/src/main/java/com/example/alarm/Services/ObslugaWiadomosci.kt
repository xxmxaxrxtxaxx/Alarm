package com.example.alarm.Services

import android.content.Context
import android.widget.Toast
import com.example.alarm.Model.WiadomoscSms

class ObslugaWiadomosci(private var context: Context) {
    fun obsluzWiadomosc(sms: WiadomoscSms){
        Toast.makeText(context,sms.nadawca + sms.tresc, Toast.LENGTH_LONG).show()
    }

}