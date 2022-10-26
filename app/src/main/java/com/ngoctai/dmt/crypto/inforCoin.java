package com.ngoctai.dmt.crypto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class inforCoin extends AppCompatActivity {
    private TextView TexPrice, Text1hour, Text24Hour, Text7Day, Texname;
    private ImageView  logoinfor ;
    private ProgressBar progressBar;
    private Button btnSell, bntBuy;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infor_coin);
        btnSell = findViewById(R.id.buttonsell);
        bntBuy = findViewById( R.id.buttonBuy);
        TexPrice = findViewById( R.id.Textprice);
        Texname = findViewById( R.id.textViewname);
        Text1hour =findViewById(R.id.Text1hour);
        Text24Hour= findViewById(R.id.Text24hour);
        Text7Day = findViewById( R.id.Text7day);
        progressBar = findViewById( R.id.progressBarinforcoin);
        logoinfor = findViewById(R.id.imageViewlogoinfor);
        String sringid = getIntent().getStringExtra("sringid");
        String name = getIntent().getStringExtra("stringname");
        String logo= getIntent().getStringExtra("stringlogo");
        Texname.setText(name);
        Picasso.get().load(logo).into(logoinfor );
        getCurrencyData(sringid );
         btnSell.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Toast.makeText(inforCoin.this, "Bán thành công ", Toast.LENGTH_SHORT).show();

             }
         });
        bntBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(inforCoin.this, "Mua thành công ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCurrencyData(String idfind) {
        progressBar.setVisibility(View.VISIBLE);
        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?CMC_PRO_API_KEY=390d8a1f-5bad-4202-8275-16d2c5bb494b&id="+idfind;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                        JSONObject dataOjectdata = response.getJSONObject("data");
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy ");
                        String strDate = sdf.format(c.getTime());
                        JSONObject dataObj = dataOjectdata.getJSONObject(idfind);
                        ModelCrypto modelCryptod= new ModelCrypto();
                        String id = dataObj.getString("id");
                        modelCryptod.setSymbol(""+strDate);
                        JSONObject quote = dataObj.getJSONObject("quote");
                        JSONObject USD = quote.getJSONObject("USD");
                        TexPrice.setText("$ " + df2.format(USD.getDouble("price")));
                        Text1hour.setText( df2.format(USD.getDouble("percent_change_1h"))+"%");
                        Text24Hour.setText( df2.format(USD.getDouble("percent_change_24h"))+"%");
                        Text7Day.setText( df2.format(USD.getDouble("percent_change_7d"))+"%");
                    progressBar.setVisibility(View.GONE);
                }
                catch (JSONException e) {
                    e.printStackTrace();

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
}