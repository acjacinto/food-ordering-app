package com.example.mgl2.jellybeancafeserver;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.mgl2.jellybeancafeserver.Adapter.OrderViewAdapter;
import com.example.mgl2.jellybeancafeserver.Model.Order;
import com.example.mgl2.jellybeancafeserver.Retrofit.IJBCafeAPI;
import com.example.mgl2.jellybeancafeserver.Utils.Common;

import java.util.Collections;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ShowOrderActivity extends AppCompatActivity {

    IJBCafeAPI mService;
    RecyclerView recycler_orders;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);


        mService = Common.getAPI();

        recycler_orders = (RecyclerView) findViewById(R.id.recycler_orders);
        recycler_orders.setLayoutManager(new LinearLayoutManager(this));
        recycler_orders.setHasFixedSize(true);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.order_new) {
                    loadOrder("0");
                } else if (item.getItemId() == R.id.order_cancel) {
                    loadOrder("-1");
                } else if (item.getItemId() == R.id.order_processing) {
                    loadOrder("1");
                } else if (item.getItemId() == R.id.order_shipping) {
                    loadOrder("2");
                } else if (item.getItemId() == R.id.order_shipped) {
                    loadOrder("3");
                }
                return true;
            }
        });

        loadOrder("0");
    }

    private void loadOrder(String code) {
        compositeDisposable.add(mService.getAllOrder(code)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Consumer<List<Order>>() {
                    @Override
                    public void accept(List<Order> orders) throws Exception {
                        displayOrder(orders);
                    }
                })

        );
    }

    private void displayOrder(List<Order> orders) {
        Collections.reverse(orders);
        OrderViewAdapter adapter = new OrderViewAdapter(this,orders);
        recycler_orders.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadOrder("0");
    }
}
