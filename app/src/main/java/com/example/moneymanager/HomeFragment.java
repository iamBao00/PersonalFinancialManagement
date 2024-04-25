package com.example.moneymanager;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.Model.JarDetail;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    Context context = getActivity();
    DatabaseHelper db;

    int idCurrentLoginUser =1 ;


    TextView tvTongThuNhap, tvTongChiTieu, tvSoDuHu1, tvSoDuHu2, tvSoDuHu3, tvSoDuHu4, tvSoDuHu5, tvSoDuHu6, tvSoDu;
    long tongThuNhap, tongChiTieu = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        tongThuNhap = 0;
        for(int i = 1; i<= 6; i++){
            tongThuNhap += db.getSumDetailMoneyInDetailIncomeByIdJarDetail(db.getJarDetail(new JarDetail(null, idCurrentLoginUser,i,null, null)).getIdJarDetail());
        }
        tvTongThuNhap.setText(String.valueOf(tongThuNhap));

        // tvSoDuHu1.setText(String.valueOf(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,1,null,null)).getMoney()));
        tvSoDuHu1.setText(String.valueOf(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,1,null,null)).getMoney()));
        tvSoDuHu2.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,2,null,null)).getMoney().toString());
        tvSoDuHu3.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,3,null,null)).getMoney().toString());
        tvSoDuHu4.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,4,null,null)).getMoney().toString());
        tvSoDuHu5.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,5,null,null)).getMoney().toString());
        tvSoDuHu6.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,6,null,null)).getMoney().toString());

        tvTongChiTieu.setText(String.valueOf(tongChiTieu));
        tvSoDu.setText(String.valueOf(tongThuNhap-tongChiTieu));
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setControl();
    }

    private void setControl() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        tvTongThuNhap = rootView.findViewById(R.id.tvTongThuNhap);
        tvTongChiTieu = rootView.findViewById(R.id.tvTongChiTieu);
        tvSoDuHu1 =  rootView.findViewById(R.id.tvSoDuHu1);
        tvSoDuHu2 =  rootView.findViewById(R.id.tvSoDuHu2);
        tvSoDuHu3 =  rootView.findViewById(R.id.tvSoDuHu3);
        tvSoDuHu4 =  rootView.findViewById(R.id.tvSoDuHu4);
        tvSoDuHu5 =  rootView.findViewById(R.id.tvSoDuHu5);
        tvSoDuHu6 =  rootView.findViewById(R.id.tvSoDuHu6);
        tvSoDu = rootView.findViewById(R.id.tvSoDu);
        Context context = getActivity();
        SharedPreferences prefs = context.getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentLoginUser = prefs.getInt("idUserCurrent", -1);
        Toast.makeText(context, "idhome " + idCurrentLoginUser, Toast.LENGTH_SHORT).show();

        if (context != null) {
            // Khởi tạo đối tượng DatabaseHelper với Context đã lấy được
            db = new DatabaseHelper(context);
        }
        return rootView;
    }
}