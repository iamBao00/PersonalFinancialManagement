package com.example.moneymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.moneymanager.Model.Income;
import com.example.moneymanager.Model.IncomeDetail;
import com.example.moneymanager.Model.JarDetail;

import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;

public class AddIncomeActivity extends AppCompatActivity {
    private int idCurrentUserLogin;
    DatabaseHelper db;
    EditText edtSoTien, edtNgayThang, edtMoTa, edtCoCauThietYeu, edtCoCauGiaoDuc, edtCoCauTietKiem, edtCoCauHuongThu, edtCoCauDauTu, edtCoCauThienTam;
    TextView tvLuuThuNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_income);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Intent myIntent = getIntent();
        idCurrentUserLogin =  myIntent.getIntExtra("userId", -1);

        db = new DatabaseHelper(this);
        setControl();
        setEvent();

    }

    private void setEvent() {
        tvLuuThuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get So Tien
                if(edtSoTien.getText().toString().isEmpty()) {
                    Toast.makeText(AddIncomeActivity.this, "Vui lòng nhập số tiền!", Toast.LENGTH_SHORT).show();
                    return;
                }
                long soTien = Long.parseLong(edtSoTien.getText().toString());
                if(soTien < 1000){
                    Toast.makeText(AddIncomeActivity.this, "Số tiền thu nhập quá nhỏ không đáng kể, vui lòng nhập số tiền trên 1000đ", Toast.LENGTH_SHORT).show();
                    edtSoTien.selectAll();
                    edtSoTien.requestFocus();
                    return;
                }

                // get Ngay Thang
                String ngayThang = edtNgayThang.getText().toString();

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                sdf.setLenient(false); // Chế độ không linh hoạt
                if(ngayThang.equals("")){
                    Toast.makeText(AddIncomeActivity.this, "Vui lòng nhập tay ngày tháng theo định dạng yyyy-mm-dd.", Toast.LENGTH_SHORT).show();
                    edtNgayThang.selectAll();
                    edtNgayThang.requestFocus();
                    return;
                }
                try {
                    sdf.parse(ngayThang);
                } catch (ParseException e) {
                    Toast.makeText(AddIncomeActivity.this, "Ngày Tháng Hiện Không Đúng Định Dạng yyyy-mm-dd, vui lòng hiệu chỉnh.", Toast.LENGTH_SHORT).show();
                    edtNgayThang.selectAll();
                    edtNgayThang.requestFocus();
                    return;
                }

                // get Mo Ta
                String moTa = edtMoTa.getText().toString();

                int thietYeu = Integer.parseInt(edtCoCauThietYeu.getText().toString());
                int giaoDuc = Integer.parseInt(edtCoCauGiaoDuc.getText().toString());
                int tietKiem = Integer.parseInt(edtCoCauTietKiem.getText().toString());
                int huongThu = Integer.parseInt(edtCoCauHuongThu.getText().toString());
                int dauTu = Integer.parseInt(edtCoCauDauTu.getText().toString());
                int thienTam = Integer.parseInt(edtCoCauThienTam.getText().toString());
                if(thietYeu+giaoDuc+tietKiem+huongThu+dauTu+thienTam != 100) {
                    Toast.makeText(AddIncomeActivity.this, "Tổng cơ cấu phân chia phải là 100%.\nVui Lòng Hiệu Chỉnh.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Them data vao bang income
                Income income = new Income(null,soTien,moTa,ngayThang);
                long idIncome = db.addIncome(income);
                // Them data vao DetailIncome
                addIncomeDetail(1,thietYeu, idIncome, soTien);
                addIncomeDetail(2,giaoDuc, idIncome, soTien);
                addIncomeDetail(3,tietKiem, idIncome, soTien);
                addIncomeDetail(4,huongThu, idIncome, soTien);
                addIncomeDetail(5,dauTu, idIncome, soTien);
                addIncomeDetail(6,thienTam, idIncome, soTien);
                // Update Jar Detail
                JarDetail jarDetail = new JarDetail(null,idCurrentUserLogin,1,soTien*thietYeu/100,null);
                db.updateJarDetail(jarDetail);
                jarDetail = new JarDetail(null,idCurrentUserLogin,2,soTien*giaoDuc/100,null);
                db.updateJarDetail(jarDetail);
                jarDetail = new JarDetail(null,idCurrentUserLogin,3,soTien*tietKiem/100,null);
                db.updateJarDetail(jarDetail);
                jarDetail = new JarDetail(null,idCurrentUserLogin,4,soTien*huongThu/100,null);
                db.updateJarDetail(jarDetail);
                jarDetail = new JarDetail(null,idCurrentUserLogin,5,soTien*dauTu/100,null);
                db.updateJarDetail(jarDetail);
                jarDetail = new JarDetail(null,idCurrentUserLogin,6,soTien*thienTam/100,null);
                db.updateJarDetail(jarDetail);
                finish();
            }
        });
    }
    private void addIncomeDetail(int idJar, int coCau, long idInCome, long soTien){
        if(coCau != 0) {
            IncomeDetail incomeDetail = new IncomeDetail(idJar,Integer.parseInt(String.valueOf(idInCome)),coCau,soTien*coCau/100);
            db.addDetailMoney(incomeDetail);
        }
    }

    private void setControl() {
        edtSoTien = findViewById(R.id.edtSoTien);

        edtNgayThang = findViewById(R.id.edtNgayThang);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String ngayThang = LocalDate.now().toString();
            edtNgayThang.setText(ngayThang);
        }
        edtMoTa = findViewById(R.id.edtMoTa);
        edtCoCauThietYeu = findViewById(R.id.edtCoCauThietYeu);
        edtCoCauGiaoDuc = findViewById(R.id.edtCoCauGiaoDuc);
        edtCoCauTietKiem = findViewById(R.id.edtCoCauTietKiem);
        edtCoCauHuongThu = findViewById(R.id.edtCoCauHuongThu);
        edtCoCauDauTu = findViewById(R.id.edtCoCauDauTu);
        edtCoCauThienTam = findViewById(R.id.edtCoCauThienTam);

    // Liên kết Button
        tvLuuThuNhap = findViewById(R.id.tvLuuThuNhap);
    }
}