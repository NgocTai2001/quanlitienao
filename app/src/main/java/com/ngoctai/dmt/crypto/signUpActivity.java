package com.ngoctai.dmt.crypto;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class signUpActivity extends AppCompatActivity {
    private EditText edtdkEmail, edtdkpassword;
    private Button btnxndangki;
    String UID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        anhxa ();
        btnxndangki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onclickSignUp();
            }

        });
    }


    private void  anhxa (){
        edtdkEmail = findViewById(R.id.editTextdangkiEmail);
        edtdkpassword = findViewById(R.id.editTextdangkiPassword);
        btnxndangki = findViewById(R.id.buttonxacnhandangki);
    }
    private void onclickSignUp() {
        String strEmail = edtdkEmail.getText().toString().trim();
        String strPassword = edtdkpassword.getText().toString().trim();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth .createUserWithEmailAndPassword(strEmail, strPassword )
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           user user = new user( strPassword,strEmail);
                            UID =FirebaseAuth.getInstance().getCurrentUser().getUid();

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(UID)
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(signUpActivity.this, "thanh cong", Toast.LENGTH_LONG).show();
                                            } else {
                                                //display a failure message
                                            }
                                        }
                                    });
                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(signUpActivity.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(signUpActivity.this, "Đăng Kí Tài Khoản Thất bại !",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


}