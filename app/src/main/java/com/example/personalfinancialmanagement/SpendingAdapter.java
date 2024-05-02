package com.example.personalfinancialmanagement;

import static android.app.PendingIntent.getActivity;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;
import java.util.ArrayList;
import com.example.personalfinancialmanagement.Model.Spending;

public class SpendingAdapter extends ArrayAdapter<Spending> {

    private ArrayList<Spending> spendingList;
    private Context context;
    DatabaseHelper db;
    int idCurrentUserLogin;

    public SpendingAdapter(Context context, ArrayList<Spending> spendingList) {
        super(context, 0, spendingList);
        this.context = context;
        this.spendingList = spendingList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                    R.layout.list_spending, parent, false);
        }

        // Lấy đối tượng Spending tại vị trí position
        Spending currentSpending = spendingList.get(position);

        // Ánh xạ các TextView từ layout list_item_spending
        TextView tvSpendingId = listItemView.findViewById(R.id.tvSpendingId);
        TextView tvMoney = listItemView.findViewById(R.id.tvMoney);
        TextView tvDescription = listItemView.findViewById(R.id.tvDescription);
        TextView tvDate = listItemView.findViewById(R.id.tvDate);

        db = new DatabaseHelper(this.getContext());

        SharedPreferences prefs = this.getContext().getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentUserLogin = prefs.getInt("idUserCurrent", -1);
        int idJar = -1;
        idJar = db.getJarId(idCurrentUserLogin, currentSpending.getIdJarDetail());


        if (idJar == 1) {
            tvSpendingId.setText("Hũ: " + "Thiết Yếu");
        }
        else if (idJar == 2) {
            tvSpendingId.setText("Hũ: " + "Giáo Dục");
        }
        else if (idJar == 3) {
            tvSpendingId.setText("Hũ: " + "Tiết Kiệm");
        }
        else if (idJar == 4) {
            tvSpendingId.setText("Hũ: " + "Hưởng Thụ");
        }
        else if (idJar == 5) {
            tvSpendingId.setText("Hũ: " + "Đầu Tư");
        }
        else if (idJar == 6) {
            tvSpendingId.setText("Hũ:" + "Thiện Tâm");
        }

        tvMoney.setText("Số Tiền: " + currentSpending.getMoney());
        tvDescription.setText("Mô Tả: " + currentSpending.getDescription());
        tvDate.setText("Ngày Tháng: " + currentSpending.getDate());


        return listItemView;
    }
}
