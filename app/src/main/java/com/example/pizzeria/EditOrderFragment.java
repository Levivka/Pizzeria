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

    private final List<OrderItem> savedItems = new ArrayList<>();


    private Map<String, Integer> orderItems = new HashMap<>();
    private Map<String, Double> itemPrices = new HashMap<>();
    private double totalAmount = 0.0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_order, container, false);

        orderListView = view.findViewById(R.id.order_list);
        totalAmountTextView = view.findViewById(R.id.total_amount);
        placeOrderButton = view.findViewById(R.id.place_order_button);

        if (orderListView == null) {
            Log.e("EditOrderFragment", "Ошибка: orderListView не найден в макете!");
        } else {
            Log.d("EditOrderFragment", "orderListView успешно инициализирован.");
        }

        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        for (OrderItem item : savedItems) {
            orderItems.put(item.itemName, item.quantity);
            itemPrices.put(item.itemName, item.price);
        }

        displayOrderItems();
        placeOrderButton.setOnClickListener(v -> placeOrder());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Map<String, Integer> savedItems = OrderManager.getInstance().getOrderItems();
        if (!savedItems.isEmpty()) {
            Log.d("EditOrderFragment", "Загружаем сохранённые заказы!");
            for (Map.Entry<String, Integer> entry : savedItems.entrySet()) {
                onOrderItemAdded(entry.getKey(), entry.getValue(), OrderManager.getInstance().getItemPrice(entry.getKey(), 0.0));
            }
        }
    }



    @Override
    public void onOrderItemAdded(String itemName, int quantity, double price) {
        savedItems.add(new OrderItem(itemName, quantity, price)); // Временно сохраняем товар
        Log.d("EditOrderFragment", "Добавлен в список: " + itemName);
    }

    private void displayOrderItems() {
        Map<String, Integer> orderItems = OrderManager.getInstance().getOrderItems();
        double totalAmount = OrderManager.getInstance().getTotalAmount();

        List<String> orderList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : orderItems.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            double price = OrderManager.getInstance().getItemPrice(itemName, 0.0);
            orderList.add(itemName + " x " + quantity + " = $" + formatPrice(price * quantity));
        }

        orderListView.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, orderList));
        totalAmountTextView.setText("Итоговая сумма: $" + formatPrice(totalAmount));
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