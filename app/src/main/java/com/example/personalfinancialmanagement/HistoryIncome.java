package com.example.personalfinancialmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ThemeUtils;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.personalfinancialmanagement.Model.Income;
import com.example.personalfinancialmanagement.Model.JarDetail;

import java.util.ArrayList;
import java.util.List;

public class HistoryIncome extends AppCompatActivity {
    private ListView lvLichSuThuNhap;
    private DatabaseHelper db;
    IncomeAdapter adapter;
    int idCurrentUserLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_income);

        setControl();

        db = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentUserLogin = prefs.getInt("idUserCurrent", -1);

        List<Integer> listIdJarDetail = new ArrayList<Integer>();
        listIdJarDetail.add(db.getIdJarDetail(new JarDetail(null,idCurrentUserLogin,1,null,null)));
        listIdJarDetail.add(db.getIdJarDetail(new JarDetail(null,idCurrentUserLogin,2,null,null)));
        listIdJarDetail.add(db.getIdJarDetail(new JarDetail(null,idCurrentUserLogin,3,null,null)));
        listIdJarDetail.add(db.getIdJarDetail(new JarDetail(null,idCurrentUserLogin,4,null,null)));
        listIdJarDetail.add(db.getIdJarDetail(new JarDetail(null,idCurrentUserLogin,5,null,null)));
        listIdJarDetail.add(db.getIdJarDetail(new JarDetail(null,idCurrentUserLogin,6,null,null)));

        List<Integer> listIdIcome = db.getAllIdIncomeByListIdJarDetail(listIdJarDetail);

        ArrayList<Income> listIncome = new ArrayList<>(db.getAllIncomeByListIdIncome(listIdIcome));
        // Tạo adapter mới và gán cho ListView
        adapter = new IncomeAdapter(this, listIncome);
        lvLichSuThuNhap.setAdapter(adapter);

        setEvent();

    }

    private void setEvent() {

    }

    private void setControl() {
        lvLichSuThuNhap = findViewById(R.id.lvLichSuThuNhap);
    }
}