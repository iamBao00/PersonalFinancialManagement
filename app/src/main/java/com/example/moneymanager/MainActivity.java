package com.example.moneymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.moneymanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private int idCurrentLoginUser = 3;

    ActivityMainBinding bingding;
    Toolbar tooltopbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bingding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_main);
        setContentView(bingding.getRoot());
        replaceFracment(new HomeFragment());
//        bingding.bottomNavagationView.setBackground(null);
        bingding.bottomNavagationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId())
            {
                case R.id.homeIcon:
                    replaceFracment(new HomeFragment());
                    break;
                case R.id.addIcon:
                    Intent myIntent = new Intent(MainActivity.this, AddIncomeActivity.class);
                    myIntent.putExtra("userId",idCurrentLoginUser);
                    startActivity(myIntent);
                    break;
                case R.id.persionIcon:
                    replaceFracment(new PersionFragment());
                    break;
            }
            return true;
        });
        setControl();
        setEvent();
    }

    private void replaceFracment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void setEvent() {
        setSupportActionBar(tooltopbar);
    }

    private void setControl() {
        tooltopbar = findViewById(R.id.tool_top_bar);
    }
}