package com.ngoctai.dmt.crypto;

import android.annotation.SuppressLint;


import android.icu.text.StringSearch;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import my_interface.IClickitemdata;

public class AdapterCrypto extends RecyclerView.Adapter<AdapterCrypto.ViewHolder> implements Filterable {

     ArrayList<ModelCrypto> modelCryptos;
private IClickitemdata iClickitemdata;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    public AdapterCrypto(ArrayList<ModelCrypto> modelCryptos, IClickitemdata listener) {
        this.modelCryptos = modelCryptos;
        this.iClickitemdata =listener;
    }
    public void filterList(ArrayList<ModelCrypto> filteredList) {
        modelCryptos = filteredList;

        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterCrypto.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.crypto_rv_item, parent, false);
        return new AdapterCrypto.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ModelCrypto modelCrypto = modelCryptos.get(position);
        holder.currencyNameTv.setText(modelCrypto.getName());
        Picasso.get().load(modelCryptos.get(position).getLogo()).into(holder.logo);
        holder.rateTv.setText("$ " + df2.format(modelCrypto.getPrice()));
        holder.change.setText(df2.format(modelCrypto.getChange())+"%");
        holder.date.setText(modelCrypto.getSymbol());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitemdata.onclickitem(modelCrypto );
            }


        });

    }


    @Override
    public Filter getFilter() {
        return null;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView currencyNameTv, date, rateTv, change;
        ImageView logo;
        private ConstraintLayout cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currencyNameTv = itemView.findViewById(R.id.idTVCurrencyName);
            logo = itemView.findViewById(R.id.imageviewHinh);
            rateTv = itemView.findViewById(R.id.idTVCurrencyRate);
            date = itemView.findViewById(R.id.textViewDate);
            change = itemView.findViewById(R.id.textView2);
            cardView = itemView.findViewById(R.id.itemclick);

        }
    }

    @Override
    public int getItemCount() {
        return modelCryptos.size();
    }



}
