package com.example.mgl2.jellybeancafeserver.Adapter.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mgl2.jellybeancafeserver.Interface.IItemClickListener;
import com.example.mgl2.jellybeancafeserver.R;

public class DrinkListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public ImageView img_product;
    public TextView txt_drink_name,txt_price;

    IItemClickListener itemClickListener;

    public void setItemClickListener(IItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public DrinkListViewHolder(View itemView) {
        super(itemView);

        img_product = (ImageView)itemView.findViewById(R.id.img_product);
        txt_drink_name = (TextView)itemView.findViewById(R.id.txt_drink_name);
        txt_price = (TextView)itemView.findViewById(R.id.txt_price);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,false);
    }
}
