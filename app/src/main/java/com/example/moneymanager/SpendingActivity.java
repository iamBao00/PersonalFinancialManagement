package com.example.moneymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.moneymanager.Model.JarDetail;
import com.example.moneymanager.Model.Spending;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SpendingActivity extends AppCompatActivity {
    private int idCurrentUserLogin;
    DatabaseHelper db;
    String ngayThang;
    EditText edtSoTienChiTieu, edtNgayThangChiTieu, edtMoTaChiTieu;
    Spinner spinnerHuChiTieu;
    TextView tvLuuChiTieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_spending);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        SharedPreferences prefs = getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentUserLogin = prefs.getInt("idUserCurrent", -1);
        Toast.makeText(this, "idUser" + idCurrentUserLogin, Toast.LENGTH_SHORT).show();
//        Intent myIntent = getIntent();
//        idCurrentUserLogin =  myIntent.getIntExtra("userId", -1);

        db = new DatabaseHelper(this);
        setControl();
        setEvent();

    }

    private void setControl() {
        // Lấy dữ liệu từ các trường nhập liệu
        edtSoTienChiTieu = findViewById(R.id.edtSoTienChiTieu);

        edtNgayThangChiTieu = findViewById(R.id.edtNgayThangChiTieu);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            ngayThang = LocalDate.now().toString();
            edtNgayThangChiTieu.setText(ngayThang);
        }
        edtMoTaChiTieu = findViewById(R.id.edtMoTaChiTieu);
        spinnerHuChiTieu = findViewById(R.id.spinnerHuChiTieu);
        tvLuuChiTieu = findViewById(R.id.tvLuuChiTieu);
    }

    private void setEvent() {
        List<String> huuList = new ArrayList<>();
        huuList.add("Chi tiêu cần thiết");
        huuList.add("Tiết kiệm dài hạn");
        huuList.add("Quỹ giáo dục");
        huuList.add("Hưởng thụ");
        huuList.add("Quỹ tự do tài chính");
        huuList.add("Quỹ từ thiện");

        // Tạo Adapter cho Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, huuList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHuChiTieu.setAdapter(adapter);

        tvLuuChiTieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get So Tien
                if(edtSoTienChiTieu.getText().toString().isEmpty()) {
                    Toast.makeText(SpendingActivity.this, "Vui lòng nhập số tiền!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Long soTien = Long.parseLong(edtSoTienChiTieu.getText().toString());
                if(soTien < 1000){
                    Toast.makeText(SpendingActivity.this, "Số tiền thu nhập quá nhỏ không đáng kể, vui lòng nhập số tiền trên 1000đ", Toast.LENGTH_SHORT).show();
                    edtSoTienChiTieu.selectAll();
                    edtSoTienChiTieu.requestFocus();
                    return;
                }

                // get Ngay Thang
                ngayThang = edtNgayThangChiTieu.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false); // Chế độ không linh hoạt
                if(ngayThang.equals("")){
                    Toast.makeText(SpendingActivity.this, "Vui lòng nhập tay ngày tháng theo định dạng yyyy-mm-dd.", Toast.LENGTH_SHORT).show();
                    edtNgayThangChiTieu.selectAll();
                    edtNgayThangChiTieu.requestFocus();
                    return;
                }
                try {
                    sdf.parse(ngayThang);
                } catch (ParseException e) {
                    Toast.makeText(SpendingActivity.this, "Ngày Tháng Hiện Không Đúng Định Dạng yyyy-mm-dd, vui lòng hiệu chỉnh.", Toast.LENGTH_SHORT).show();
                    edtNgayThangChiTieu.selectAll();
                    edtNgayThangChiTieu.requestFocus();
                    return;
                }

                String moTa = edtMoTaChiTieu.getText().toString();

                String selectedHu = spinnerHuChiTieu.getSelectedItem().toString();

                int idJar = 1;
                if ("Chi tiêu cần thiết".equals(selectedHu)) {
                    idJar = 1;
                } else if ("Tiết kiệm dài hạn".equals(selectedHu)) {
                    idJar = 2;
                } else if ("Quỹ giáo dục".equals(selectedHu)) {
                    idJar = 3;
                } else if ("Hưởng thụ".equals(selectedHu)) {
                    idJar = 4;
                } else if ("Quỹ tự do tài chính".equals(selectedHu)) {
                    idJar = 5;
                } else if ("Quỹ từ thiện".equals(selectedHu)) {
                    idJar = 6;
                }
//                Log.d("1", String.valueOf(idCurrentUserLogin));
//                Log.d("2", String.valueOf(idJar));

//                idCurrentUserLogin = 1;
                int idJardetail = db.getIdJarDetail(new JarDetail(null, idCurrentUserLogin, idJar, null, null));



                Spending spending = new Spending(null, idJardetail, soTien, moTa, ngayThang);
                long l = db.addSpending(spending);
                if (l == -1) {
                    Toast.makeText(SpendingActivity.this, "Thêm Thất bại", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SpendingActivity.this, "Thêm Thành Công", Toast.LENGTH_SHORT).show();
                }

                JarDetail jarDetail = new JarDetail(null,idCurrentUserLogin,idJar, db.getJarDetail(new JarDetail(null,idCurrentUserLogin,idJar,null,null)).getMoney()-soTien,null);
                db.updateJarDetail(jarDetail);
                finish();
            }
        });
    }
}