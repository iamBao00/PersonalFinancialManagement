package com.example.personalfinancialmanagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.personalfinancialmanagement.Model.Income;
import com.example.personalfinancialmanagement.Model.IncomeDetail;

import java.util.ArrayList;

public class DetailHistoryIncomeAdapter extends ArrayAdapter<IncomeDetail> {
    private ArrayList<IncomeDetail> incomeDetailList;
    private Context context;
    DatabaseHelper db;
    int idCurrentUserLogin;

    public DetailHistoryIncomeAdapter(Context context, ArrayList<IncomeDetail> incomeDetailList) {
        super(context, 0, incomeDetailList);
        this.context = context;
        this.incomeDetailList = incomeDetailList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                    R.layout.detail_income, parent, false);
        }

        // Lấy đối tượng Spending tại vị trí position
        IncomeDetail currentIncomeDetail = incomeDetailList.get(position);

        // Ánh xạ các TextView từ layout list_item_spending
        TextView tvCoCau = listItemView.findViewById(R.id.tvCoCau);
        TextView tvMoney = listItemView.findViewById(R.id.tvMoney);
        TextView tvTenHu = listItemView.findViewById(R.id.tvTenHu);

        db = new DatabaseHelper(this.getContext());

        int idJar = db.getIdJarByIdJarDetail(currentIncomeDetail.getIdJarDetail());
        if(idJar == -1) tvTenHu.setText("ERROR");
        else if(idJar == 1) tvTenHu.setText("Tên Hủ: Thiết Yếu");
        else if(idJar == 2) tvTenHu.setText("Tên Hủ: Giáo Dục");
        else if(idJar == 3) tvTenHu.setText("Tên Hủ: Tiết Kiệm");
        else if(idJar == 4) tvTenHu.setText("Tên Hủ: Hưởng Thụ");
        else if(idJar == 5) tvTenHu.setText("Tên Hủ: Đầu Tư");
        else if(idJar == 6) tvTenHu.setText("Tên Hủ: Thiện Tâm");
        tvCoCau.setText("Cơ Cấu: "+ currentIncomeDetail.getCo_cau() + "%");
        tvMoney.setText("Số Tiền: "+ currentIncomeDetail.getDetailMoney());

        return listItemView;
    }
}
