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

public class DrinksFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_drinks, container, false);

        // Список напитков
        String[] drinks = {"Кола", "Лимонад", "Чай", "Кофе", "Сок", "Минеральная вода"};

        // Находим ListView
        ListView listView = view.findViewById(R.id.drinks_list);

        // Создаем адаптер для списка
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_1,
                drinks
        );
        listView.setAdapter(adapter);

        // Обработка нажатия на элемент списка
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedDrink = drinks[position];

            // Логирование
            Log.d("DrinksFragment", "Selected item: " + selectedDrink);

            // Запуск DetailActivity через MainActivity
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("item", selectedDrink);
            ((MainActivity) requireActivity()).detailActivityLauncher.launch(intent);
        });

        return view;
    }
}