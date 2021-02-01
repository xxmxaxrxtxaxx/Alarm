package com.example.alarm.Services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Telephony
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.alarm.Model.WiadomoscSms

class SerwisSms: BroadcastReceiver()  {
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onReceive(context: Context?, intent: Intent?) {
        if (Telephony.Sms.Intents.SMS_RECEIVED_ACTION == intent?.action) {
            if (context != null) {
                var wiadomosci = arrayListOf<WiadomoscSms>()
                for (wiadomoscSms in Telephony.Sms.Intents.getMessagesFromIntent(intent).sortedBy { m -> "${m.originatingAddress} - ${m.timestampMillis}" }) {
                    var wiadomosciNadawcy = wiadomosci.find { m -> m.nadawca == wiadomoscSms.originatingAddress }
                    if (wiadomosciNadawcy == null) {
                        wiadomosciNadawcy = WiadomoscSms(wiadomoscSms.displayOriginatingAddress,wiadomoscSms.messageBody)
                        wiadomosci.add(wiadomosciNadawcy)
                    }
                    wiadomosciNadawcy.tresc += wiadomoscSms.messageBody
                }
                wiadomosci.forEach({ m ->  instancjaObslugi(context).obsluzWiadomosc(m) })
            }
        }
    }

    companion object {
        private var smsHandler: ObslugaWiadomosci? = null
        fun instancjaObslugi(context: Context): ObslugaWiadomosci {
            if (smsHandler == null) {
                smsHandler = ObslugaWiadomosci(context)
            }
            return smsHandler!!
        }
    }
}