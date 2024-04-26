package com.example.moneymanager;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moneymanager.Model.JarDetail;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements MenuProvider {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    Context context = getActivity();
    DatabaseHelper db;

    int idCurrentLoginUser;


    TextView tvTongThuNhap, tvTongChiTieu, tvSoDuHu1, tvSoDuHu2, tvSoDuHu3, tvSoDuHu4, tvSoDuHu5, tvSoDuHu6, tvSoDu;
    FrameLayout frChiTieu;
    View xml_payment;
    long tongThuNhap, tongChiTieu = 0;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        tongThuNhap = 0;
        tongChiTieu = 0;
        for(int i = 1; i<= 6; i++){
            tongThuNhap += db.getSumDetailMoneyInDetailIncomeByIdJarDetail(db.getJarDetail(new JarDetail(null, idCurrentLoginUser,i,null, null)).getIdJarDetail());
        }
        tvTongThuNhap.setText(String.valueOf(tongThuNhap));
        for (int i = 1; i <= 6; i++) {
            tongChiTieu += db.getTotalSpending(db.getIdJarDetail(new JarDetail(null, idCurrentLoginUser,i,null, null)));
        }
//        tongChiTieu = db.getTotalSpending(12);
        // tvSoDuHu1.setText(String.valueOf(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,1,null,null)).getMoney()));
        tvSoDuHu1.setText(String.valueOf(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,1,null,null)).getMoney()));
        tvSoDuHu2.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,2,null,null)).getMoney().toString());
        tvSoDuHu3.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,3,null,null)).getMoney().toString());
        tvSoDuHu4.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,4,null,null)).getMoney().toString());
        tvSoDuHu5.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,5,null,null)).getMoney().toString());
        tvSoDuHu6.setText(db.getJarDetail(new JarDetail(null,idCurrentLoginUser,6,null,null)).getMoney().toString());

        tvTongChiTieu.setText(String.valueOf(tongChiTieu));
        tvTongChiTieu.setText(String.valueOf(tongChiTieu));
        tvSoDu.setText(String.valueOf(tongThuNhap-tongChiTieu));
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        // Inflate the layout for this fragment
        setControl(rootView);

        Context context = getActivity();
        if (context != null) {
            // Khởi tạo đối tượng DatabaseHelper với Context đã lấy được
            db = new DatabaseHelper(context);
        }

        SharedPreferences prefs = context.getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentLoginUser = prefs.getInt("idUserCurrent", -1);
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        setEvent();

        return rootView;
    }

    private void replaceFracment(Fragment fragment)
    {

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setEvent() {
        frChiTieu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = getActivity();
                    Intent intent = new Intent(context, SpendingActivity.class);
                    startActivity(intent);
                }
            });
//       xml_payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                replaceFracment(new PersionFragment());
//            }
//        });

    }


    private void setControl(View rootView) {
        tvTongThuNhap = rootView.findViewById(R.id.tvTongThuNhap);
        tvTongChiTieu = rootView.findViewById(R.id.tvTongChiTieu);
        tvSoDuHu1 =  rootView.findViewById(R.id.tvSoDuHu1);
        tvSoDuHu2 =  rootView.findViewById(R.id.tvSoDuHu2);
        tvSoDuHu3 =  rootView.findViewById(R.id.tvSoDuHu3);
        tvSoDuHu4 =  rootView.findViewById(R.id.tvSoDuHu4);
        tvSoDuHu5 =  rootView.findViewById(R.id.tvSoDuHu5);
        tvSoDuHu6 =  rootView.findViewById(R.id.tvSoDuHu6);
        tvSoDu = rootView.findViewById(R.id.tvSoDu);
        frChiTieu = rootView.findViewById(R.id.frChiTieu);

        xml_payment = rootView.findViewById(R.id.xml_payment);
    }

    @Override
    public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.main_top_bar,menu);
    }

    @Override
    public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId())
        {
            case R.id.iconStatis:
                replaceFracment(new StatiscalFragment());
                break;
            case R.id.iconNotify:
                replaceFracment(new NotifyFragment());
                break;
        }
        return true;
    }
}