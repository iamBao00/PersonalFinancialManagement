package com.example.personalfinancialmanagement;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.personalfinancialmanagement.Model.Notify;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class NotifyFragment extends Fragment  {
    DatabaseHelper db ;
    ListView lvNotifyList;
    ArrayList<Notify> data = new ArrayList<>();
    ArrayAdapter<Notify> notifyArrayAdapter;
    int idCurrentLoginUser;

    public NotifyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {

        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_notify, container, false);

        if(getActivity() != null)
        {
            db = new DatabaseHelper(getActivity());
        }
        SharedPreferences prefs = getActivity().getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentLoginUser = prefs.getInt("idUserCurrent", -1);
        setControl(rootView);
        setEvent(rootView);

        return  rootView;
    }

    private void setEvent(View rootView) {
        getData(rootView);

    }

    private void getData(View rootView) {
        List<Integer> idNotifyList = db.getAllIdNotifyOfUser(idCurrentLoginUser);
        if (idNotifyList != null) {
            for (Integer item : idNotifyList) {
                Notify notify = db.getNotify(item);
                if (notify != null) {
                    data.add(0,notify);
                }
            }
        }
        notifyArrayAdapter = new NotifyAdaptter(getContext(), R.layout.notify_item_layout, data);
        lvNotifyList.setAdapter(notifyArrayAdapter);
        notifyArrayAdapter.notifyDataSetChanged();
    }

    private void setControl(View rootView) {
        lvNotifyList = rootView.findViewById(R.id.lvNotifyList);
    }

    private void replaceFracment(Fragment fragment)
    {

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}