package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    // Создаем лаунчер для запуска активности и обработки результата
    final ActivityResultLauncher<Intent> detailActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    String itemName = data.getStringExtra("item");
                    int quantity = data.getIntExtra("quantity", 1);
                    double price = data.getDoubleExtra("price", 0.0);

                    // Логирование
                    Log.d("MainActivity", "Received item: " + itemName + ", Quantity: " + quantity + ", Price: " + price);

                    // Передаем данные в EditOrderFragment
                    Fragment existingFragment = getSupportFragmentManager().findFragmentByTag("EDIT_ORDER");

                    if (existingFragment instanceof OnOrderItemAddedListener) {
                        ((OnOrderItemAddedListener) existingFragment).onOrderItemAdded(itemName, quantity, price);
                    } else {
                        Log.e("MainActivity", "EditOrderFragment не найден! Сохраняем заказ временно.");
                        OrderManager.getInstance().addItem(itemName, quantity, price);
                    }


                } else {
                    Log.e("MainActivity", "Failed to receive data from DetailActivity");
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Инициализация DrawerLayout и NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment = null;

            // Выбор фрагмента в зависимости от выбранного пункта меню
            if (id == R.id.nav_pizza) {
                fragment = new PizzaFragment();
            } else if (id == R.id.nav_pasta) {
                fragment = new PastaFragment();
            } else if (id == R.id.nav_drinks) {
                fragment = new DrinksFragment();
            } else if (id == R.id.nav_edit_order) {
                fragment = new EditOrderFragment();
            }

            // Замена фрагмента в контейнере
            if (fragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit();
            }

            // Закрытие NavigationDrawer
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Инициализация ActionBarDrawerToggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Установка начального фрагмента (например, PizzaFragment)
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new PizzaFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_pizza);
        }
    }

    // Интерфейс для передачи данных
    public interface OnOrderItemAddedListener {
        void onOrderItemAdded(String itemName, int quantity, double price);
    }

    // Метод для запуска DetailActivity из фрагментов
    public void launchDetailActivity(String itemName) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("item", itemName);
        detailActivityLauncher.launch(intent);
    }
}