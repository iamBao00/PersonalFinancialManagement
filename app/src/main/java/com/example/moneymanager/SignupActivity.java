package com.example.moneymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.moneymanager.databinding.ActivitySignupBinding;
import com.example.moneymanager.DatabaseHelper;
public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;
    DatabaseHelper db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.signupUsername.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String email = binding.emailUsername.getText().toString();
                String fullName = binding.signupFullname.getText().toString();
                String confirmPassword = binding.confirmPassword.getText().toString();

                if(username.equals("") || password.equals("") || confirmPassword.equals("") || email.equals("") || fullName.equals("")){
                    Toast.makeText(SignupActivity.this, "Vui lòng nhập đầy đủ các thng tin", Toast.LENGTH_SHORT).show();
                }else{
                    if(password.equals(confirmPassword)){
                        Boolean checkUserEmail = db.checkEmail(email);
                        if(checkUserEmail == false){
                            long insert = db.insertData(username, password, email, fullName);
                            System.out.println(insert);
                            if(insert > 0){
                                Toast.makeText(SignupActivity.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                                SignupActivity.this.startActivity(intent);
                            }else{
                                Toast.makeText(SignupActivity.this, "Đăng kí thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(SignupActivity.this, "Người dùng đã tồn tại!Vui lòng đăng nhâập", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(SignupActivity.this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                SignupActivity.this.startActivity(intent);
            }
       });
    }
}