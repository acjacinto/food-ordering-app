package com.example.mgl2.jellybeancafeserver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mgl2.jellybeancafeserver.Adapter.ViewHolder.DrinkListViewHolder;
import com.example.mgl2.jellybeancafeserver.Interface.IItemClickListener;
import com.example.mgl2.jellybeancafeserver.Model.Drink;
import com.example.mgl2.jellybeancafeserver.R;
import com.example.mgl2.jellybeancafeserver.UpdateProductActivity;
import com.example.mgl2.jellybeancafeserver.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DrinkListAdapter extends RecyclerView.Adapter<DrinkListViewHolder> {

    Context context;
    List<Drink> drinkList;

    public DrinkListAdapter(Context context, List<Drink> drinkList) {
        this.context = context;
        this.drinkList = drinkList;
    }

    @NonNull
    @Override
    public DrinkListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_layout,parent,false);
        return new DrinkListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DrinkListViewHolder holder, final int position) {

        Picasso.with(context).load(drinkList.get(position).Link).into(holder.img_product);
        holder.txt_price.setText(new StringBuilder("₱").append(drinkList.get(position).Price).toString());
        holder.txt_drink_name.setText(drinkList.get(position).Name);

        //Event - anti crash null item click
        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, boolean isLongClick) {
                Common.currentDrink = drinkList.get(position);
                context.startActivity(new Intent(context, UpdateProductActivity.class));
            }
        });
    }

    @Override
    public int getItemCount() {
        return drinkList.size();
    }
}
