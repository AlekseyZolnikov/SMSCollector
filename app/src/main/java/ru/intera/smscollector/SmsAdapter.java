package ru.intera.smscollector;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.MyViewHolder> {

    private ArrayList<String> smsList;

    public SmsAdapter(ArrayList<String> smsList) {
        this.smsList = smsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sms_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.bind(smsList.get(i));
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        final TextView textView;

        private MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.sms_text);

        }

        private void bind(String text) {
            textView.setText(text);
        }
    }
}
