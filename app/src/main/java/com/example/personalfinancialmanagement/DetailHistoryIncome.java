package com.example.personalfinancialmanagement;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.personalfinancialmanagement.Model.IncomeDetail;

import java.util.ArrayList;
import java.util.List;

public class DetailHistoryIncome extends AppCompatActivity {
    ListView lvChiTiet;
    DatabaseHelper dbHandler;
    DetailHistoryIncomeAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detail_history_income);

        lvChiTiet = findViewById(R.id.lvChiTiet);

        Intent myIntent = getIntent();
        int id = myIntent.getIntExtra("idThuNhap", -1);
        dbHandler = new DatabaseHelper(this);
        List<IncomeDetail> listIncomeDetail = dbHandler.getIncomeDetailByIdIncome(id);

//        System.out.println(listThuNhap.get(0).toString());
        adapter = new DetailHistoryIncomeAdapter(this, new ArrayList<>(listIncomeDetail));
        lvChiTiet.setAdapter(adapter);
    }
}