package com.example.pizzeria;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;

public class PastaFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pasta, container, false);

        ListView listView = view.findViewById(R.id.pasta_list);
        String[] pastas = {"Паста Карбонара", "Паста Болоньезе", "Паста с грибами", "Паста с морепродуктами", "Паста с курицей", "Паста с овощами"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, pastas);
        listView.setAdapter(adapter);

        // Обработка нажатия на элемент списка
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedPasta = pastas[position];
            Intent intent = new Intent(getActivity(), DetailActivity.class);
            intent.putExtra("item", selectedPasta);
            startActivityForResult(intent, MainActivity.REQUEST_CODE_ADD_ITEM); // Используем startActivityForResult
        });

        return view;
    }
}
