package com.example.personalfinancialmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.personalfinancialmanagement.Model.Spending;

import java.util.ArrayList;

public class HistorySpending extends AppCompatActivity {
    DatabaseHelper db;
    ArrayList<Spending> spendingList;
    ListView lvHistorySpending;
    int idCurrentUserLogin;
    SpendingAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.layout_history_spending);

        lvHistorySpending = findViewById(R.id.lvLichSuChiTieu);

        db = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentUserLogin = prefs.getInt("idUserCurrent", -1);

        spendingList = db.getSpendingByUserId(idCurrentUserLogin);

        // Tạo adapter mới và gán cho ListView
        adapter = new SpendingAdapter(this, spendingList);
        lvHistorySpending.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        spendingList = db.getSpendingByUserId(idCurrentUserLogin);
        adapter = new SpendingAdapter(this, spendingList);
        lvHistorySpending.setAdapter(adapter);
    }
}
