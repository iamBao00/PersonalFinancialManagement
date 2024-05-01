package com.example.moneymanager;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class StatiscalFragment extends Fragment {
    DatabaseHelper db ;
    private int idCurrentLoginUser;
    PieChart pieChart;




    public StatiscalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_statiscal, container, false);
        setControl(rootView);
        setEvent(rootView);

        return  rootView;
    }

    private void setEvent(View rootView) {
        setDrawPieChart();

    }

    private void setDrawPieChart() {
        ArrayList<PieEntry> xEntries = new ArrayList<>();
        ArrayList<String> labels = db.getListNameOfJar(idCurrentLoginUser);
        ArrayList<Integer> moneys = db.getMoneyOfJar(idCurrentLoginUser);

        for (int i = 0 ; i< moneys.size(); i++  )
        {
            xEntries.add(new PieEntry(moneys.get(i),labels.get(i)));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#DC3FC3"));
        colors.add(Color.parseColor("#00C7C7"));
        colors.add(Color.parseColor("#DBAD06"));
        colors.add(Color.parseColor("#1B8D08"));
        colors.add(Color.parseColor("#0D84F2"));
        colors.add(Color.parseColor("#F5453A"));
        colors.add(Color.parseColor("#C15DD9"));

        PieDataSet pieDataSet = new PieDataSet(xEntries, "Subject");
        pieDataSet.setColors(colors);

        pieDataSet.setSliceSpace(2f);
        pieDataSet.setValueTextSize(15);
        pieDataSet.setValueTextColor(Color.WHITE);

        pieChart.setRotationEnabled(true);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setHoleColor(Color.TRANSPARENT);

        pieChart.getDescription().setEnabled(false);
        pieChart.animateY(1000);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

        Legend legend = pieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        legend.setWordWrapEnabled(true);
        legend.setFormSize(20f);
        legend.setYEntrySpace(20f);
        legend.setTextColor(Color.WHITE);
        List<LegendEntry> legendList = new ArrayList<>();
        for(int i = 0 ; i<labels.size();i++ )
        {
            LegendEntry lg = new LegendEntry();
            lg.label = labels.get(i);
            lg.formColor = colors.get(i);
            legendList.add(lg);
        }
        legend.setCustom(legendList);
    }

    private void setControl(View rootView) {
        // get idUser
        SharedPreferences prefs = getActivity().getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentLoginUser = prefs.getInt("idUserCurrent", -1);
        //get Database
        db = new DatabaseHelper(rootView.getContext());
        pieChart = rootView.findViewById(R.id.pieChart);

    }

    private void replaceFracment(Fragment fragment)
    {

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



}