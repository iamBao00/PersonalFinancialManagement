package com.example.personalfinancialmanagement;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;

import com.example.personalfinancialmanagement.Model.Income;
import com.example.personalfinancialmanagement.Model.Spending;

public class IncomeAdapter extends ArrayAdapter<Income> {

    private ArrayList<Income> incomeList;
    private Context context;
    DatabaseHelper db;
    int idCurrentUserLogin;

    public IncomeAdapter(Context context, ArrayList<Income> incomeList) {
        super(context, 0, incomeList);
        this.context = context;
        this.incomeList = incomeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                    R.layout.list_income, parent, false);
        }

        // Lấy đối tượng Spending tại vị trí position
        Income currentIncome = incomeList.get(position);

        // Ánh xạ các TextView từ layout list_item_spending
        TextView tvId = listItemView.findViewById(R.id.tvIDIncome);
        TextView tvMoney = listItemView.findViewById(R.id.tvTotalMoney);
        TextView tvDescription = listItemView.findViewById(R.id.tvDescription2);
        TextView tvDate = listItemView.findViewById(R.id.tvDate2);
        Button btnChiTiet = listItemView.findViewById(R.id.btnChiTiet);

        db = new DatabaseHelper(this.getContext());

        tvId.setText("ID: " + currentIncome.getIdIncome());
        tvMoney.setText("Số Tiền: " + currentIncome.getTotal_money());
        tvDescription.setText("Mô Tả: " + currentIncome.getDescription());
        tvDate.setText("Ngày Tháng: " + currentIncome.getDate());

        btnChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(context, DetailHistoryIncome.class);
                myIntent.putExtra("idThuNhap", currentIncome.getIdIncome());
                context.startActivity(myIntent);
            }
        });
        return listItemView;
    }
}
