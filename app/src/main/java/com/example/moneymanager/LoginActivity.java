package com.example.moneymanager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseHelper(this);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.loginUsername.getText().toString();
                String password = binding.loginPassword.getText().toString();

                if(username.equals("") || password.equals("")){
                    Toast.makeText(LoginActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean checkUserPass = db.checkUsernamePassword(username, password);
                    if(checkUserPass == true){
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        //////////////////////////////////////////
                        //getIDUser
                        int idUser = db.getIDUser(username);
                        SharedPreferences.Editor editor = getSharedPreferences("getIdUser", MODE_PRIVATE).edit();
                        editor.putInt("idUserCurrent", idUser);
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "id " + idUser, Toast.LENGTH_SHORT).show();
                        //////////////////////////////////
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("idUserCurrent", idUser);
                        startActivity(intent);
                    }else{
                        Toast.makeText(LoginActivity.this, "Thông tin không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
