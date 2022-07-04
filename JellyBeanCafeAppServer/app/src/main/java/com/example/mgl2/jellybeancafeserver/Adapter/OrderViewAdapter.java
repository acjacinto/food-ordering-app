package com.example.mgl2.jellybeancafeserver.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mgl2.jellybeancafeserver.Adapter.ViewHolder.OrderViewHolder;
import com.example.mgl2.jellybeancafeserver.Interface.IItemClickListener;
import com.example.mgl2.jellybeancafeserver.Model.Order;
import com.example.mgl2.jellybeancafeserver.R;
import com.example.mgl2.jellybeancafeserver.Utils.Common;
import com.example.mgl2.jellybeancafeserver.ViewOrderDetail;

import java.util.List;

public class OrderViewAdapter extends RecyclerView.Adapter<OrderViewHolder> {

    Context context;
    List<Order> orderList;

    public OrderViewAdapter(Context context, List<Order> orderList) {
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
        /*holder.txt_order_address.setText(orderList.get(position).getOrderAddress());*/
        holder.txt_order_comment.setText(orderList.get(position).getOrderComment());
        holder.txt_order_status.setText(new StringBuilder("Order Status: ").append(Common.convertCodeToStatus(orderList.get(position).getOrderStatus())));

        holder.setItemClickListener(new IItemClickListener() {
            @Override
            public void onClick(View view, boolean isLongClick) {
                Common.currentOrder = orderList.get(position);

                context.startActivity(new Intent(context, ViewOrderDetail.class));
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }
}
