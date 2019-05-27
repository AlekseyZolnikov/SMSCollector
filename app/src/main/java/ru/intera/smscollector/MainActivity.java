package ru.intera.smscollector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsMessage;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "ru.geekbrains.action.TestReceiver";
    private static final String KEY = "message";
    private BroadcastReceiver broadcastReceiver;
    public RecyclerView recyclerView;
    private ArrayList<String> sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sms = new ArrayList<>();

        recyclerView = findViewById(R.id.sms_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        final SmsAdapter adapter = new SmsAdapter(sms);
        recyclerView.setAdapter(adapter);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String answer = intent.getStringExtra(KEY);
                sms.add(answer);
                adapter.notifyDataSetChanged();
            }
        };

        IntentFilter intFilter = new IntentFilter(ACTION);
        registerReceiver(broadcastReceiver, intFilter);
    }

}
