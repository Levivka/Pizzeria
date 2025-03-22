package com.example.pizzeria;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditOrderFragment extends Fragment implements MainActivity.OnOrderItemAddedListener {

    private ListView orderListView;
    private TextView totalAmountTextView;
    private Button placeOrderButton;

    private Map<String, Integer> orderItems = new HashMap<>();
    private Map<String, Double> itemPrices = new HashMap<>();
    private double totalAmount = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_order, container, false);

        // Инициализация элементов
        orderListView = view.findViewById(R.id.order_list);
        totalAmountTextView = view.findViewById(R.id.total_amount);
        placeOrderButton = view.findViewById(R.id.place_order_button);

        // Отображаем заказы
        displayOrderItems();

        // Обработка нажатия на кнопку "Оформить заказ"
        placeOrderButton.setOnClickListener(v -> placeOrder());

        return view;
    }

    @Override
    public void onOrderItemAdded(String itemName, int quantity, double price) {
        // Логирование
        Log.d("EditOrderFragment", "Adding item: " + itemName + ", Quantity: " + quantity + ", Price: " + price);

        // Добавляем товар в заказ
        orderItems.put(itemName, quantity);
        itemPrices.put(itemName, price);
        totalAmount += price * quantity;

        // Логирование
        Log.d("EditOrderFragment", "Current order items: " + orderItems.toString());
        Log.d("EditOrderFragment", "Current total amount: " + totalAmount);

        // Обновляем отображение
        displayOrderItems();
    }

    // Отображение списка заказов
    private void displayOrderItems() {
        List<String> orderList = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            double price = itemPrices.getOrDefault(itemName, 0.0);
            double itemTotal = price * quantity;

            orderList.add(itemName + " x " + quantity + " = $" + formatPrice(itemTotal));
        }

        // Логирование
        Log.d("EditOrderFragment", "Displaying order list: " + orderList.toString());

        // Адаптер для списка заказов
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                orderList
        );
        orderListView.setAdapter(adapter);

        // Отображение итоговой суммы
        totalAmountTextView.setText("Итоговая сумма: $" + formatPrice(totalAmount));

        // Логирование
        Log.d("EditOrderFragment", "Total amount displayed: $" + formatPrice(totalAmount));
    }

    // Оформление заказа
    private void placeOrder() {
        if (getActivity() != null) {
            android.widget.Toast.makeText(getActivity(), "Заказ оформлен!", android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    // Форматирование цены
    private String formatPrice(double price) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        return decimalFormat.format(price);
    }
}
