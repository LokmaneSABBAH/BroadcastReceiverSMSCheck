package com.example.brodcastreceiver3;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsMessage;

public class MyReceiverOfSMS extends BroadcastReceiver {
    String number;
    String sms;

    @Override
    public void onReceive(Context context, Intent intent) {

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle b = intent.getExtras();
            if(b!=null){
                Object[] mypdu = (Object[]) b.get("pdus");
                SmsMessage[] smsMessages = new SmsMessage[mypdu.length];
                for(int i=0; i< mypdu.length; i++){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        String f = b.getString("format");
                        smsMessages[i] = SmsMessage.createFromPdu((byte[])mypdu[i], f);
                    }
                    else{
                        smsMessages[i] = SmsMessage.createFromPdu((byte[])mypdu[i]);
                    }

                    sms = smsMessages[i].getMessageBody();
                    number = smsMessages[i].getOriginatingAddress();
                    MainActivity.afficher(sms, number);
                }


            }

        }

    }
}