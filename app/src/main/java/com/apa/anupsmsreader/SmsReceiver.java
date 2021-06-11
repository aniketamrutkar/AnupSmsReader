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
import android.widget.TextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;

public class SmsReceiver extends BroadcastReceiver {

    private  static TextView textView;
    private static String jijuPhone = "9821077656";
    private static String pradnyaPhone = "8369553630";
    private static String anupPhone = "9766518255";

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for(SmsMessage msg : messages){
            String sender = msg.getDisplayOriginatingAddress();
            String messageBody = msg.getMessageBody();
            Log.w("ANUP-SMS","Message Received : " + messageBody);
            try {
                if(messageBody.contains("CDSL-Please use OTP") ||
                        messageBody.contains("W1573") ||
                        messageBody.contains("J77302")) {
                    //sendSmsUsingSMSManager(messageBody, jijuPhone);
                    sendSmsUsingSMSManager(messageBody, pradnyaPhone);
                }
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

    public void sendSmsUsingSMSManager(String messageBody, String phoneNo) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Date currentTime = Calendar.getInstance().getTime();
        messageBody += "\n FROM ANUP SMS MAGIC :)";
        messageBody =  messageBody.length() > 160 ? messageBody.substring(0,160) : messageBody;
        SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(1);
        smsManager.sendTextMessage(phoneNo, null, messageBody, null, null);
        textView.append("\n"+ currentTime+" Message Forwarded !!! to :" + phoneNo +" " + messageBody);
        Log.w("ANUP-SMS", "Message Forwarded !!!");
    }

    public void setLog(TextView textView) {
        SmsReceiver.textView = textView;
    }
}