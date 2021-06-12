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
    //private static String usage = "Papa";
    private static String usage = "Jiju";
    private static String jijuPhone = "9821077656";
    private static String pradnyaPhone = "8369553630";
    private static String anupPhone = "9766518255";
    private static String papaPhone = "8390906135";

    @Override
    public void onReceive(Context context, Intent intent) {
        SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);

        for(SmsMessage msg : messages){
            String sender = msg.getDisplayOriginatingAddress();
            String messageBody = msg.getMessageBody();
            Log.w("ANUP-SMS","Message Received : " + messageBody);
            try {
                if(usage.equalsIgnoreCase("Jiju") && (messageBody.contains("CDSL-Please use OTP") ||
                        messageBody.contains("W1573") ||
                        messageBody.contains("J77302"))) {
                    SmsManager smsManager = SmsManager.getSmsManagerForSubscriptionId(1);
                    sendSmsUsingSMSManager(messageBody, smsManager, jijuPhone);
                    //sendSmsUsingSMSManager(messageBody, smsManager, pradnyaPhone);
                }else if(usage.equalsIgnoreCase("Papa") && (messageBody.contains("OTP") || messageBody.contains("otp"))){
                    SmsManager smsManager = SmsManager.getDefault();
                    sendSmsUsingSMSManager(messageBody, smsManager, anupPhone);
                    //sendSmsUsingSMSManager(messageBody, smsManager, pradnyaPhone);
                }
            } catch (Exception e) {
                Log.w("ANUP-SMS-ERROR","Error in forwarding SMS : " + messageBody);
                e.printStackTrace();
            }
            //sendSms(messageBody);
        }
    }

    public void sendSmsUsingSMSManager(String messageBody, SmsManager smsManager, String phoneNo) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Date currentTime = Calendar.getInstance().getTime();
        messageBody += "\n FROM ANUP SMS MAGIC :)";
        messageBody =  messageBody.length() > 160 ? messageBody.substring(0,160) : messageBody;
        smsManager.sendTextMessage(phoneNo, null, messageBody, null, null);
        textView.append("\n"+ currentTime+" Message Forwarded !!! to :" + phoneNo +" " + messageBody);
        Log.w("ANUP-SMS", "Message Forwarded !!!");
    }

    public void setLog(TextView textView) {
        SmsReceiver.textView = textView;
    }
}