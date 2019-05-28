package ru.intera.smscollector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String ACTION = "ru.geekbrains.action.TestReceiver";
    public static final String KEY = "message";
    public RecyclerView recyclerView;
    public SmsAdapter adapter;
    public ArrayList<String> sms;
    public FloatingActionButton fab;
    public AlertDialog.Builder ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initGui();
        initRecyclerView();
        initBroadcast();
        initEvents();
    }

    private void initGui() {
        ad = new AlertDialog.Builder(this);
        sms = new ArrayList<>();
        fab = findViewById(R.id.fab);
        recyclerView = findViewById(R.id.sms_list);
    }

    private void initEvents() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(v);
            }
        });

    }

    private void showDialog(View v) {

        final EditText input = new EditText(this);
        ad.setView(input);
        ad.setTitle("Ваше сообщение");

        ad.setPositiveButton("Отправить",  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String msg = input.getText().toString();
                try {
                    sendMessage(msg);
                }catch (Exception e) {
                    Log.e("Exception", "Field is not empty", e);
                }

            }
        });
        ad.show();
    }

    private void sendMessage(String message) {
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("+79638527412", null, message, null, null);
        Intent sendIntent = new Intent(MainActivity.ACTION);
        sendIntent.putExtra(MainActivity.KEY, message);
        sendBroadcast(sendIntent);
    }

    private void initBroadcast() {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
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

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new SmsAdapter(sms);
        recyclerView.setAdapter(adapter);
    }

}
