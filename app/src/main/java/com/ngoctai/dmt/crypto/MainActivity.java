package com.ngoctai.dmt.crypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {


  private Button btndangxuat, btngetdata, btnpushdata;
    FirebaseAuth mAuth;
    EditText edtdata;
    TextView tvdata;
    BottomNavigationView navigationView ;
    String UID ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhxa ();


        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this,SiingInActivity.class);
                startActivity(intent);
                finish();    
            }
        });


        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionhome:
                        Toast.makeText(MainActivity.this, "Home ", Toast.LENGTH_SHORT).show();
                        item.setEnabled(false);
                        break;
                    case R.id.actiontransaction:
                        Intent intent = new Intent(MainActivity.this,CryptoActivity.class);
                        startActivity(intent);
                        item.setEnabled(false);
                        finish();
                        break;
                    case R.id.actionnews:
                        Toast.makeText(MainActivity.this, "Tin Tức  ", Toast.LENGTH_SHORT).show();
                        item.setEnabled(false);
                        break;
                    case R.id.actiohistory:
                        Intent intent1 = new Intent(MainActivity.this,inforCoin.class);
                        startActivity(intent1);
                        item.setEnabled(false);
                        finish();
                        break;
                    case R.id.actionprofile:
                        Toast.makeText(MainActivity.this, "Hồ sơ  ", Toast.LENGTH_SHORT).show();
                        item.setEnabled(false);
                        break;
                }

                return true;
            }
        });
        btnpushdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   writeData ();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                 UID =FirebaseAuth.getInstance().getCurrentUser().getUid();
                //Toast.makeText(MainActivity.this,"Users/"+UID+"/id" , Toast.LENGTH_SHORT).show();
                DatabaseReference myref = database.getReference("Users/"+UID+"/id");
                myref.setValue(edtdata.getText().toString().trim());


            }
        });
        btngetdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ReadData();
            }
        });
    }
    private  void  anhxa ()
    {   navigationView= findViewById(R.id.bottomNavigationView);
        btndangxuat = findViewById(R.id.buttondangxuat);
        mAuth = FirebaseAuth.getInstance();
        btngetdata= findViewById(R.id.buttongetdata);
        edtdata= findViewById(R.id.editTextdata);
        btnpushdata  =findViewById(R.id.buttonpushdata);
        tvdata = findViewById(R.id.Textviewdata);
    }


    // Write a message to the database
private  void writeData ()
{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    myRef.setValue(edtdata.getText().toString().trim());
    DatabaseReference myRef1 = database.getReference("test");
    myRef1.setValue("tranngoctai");
    DatabaseReference myRef2 = database.getReference("check");
    myRef2.setValue(true);
    DatabaseReference myRef3 = database.getReference("project/test");
    myRef3.setValue(true);
    DatabaseReference myRef4= database.getReference("project/test");
    myRef4.setValue(false);
}
private void ReadData ()
    { FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Users/"+UID+"/id");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                tvdata.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });
    }


}