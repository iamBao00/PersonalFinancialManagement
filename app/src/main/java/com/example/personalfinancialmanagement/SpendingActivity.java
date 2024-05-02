package com.example.personalfinancialmanagement;

import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.example.personalfinancialmanagement.Model.JarDetail;
import com.example.personalfinancialmanagement.Model.Spending;

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
//        Toast.makeText(this, "idUser" + idCurrentUserLogin, Toast.LENGTH_SHORT).show();
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
        huuList.add("Thiết Yếu");
        huuList.add("Giáo Dục");
        huuList.add("Tiết Kiệm");
        huuList.add("Hưởng Thụ");
        huuList.add("Đầu Tư");
        huuList.add("Thiện Tâm");

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
                if ("Thiết Yếu".equals(selectedHu)) {
                    idJar = 1;
                } else if ("Giáo Dục".equals(selectedHu)) {
                    idJar = 2;
                } else if ("Tiết Kiệm".equals(selectedHu)) {
                    idJar = 3;
                } else if ("Hưởng Thụ".equals(selectedHu)) {
                    idJar = 4;
                } else if ("Đầu Tư".equals(selectedHu)) {
                    idJar = 5;
                } else if ("Thiện Tâm".equals(selectedHu)) {
                    idJar = 6;
                }
//                Log.d("1", String.valueOf(idCurrentUserLogin));
//                Log.d("2", String.valueOf(idJar));

//                idCurrentUserLogin = 1;
                int idJardetail = db.getIdJarDetail(new JarDetail(null, idCurrentUserLogin, idJar, null, null));

                long soDu = db.getJarDetail(new JarDetail(null, idCurrentUserLogin, idJar, null, null)).getMoney();
                if(soDu < soTien){
                    Toast.makeText(SpendingActivity.this, "Số dư hủ không đủ!", Toast.LENGTH_SHORT).show();
                    return;
                }

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