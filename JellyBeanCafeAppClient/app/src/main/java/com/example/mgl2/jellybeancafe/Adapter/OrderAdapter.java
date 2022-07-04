package com.example.mgl2.jellybeancafe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mgl2.jellybeancafe.Interface.ItemClickListener;
import com.example.mgl2.jellybeancafe.Model.Order;
import com.example.mgl2.jellybeancafe.OrderDetailActivity;
import com.example.mgl2.jellybeancafe.R;
import com.example.mgl2.jellybeancafe.Utils.Common;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    Context context;
    List<Order> orderList;

    public OrderAdapter(Context context, List<Order> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.order_layout,parent,false);
        return new OrderViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, final int position) {
        holder.txt_order_id.setText(new StringBuilder("#").append(orderList.get(position).getOrderId()));
        holder.txt_order_price.setText(new StringBuilder("₱").append(orderList.get(position).getOrderPrice()).append("0"));
        holder.txt_order_address.setText(orderList.get(position).getOrderAddress());
        holder.txt_order_comment.setText(orderList.get(position).getOrderComment());
        holder.txt_order_status.setText(new StringBuilder("Order Status: ").append(Common.convertCodeToStatus(orderList.get(position).getOrderStatus())));

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View v) {
                Common.currentOrder = orderList.get(position);
                context.startActivity(new Intent(context, OrderDetailActivity.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
