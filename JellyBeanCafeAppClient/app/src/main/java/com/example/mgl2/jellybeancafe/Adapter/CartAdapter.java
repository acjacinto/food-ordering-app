package com.example.mgl2.jellybeancafe.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.mgl2.jellybeancafe.Database.ModelDB.Cart;
import com.example.mgl2.jellybeancafe.Database.ModelDB.Favorite;
import com.example.mgl2.jellybeancafe.R;
import com.example.mgl2.jellybeancafe.Utils.Common;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder>{

    Context context;
    List<Cart> cartList;

    public CartAdapter(Context context, List<Cart> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.cart_item_layout,parent,false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final CartViewHolder holder, final int position) {

        String currentMenuId = cartList.get(position).name;

        //For Drinks
        if(currentMenuId.toString().equals("White Mocha Frappe") || currentMenuId.toString().equals("Chocolate Frappe")
                || currentMenuId.toString().equals("Cookies n Cream Frappe") || currentMenuId.toString().equals("Coffee Frappe")
                || currentMenuId.toString().equals("Mocha Frappe") || currentMenuId.toString().equals("Blue Lemonade Slush")
                || currentMenuId.toString().equals("Lemon and Mint Slush") || currentMenuId.toString().equals("JB Milk Tea")
                || currentMenuId.toString().equals("Almond Roca Milk Tea") || currentMenuId.toString().equals("Creme Caramel Milk Tea")
                || currentMenuId.toString().equals("Black") || currentMenuId.toString().equals("Cappuccino")
                || currentMenuId.toString().equals("Flat White") || currentMenuId.toString().equals("Latte")
                || currentMenuId.toString().equals("Macchiato") || currentMenuId.toString().equals("Mocha")
                || currentMenuId.toString().equals("Mochaccino")) {
            Picasso.with(context)
                    .load(cartList.get(position).link)
                    .into(holder.img_product);

            holder.txt_amount.setNumber(String.valueOf(cartList.get(position).amount));
            holder.txt_price.setText(new StringBuilder("₱").append(cartList.get(position).price).append("0"));
            holder.txt_product_name.setText(new StringBuilder(cartList.get(position).name)
                    .append(" x")
                    .append(cartList.get(position).amount)
                    .append((cartList.get(position).size == 0 ? " Regular " : " Large ")));
            holder.txt_sugar_ice.setText(new StringBuilder("Sugar: ").append(cartList.get(position).sugar)
                    .append("%").toString());

            //For Pizza
        }else if(currentMenuId.toString().equals("Hawaiian Pizza") || currentMenuId.toString().equals("All Meat Pizza")
                || currentMenuId.toString().equals("Supreme Pizza")){
            Picasso.with(context)
                    .load(cartList.get(position).link)
                    .into(holder.img_product);

            holder.txt_amount.setNumber(String.valueOf(cartList.get(position).amount));
            holder.txt_price.setText(new StringBuilder("₱").append(cartList.get(position).price).append("0"));
            holder.txt_product_name.setText(new StringBuilder(cartList.get(position).name)
                    .append(" x")
                    .append(cartList.get(position).amount)
                    .append((cartList.get(position).size == 0 ? " Sliced (1/4) " : " Whole ")));
            holder.txt_sugar_ice.setText(new StringBuilder(" "));


            //For Cakes
        }else if(currentMenuId.toString().equals("Carrot Cake") || currentMenuId.toString().equals("Chocolate Cake")
                || currentMenuId.toString().equals("Blueberry Cheese Cake") || currentMenuId.toString().equals("Strawberry Cheese Cake")){
            Picasso.with(context)
                    .load(cartList.get(position).link)
                    .into(holder.img_product);

            holder.txt_amount.setNumber(String.valueOf(cartList.get(position).amount));
            holder.txt_price.setText(new StringBuilder("₱").append(cartList.get(position).price).append("0"));
            holder.txt_product_name.setText(new StringBuilder(cartList.get(position).name)
                    .append(" x")
                    .append(cartList.get(position).amount)
                    .append((cartList.get(position).size == 0 ? " Sliced " : " Whole ")));
            holder.txt_sugar_ice.setText(new StringBuilder(" "));


            //For Other Solos (Jelly, Cupcake, Topping)
        }else {
            Picasso.with(context)
                    .load(cartList.get(position).link)
                    .into(holder.img_product);

            holder.txt_amount.setNumber(String.valueOf(cartList.get(position).amount));
            holder.txt_price.setText(new StringBuilder("₱").append(cartList.get(position).price).append("0"));
            holder.txt_product_name.setText(new StringBuilder(cartList.get(position).name)
                    .append(" x")
                    .append(cartList.get(position).amount));
            holder.txt_sugar_ice.setText(new StringBuilder(" "));
        }


        //Get Price of one cup with all options
        final double priceOneCup = cartList.get(position).price / cartList.get(position).amount;




        //Auto save item when user change amount
        holder.txt_amount.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                Cart cart = cartList.get(position);
                cart.amount = newValue;
                cart.price = Math.round(priceOneCup*newValue);

                Common.cartRepository.updateCart(cart);

                holder.txt_price.setText(new StringBuilder("₱").append(cartList.get(position).price));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder
    {
        ImageView img_product;
        TextView txt_product_name,txt_sugar_ice,txt_price;
        ElegantNumberButton txt_amount;

        public RelativeLayout view_background;
        public LinearLayout view_foreground;

        public CartViewHolder(View itemView) {
            super(itemView);

            img_product = (ImageView)itemView.findViewById(R.id.img_product);
            txt_amount = (ElegantNumberButton)itemView.findViewById(R.id.txt_amount);
            txt_product_name = (TextView)itemView.findViewById(R.id.txt_product_name);
            txt_sugar_ice = (TextView)itemView.findViewById(R.id.txt_sugar_ice);
            txt_price = (TextView)itemView.findViewById(R.id.txt_price);
            txt_amount = (ElegantNumberButton)itemView.findViewById(R.id.txt_amount);

            view_background = (RelativeLayout)itemView.findViewById(R.id.view_background);
            view_foreground = (LinearLayout)itemView.findViewById(R.id.view_foreground);
        }
    }

    public void removeItem(int position)
    {
        cartList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Cart item, int position)
    {
        cartList.add(position,item);
        notifyItemInserted(position);
    }
}
