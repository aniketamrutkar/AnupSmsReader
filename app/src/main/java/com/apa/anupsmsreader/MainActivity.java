package com.apa.anupsmsreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logView = (TextView) findViewById(R.id.log_text_view);
        new SmsReceiver().setLog(logView);
        // read permission
        requestSMSPermissions();
    }

    private void requestSMSPermissions() {
        String smspermission = Manifest.permission.RECEIVE_SMS;
        String smssendpermission = Manifest.permission.SEND_SMS;
        int grant = ContextCompat.checkSelfPermission(this,smssendpermission);
        if(grant != PackageManager.PERMISSION_GRANTED){
            String [] permission_list = new String[2];
            permission_list[0] = smspermission;
            permission_list[1] = smssendpermission;
            ActivityCompat.requestPermissions(this,permission_list,1);
        }
    }
}