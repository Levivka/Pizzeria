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
    private int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailTitle = findViewById(R.id.detail_title);
        detailDescription = findViewById(R.id.detail_description);
        detailPrice = findViewById(R.id.detail_price);
        quantityText = findViewById(R.id.quantity_text);
        quantityDecrease = findViewById(R.id.quantity_decrease);
        quantityIncrease = findViewById(R.id.quantity_increase);
        addToOrderButton = findViewById(R.id.add_to_order_button);

        String itemName = getIntent().getStringExtra("item");
        String itemDescription = getDescriptionForItem(itemName);
        String itemPrice = getPriceForItem(itemName);

        detailTitle.setText(itemName);
        detailDescription.setText(itemDescription);
        detailPrice.setText(itemPrice);

        quantityText.setText(String.valueOf(quantity));

        quantityDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                quantityText.setText(String.valueOf(quantity));
            }
        });

        quantityIncrease.setOnClickListener(v -> {
            quantity++;
            quantityText.setText(String.valueOf(quantity));
        });

        addToOrderButton.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("item", itemName); // Название товара
            resultIntent.putExtra("quantity", quantity); // Количество
            resultIntent.putExtra("price", getPriceValue(itemPrice)); // Цена (числовое значение)

            Log.d("DetailActivity", "Item added: " + itemName + ", Quantity: " + quantity + ", Price: " + itemPrice);

            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }

    private String getDescriptionForItem(String itemName) {
        switch (itemName) {
            case "Пицца Маргарита":
                return "Настоящая итальянская классика! Тонкое тесто, ароматный томатный соус, нежная моцарелла и свежий базилик – всё, что нужно для идеального вкуса.";
            case "Пицца Пепперони":
                return "Остренькая, ароматная и невероятно вкусная! Сочные ломтики пепперони, тягучий сыр и пикантный томатный соус на тонком хрустящем тесте.";
            case "Пицца Гавайская":
                return "Яркое сочетание вкусов: нежная курочка, сладкие ананасы и расплавленный сыр на воздушном тесте. Настоящий тропический взрыв!";
            case "Пицца Четыре Сыра":
                return "Идеальный выбор для любителей сыра! Комбинация из моцареллы, горгонзолы, пармезана и эмменталя создаёт богатый, насыщенный вкус.";
            case "Пицца Мясная":
                return "Настоящий мясной праздник! Говядина, курица, бекон и пикантная колбаска на румяном тесте с нежным сыром и томатным соусом.";
            case "Пицца Вегетарианская":
                return "Лёгкая, но невероятно вкусная! Свежие помидоры, сочный болгарский перец, шампиньоны и маслины, приправленные ароматными специями.";

            case "Паста Карбонара":
                return "Настоящая итальянская карбонара с нежным соусом из яиц и пармезана, хрустящими кусочками гуанчиале и ароматным чёрным перцем.";
            case "Паста Болоньезе":
                return "Аппетитная паста в густом мясном соусе из рубленой говядины, томатов и ароматных специй, приготовленная по традиционному рецепту.";
            case "Паста с грибами":
                return "Нежные сливки, ароматные шампиньоны и тягучий сыр создают идеальную пасту для любителей насыщенного вкуса.";
            case "Паста с морепродуктами":
                return "Креветки, мидии, кальмары и чесночный соус – идеальное сочетание для поклонников средиземноморской кухни.";
            case "Паста с курицей":
                return "Сочная курочка, нежный сливочный соус и пармезан — сочетание, которое никого не оставит равнодушным!";
            case "Паста с овощами":
                return "Яркий микс свежих овощей с ароматными специями и лёгким томатным соусом – идеальный выбор для любителей полезных блюд.";

            case "Кола":
                return "Классическая газировка с бодрящим вкусом. Идеально освежает и дополняет любую пиццу!";
            case "Лимонад":
                return "Домашний лимонад с дольками лимона, мятой и лёгкой газировкой – освежает и дарит заряд бодрости!";
            case "Чай":
                return "Чёрный, зелёный или травяной – выберите свой идеальный чай для уютного вечера.";
            case "Кофе":
                return "Крепкий эспрессо, воздушный капучино или нежный латте – бодрящий ритуал на любой вкус!";
            case "Сок":
                return "Свежевыжатый сок из сочных фруктов – натуральный источник витаминов и энергии.";
            case "Минеральная вода":
                return "Чистая, освежающая вода с лёгким природным вкусом – с газом или без.";

            default:
                return "Описание блюда отсутствует.";
        }
    }

    private String getPriceForItem(String itemName) {
        switch (itemName) {
            case "Пицца Маргарита":
                return "Цена: $10.00";
            case "Пицца Пепперони":
                return "Цена: $12.00";
            case "Пицца Гавайская":
                return "Цена: $11.00";
            case "Пицца Четыре Сыра":
                return "Цена: $13.00";
            case "Пицца Мясная":
                return "Цена: $14.00";
            case "Пицца Вегетарианская":
                return "Цена: $9.00";

            case "Паста Карбонара":
                return "Цена: $8.00";
            case "Паста Болоньезе":
                return "Цена: $9.00";
            case "Паста с грибами":
                return "Цена: $7.50";
            case "Паста с морепродуктами":
                return "Цена: $10.00";
            case "Паста с курицей":
                return "Цена: $8.50";
            case "Паста с овощами":
                return "Цена: $7.00";

            case "Кола":
                return "Цена: $2.00";
            case "Лимонад":
                return "Цена: $3.50";
            case "Чай":
                return "Цена: $2.50";
            case "Кофе":
                return "Цена: $4.00";
            case "Сок":
                return "Цена: $3.00";
            case "Минеральная вода":
                return "Цена: $1.50";

            default:
                return "Цена: $0.00";
        }
    }

    private double getPriceValue(String priceText) {
        Log.d("DetailActivity", "Price text: " + priceText);

        String numericValue = priceText.replaceAll("[^\\d.]", "");

        Log.d("DetailActivity", "Numeric value: " + numericValue);

        return Double.parseDouble(numericValue);
    }
}