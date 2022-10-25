package com.ngoctai.dmt.crypto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;

import my_interface.IClickitemdata;

public class CryptoActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    public   TextView textView,TextViewmonney;
    private ArrayList<ModelCrypto> modelCryptos;
    private AdapterCrypto adapterCrypto;
    public RecyclerView currenciesRV;

     EditText searchView; ;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crypto);
        BottomNavigationView navigationView2= findViewById(R.id.botomnavicryptor);
        currenciesRV = findViewById(R.id.idRVCurrencies);

        modelCryptos = new ArrayList<>();
        adapterCrypto = new AdapterCrypto(modelCryptos , new IClickitemdata() {
            @Override
            public void onclickitem(ModelCrypto modelCrypto) {
                onclickgotodetail(modelCrypto);
            }
        });
        currenciesRV.setAdapter(adapterCrypto);
        currenciesRV.setLayoutManager(new LinearLayoutManager(this));
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        TextViewmonney =  findViewById( R.id.textViewmonney);
        TextViewmonney.setText(" $ "+"28,122,001.87");
        getCurrencyData();
        searchView =(EditText) findViewById(R.id.EdtSearch);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filterCurrencies(s.toString());
            }

            private void filterCurrencies(String currency) {
                ArrayList<ModelCrypto> filteredList = new ArrayList<>();
                for (ModelCrypto item : modelCryptos) {
                    if (item.getName().toLowerCase().contains(currency.toLowerCase())) {
                        filteredList.add(item);
                    }
                    adapterCrypto.filterList(filteredList);
                }
            }
        });


        navigationView2.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.actionhome:
                        Intent intent = new Intent(CryptoActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        item.setEnabled(false);
                        break;


                    case R.id.actiontransaction:
                     //   Toast.makeText(CryptoActivity.this, "Home ", Toast.LENGTH_SHORT).show();
                        item.setEnabled(false);
                        break;
                    case R.id.actionnews:
                        Toast.makeText(CryptoActivity.this, "Tin Tức  ", Toast.LENGTH_SHORT).show();
                        item.setEnabled(false);
                        break;
                    case R.id.actiohistory:
                        Toast.makeText(CryptoActivity.this, "lịch sử giao dịch", Toast.LENGTH_SHORT).show();
                        item.setEnabled(false);
                        break;
                    case R.id.actionprofile:
                        Toast.makeText(CryptoActivity.this, "Hồ sơ  ", Toast.LENGTH_SHORT).show();
                        item.setEnabled(false);
                        break;
                }

                return true;
            }
        });


    }
    private void getCurrencyData() {
        progressBar.setVisibility(View.VISIBLE);
        String URLimage = "https://s2.coinmarketcap.com/static/img/coins/64x64/";
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest?CMC_PRO_API_KEY=390d8a1f-5bad-4202-8275-16d2c5bb494b&limit=5000";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i=0; i<dataArray.length(); i++) {

                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy ");
                        String strDate = sdf.format(c.getTime());
                       // Toast.makeText(CryptoActivity.this, ""+currentTime , Toast.LENGTH_SHORT).show();
                        JSONObject dataObj = dataArray.getJSONObject(i);
                      ModelCrypto modelCryptod= new ModelCrypto();
                        String id = dataObj.getString("id");
                        modelCryptod.setId(id);
                        modelCryptod.setName(dataObj.getString("name"));
                        modelCryptod.setSymbol(""+strDate);
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        modelCryptod.setPrice(USD.getDouble("price"));
                       modelCryptod.setChange(USD.getDouble("percent_change_1h"));
                        String logo= URLimage+id+".png";
                        modelCryptod.setLogo(logo);
                        modelCryptos.add(modelCryptod);
                        //Toast.makeText(CryptoActivity.this, ""+ modelCryptod.getName(), Toast.LENGTH_SHORT).show();
                    }
                    adapterCrypto.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
                catch (JSONException e) {
                    e.printStackTrace();
                 //   Toast.makeText(MainActivity.this, "Fail to extract json data...", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
               // Toast.makeText(MainActivity.this, "Fail to get the data.", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void onclickgotodetail(ModelCrypto modelCrypto) {
        Intent intent = new Intent(this,inforCoin.class);
        Bundle bundle = new Bundle();

        bundle.putSerializable("object_infor", (Serializable) modelCrypto);
        intent.putExtras(bundle);
       startActivity(intent);

    }
}

