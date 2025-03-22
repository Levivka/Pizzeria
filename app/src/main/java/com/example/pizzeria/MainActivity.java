package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    public interface OnOrderItemAddedListener {
        void onOrderItemAdded(String itemName, int quantity, double price);
    }
    private DrawerLayout drawerLayout;
    public static final int REQUEST_CODE_ADD_ITEM = 1; // Добавляем константу для запроса

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment = null;

            if (id == R.id.nav_pizza) {
                fragment = new PizzaFragment();
            } else if (id == R.id.nav_pasta) {
                fragment = new PastaFragment();
            } else if (id == R.id.nav_drinks) {
                fragment = new DrinksFragment();
            } else if (id == R.id.nav_edit_order) {
                fragment = new EditOrderFragment();
            }

            if (fragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PizzaFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_pizza);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Логирование
        Log.d("MainActivity", "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_CODE_ADD_ITEM && resultCode == RESULT_OK && data != null) {
            // Логирование
            Log.d("MainActivity", "Received data: " + data.getExtras().toString());

            String itemName = data.getStringExtra("item");
            int quantity = data.getIntExtra("quantity", 1);
            double price = data.getDoubleExtra("price", 0.0);

            // Логирование
            Log.d("MainActivity", "Received item: " + itemName + ", Quantity: " + quantity + ", Price: " + price);

            // Передаем данные через интерфейс
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragment != null && fragment.isVisible() && fragment instanceof OnOrderItemAddedListener) {
                ((OnOrderItemAddedListener) fragment).onOrderItemAdded(itemName, quantity, price);
            } else {
                Log.e("MainActivity", "EditOrderFragment is not visible or not initialized");
            }
        } else {
            Log.e("MainActivity", "Failed to receive data from DetailActivity");
        }
    }
}
