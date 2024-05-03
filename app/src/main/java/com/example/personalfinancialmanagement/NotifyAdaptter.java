package com.example.personalfinancialmanagement;

import static com.example.personalfinancialmanagement.R.color.white;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.personalfinancialmanagement.Model.Notify;

import java.util.ArrayList;

public class NotifyAdaptter extends ArrayAdapter<Notify> {

    Context context;
    DatabaseHelper db = new DatabaseHelper(getContext());

    int resource;
    ArrayList<Notify> data;


    public NotifyAdaptter(@NonNull Context context, int resource, @NonNull ArrayList<Notify> data) {
        super(context, resource, data);
        this.context =context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView tvNotifyTitle = convertView.findViewById(R.id.tvNotifyTitle);
        TextView tvNotifyDesc = convertView.findViewById(R.id.tvNotifyDesc);
        LinearLayout notifyItem = convertView.findViewById(R.id.notifyItem);
        Notify notify = data.get(position);
        tvNotifyTitle.setText(notify.getTitle());
        tvNotifyDesc.setText(notify.getDescription());

        if(notify.getStatus() == 0  ) {
            notifyItem.setBackgroundColor(Color.GREEN);
        }else if(notify.getStatus() == 1) {
            notifyItem.setBackgroundColor(Color.RED);

        }

        notifyItem.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {

                db = new DatabaseHelper(context);
                db.setReadNotifyStatus(notify.getId_notify());
                notifyItem.setBackgroundColor(Color.RED);

            }


        });


        return convertView;
    }
}
