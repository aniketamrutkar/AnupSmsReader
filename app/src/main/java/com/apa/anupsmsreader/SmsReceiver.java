package com.apa.anupsmsreader;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SmsReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for(SmsMessage msg : messages){
            String sender = msg.getDisplayOriginatingAddress();
            String messageBody = msg.getMessageBody();
            Log.w("ANUP-SMS","Message Received : " + messageBody);
            try {
                sendSmsUsingSMSManager(messageBody);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //sendSms(messageBody);
        }
    }

    public void sendSmsUsingSMSManager(String messageBody) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if(messageBody.contains("CDSL-Please use OTP") ||
                messageBody.contains("W1573") ||
                messageBody.contains("J77302"))
        {
            messageBody += " \n FROM ANUP SMS MAGIC :)";
            messageBody =  messageBody.substring(0,160);
            SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(2);
            smsManager.sendTextMessage("9821077656", null, messageBody, null, null);
            Log.w("ANUP-SMS", "Message Forwarded !!!");
        }
    }
}