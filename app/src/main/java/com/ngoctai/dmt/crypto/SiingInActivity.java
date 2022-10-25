package com.ngoctai.dmt.crypto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SiingInActivity extends AppCompatActivity {

    public Button buttonsignin;
    TextView  buttonsignup;
    public EditText edtdnEmail, edtdnpassword;
    private Button btnxndangnhap;
    FirebaseAuth mAuth , auth;
     private  FirebaseUser user ;
     public   String UserID;
     private ProgressBar progressBar;
    public  int conho =0, temp=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_siing_in);
        //progressBar.setVisibility(View.GONE);
        anhxa();


        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SiingInActivity.this,signUpActivity.class);
                startActivity(intent);

            }
        });

    }
    private  void  anhxa ()
    {   progressBar = findViewById(R.id.progressBardangnhap);
        buttonsignup = findViewById(R.id.buttondangki);
        buttonsignin = findViewById(R.id.buttondangnhap);
        edtdnEmail = findViewById(R.id.editTextdangnhapEmail);
        edtdnpassword = findViewById(R.id.editTextdangnhapPassword);
        mAuth = FirebaseAuth.getInstance();
        progressBar.setVisibility(View.GONE);

        buttonsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                buttonsignin.setEnabled(false);
                String email =edtdnEmail.getText().toString().trim();
                String password = edtdnpassword.getText().toString().trim();
                loginUser(email,password);

            }
        });

    }
    private void   loginUser(String email , String password){
        progressBar.setVisibility(View.VISIBLE);
        if (TextUtils.isEmpty(email)){
            //Toast.makeText(SiingInActivity.this, "Sai email", Toast.LENGTH_SHORT).show();
            edtdnEmail. setError("Please enter your email address completely.");
            progressBar.setVisibility(View.GONE);
            buttonsignin.setEnabled(true);
        }else if (TextUtils.isEmpty(password)){
           //Toast.makeText(SiingInActivity.this, "Sai mật khẩu", Toast.LENGTH_SHORT).show();
            edtdnpassword.setError("Please enter your password");
            progressBar.setVisibility(View.GONE);
            buttonsignin.setEnabled(true);
        } else{
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                     Toast.makeText(SiingInActivity.this, "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                       // Toast.makeText(SiingInActivity.this, loginUser()+"", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SiingInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finishAffinity();

                    }else{
                        buttonsignin.setEnabled(true);
                      //Toast.makeText(SiingInActivity.this, "Tài Khoản Chưa Được Đăng Kí" , Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            });
        }
      //  return conho;
    }

}