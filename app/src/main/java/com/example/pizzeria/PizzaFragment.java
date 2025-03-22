package com.example.pizzeria;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class PizzaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza, container, false);

        ListView listView = view.findViewById(R.id.pizza_list);
        String[] pizzas = {"Пицца Маргарита", "Пицца Пепперони", "Пицца Гавайская", "Пицца Четыре Сыра", "Пицца Мясная", "Пицца Вегетарианская"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, pizzas);
        listView.setAdapter(adapter);

        // Обработка нажатия на элемент списка
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedPizza = pizzas[position];

            // Логирование
            Log.d("PizzaFragment", "Selected item: " + selectedPizza);

            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("item", selectedPizza);
            startActivityForResult(intent, MainActivity.REQUEST_CODE_ADD_ITEM); // Используем startActivityForResult
        });

        return view;
    }
}
