package com.example.sxs10540.uifragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sxs10540.uiadapter.FruitAdapter;
import com.example.sxs10540.uibean.Fruit;
import com.example.sxs10540.uiwigettest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxs10540 on 2017/8/3.
 */

public class FruitFragment extends Fragment {

    private Context context;

    private String[] data = {"Apple", "Banana", "Orange", "Grape", "Pineapple",
            "Pear", "Grape", "Strawberry", "Cherry", "Mango", "Apple", "Banana",
            "Orange", "Grape", "Pineapple", "Pear", "Grape", "Strawberry",
            "Cherry", "Mango", "Cherry", "Mango", "Apple", "Banana"};

    private List<Fruit> fruitList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_third, container, false);
        context = getContext();

        initFruits();
        FruitAdapter adapter = new FruitAdapter(context,
                R.layout.fruit_item, fruitList);
        ListView listView = (ListView) view.findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(context, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;

    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            Fruit ice1 = new Fruit("ice1", R.drawable.icecream_01);
            fruitList.add(ice1);
            Fruit ice2 = new Fruit("ice2", R.drawable.icecream_02);
            fruitList.add(ice2);
            Fruit ice3 = new Fruit("ice3", R.drawable.icecream_03);
            fruitList.add(ice3);
            Fruit ice4 = new Fruit("ice4", R.drawable.icecream_04);
            fruitList.add(ice4);
            Fruit ice5 = new Fruit("ice7", R.drawable.icecream_07);
            fruitList.add(ice5);
            Fruit ice6 = new Fruit("ice9", R.drawable.icecream_09);
            fruitList.add(ice6);
        }
    }
}
