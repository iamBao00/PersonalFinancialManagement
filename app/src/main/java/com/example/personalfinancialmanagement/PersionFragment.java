package com.example.personalfinancialmanagement;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.EditText;

import com.example.personalfinancialmanagement.Model.User;


public class PersionFragment extends Fragment implements MenuProvider {
    EditText edtFullname, edtEmail, edtUsername;
    Button btnLuu;
    int idCurrentLoginUser;
    DatabaseHelper db;

    public PersionFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_persion, container, false);
        MenuHost menuHost = requireActivity();
        menuHost.addMenuProvider(this, getViewLifecycleOwner(), Lifecycle.State.RESUMED);

        // Set control
        edtFullname = rootView.findViewById(R.id.edtFullname);
        edtEmail = rootView.findViewById(R.id.edtEmail);
        edtUsername = rootView.findViewById(R.id.edtUserName);
        btnLuu = rootView.findViewById(R.id.btnLuu);

        SharedPreferences prefs = getActivity().getSharedPreferences("getIdUser", MODE_PRIVATE);
        idCurrentLoginUser = prefs.getInt("idUserCurrent", -1);

        db = new DatabaseHelper(getContext());

        User user = db.getUserByID(idCurrentLoginUser);
        edtUsername.setText(user.getUsername());
        edtFullname.setText(user.getFullname());
        edtEmail.setText(user.getEmail());

        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setFullname(edtFullname.getText().toString());
                user.setEmail(edtEmail.getText().toString());
                user.setUsername(edtUsername.getText().toString());
                db.updateUser(user);
                replaceFracment(new HomeFragment());
            }
        });
        return  rootView;

    }

    private void replaceFracment(Fragment fragment)
    {

        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
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