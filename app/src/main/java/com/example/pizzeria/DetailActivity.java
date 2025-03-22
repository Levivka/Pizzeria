package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {

    private TextView detailTitle, detailDescription, detailPrice, quantityText;
    private Button quantityDecrease, quantityIncrease, addToOrderButton;
    private int quantity = 1; // Количество товара по умолчанию

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Инициализация View элементов
        detailTitle = findViewById(R.id.detail_title);
        detailDescription = findViewById(R.id.detail_description);
        detailPrice = findViewById(R.id.detail_price);
        quantityText = findViewById(R.id.quantity_text);
        quantityDecrease = findViewById(R.id.quantity_decrease);
        quantityIncrease = findViewById(R.id.quantity_increase);
        addToOrderButton = findViewById(R.id.add_to_order_button);

        // Получаем данные из Intent
        String itemName = getIntent().getStringExtra("item");
        String itemDescription = getDescriptionForItem(itemName);
        String itemPrice = getPriceForItem(itemName);

        // Устанавливаем данные
        detailTitle.setText(itemName);
        detailDescription.setText(itemDescription);
        detailPrice.setText(itemPrice);

        // Обновляем количество
        quantityText.setText(String.valueOf(quantity));

        // Уменьшение количества
        quantityDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
            }
        });

        // Увеличение количества
        quantityIncrease.setOnClickListener(v -> {
            quantity++;
            quantityText.setText(String.valueOf(quantity));
        });

        // Добавление в заказ
        addToOrderButton.setOnClickListener(v -> {
            // Создаем Intent для передачи данных
            Intent resultIntent = new Intent();
            resultIntent.putExtra("item", itemName); // Название товара
            resultIntent.putExtra("quantity", quantity); // Количество
            resultIntent.putExtra("price", getPriceValue(itemPrice)); // Цена (числовое значение)

            // Логирование
            Log.d("DetailActivity", "Item added: " + itemName + ", Quantity: " + quantity + ", Price: " + itemPrice);
            Log.d("DetailActivity", "ResultIntent data: " + resultIntent.getExtras().toString());

            // Устанавливаем результат и закрываем активность
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    // Метод для получения описания блюда
    private String getDescriptionForItem(String itemName) {
        switch (itemName) {
            case "Пицца Маргарита":
                return "Классическая пицца с томатным соусом, моцареллой и базиликом.";
            case "Пицца Пепперони":
                return "Пицца с острой колбасой пепперони и сыром.";
            case "Паста Карбонара":
                return "Паста с соусом из яиц, сыра пармезан, гуанчиале и черного перца.";
            case "Кола":
                return "Освежающий газированный напиток.";
            default:
                return "Описание блюда отсутствует.";
        }
    }

    // Метод для получения цены блюда
    private String getPriceForItem(String itemName) {
        switch (itemName) {
            case "Пицца Маргарита":
                return "Цена: $10.00";
            case "Пицца Пепперони":
                return "Цена: $12.00";
            case "Паста Карбонара":
                return "Цена: $8.00";
            case "Кола":
                return "Цена: $2.00";
            default:
                return "Цена: $0.00";
        }
    }

    private double getPriceValue(String priceText) {
        // Логирование
        Log.d("DetailActivity", "Price text: " + priceText);

        // Удаляем все символы, кроме цифр и точки
        String numericValue = priceText.replaceAll("[^\\d.]", "");

        // Логирование
        Log.d("DetailActivity", "Numeric value: " + numericValue);

        return Double.parseDouble(numericValue);
    }
}
