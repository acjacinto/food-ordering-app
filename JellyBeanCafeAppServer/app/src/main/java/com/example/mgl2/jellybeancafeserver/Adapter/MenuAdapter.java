package com.example.mgl2.jellybeancafeserver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mgl2.jellybeancafeserver.Adapter.ViewHolder.MenuViewHolder;
import com.example.mgl2.jellybeancafeserver.DrinkListActivity;
import com.example.mgl2.jellybeancafeserver.Interface.IItemClickListener;
import com.example.mgl2.jellybeancafeserver.Model.Category;
import com.example.mgl2.jellybeancafeserver.R;
import com.example.mgl2.jellybeancafeserver.UpdateCategoryActivity;
import com.example.mgl2.jellybeancafeserver.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuViewHolder> {

    Context context;
    List<Category> categoryList;

    public MenuAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.menu_item_layout,parent,false);
        return new MenuViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, final int position) {
        Picasso.with(context)
                .load(categoryList.get(position).Link)
                .into(holder.img_product);

        holder.txt_product.setText(categoryList.get(position).Name);

        //Implement item click
        holder.setItemClickListener(new IItemClickListener() {

            @Override
            public void onClick(View view, boolean isLongClick) {
                if(isLongClick)
                {
                    //Assign this Category to variable global
                    Common.currentCategory = categoryList.get(position);
                    //Start new activity
                    context.startActivity(new Intent(context, UpdateCategoryActivity.class));
                }
                else
                {
                    //Assign this Category to variable global
                    Common.currentCategory = categoryList.get(position);
                    //Start new activity
                    context.startActivity(new Intent(context, DrinkListActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
