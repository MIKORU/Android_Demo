package com.example.sxs10540.uiwigettest;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sxs10540.uiadapter.FruitAdapter;
import com.example.sxs10540.uibean.Fruit;

import java.util.ArrayList;
import java.util.List;

public class ThirdActivity extends AppCompatActivity {

    private String[] data={"Apple","Banana","Orange","Grape","Pineapple",
            "Pear","Grape","Strawberry","Cherry","Mango","Apple","Banana",
            "Orange","Grape","Pineapple", "Pear","Grape","Strawberry",
            "Cherry","Mango","Cherry","Mango","Apple","Banana"};

    private List<Fruit> fruitList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
//                ThirdActivity.this,android.R.layout.simple_list_item_1,data);
//        ListView listView = (ListView) findViewById(R.id.list_view);
//        listView.setAdapter(adapter);
        initFruits();
        FruitAdapter adapter = new FruitAdapter(ThirdActivity.this,
                R.layout.fruit_item,fruitList);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Fruit fruit = fruitList.get(position);
                Toast.makeText(ThirdActivity.this, fruit.getName(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void initFruits(){
        for(int i=0;i<2;i++){
            Fruit ice1 = new Fruit("ice1",R.drawable.icecream_01);
            fruitList.add(ice1);
            Fruit ice2 = new Fruit("ice2",R.drawable.icecream_02);
            fruitList.add(ice2);
            Fruit ice3 = new Fruit("ice3",R.drawable.icecream_03);
            fruitList.add(ice3);
            Fruit ice4 = new Fruit("ice4",R.drawable.icecream_04);
            fruitList.add(ice4);
            Fruit ice5 = new Fruit("ice7",R.drawable.icecream_07);
            fruitList.add(ice5);
            Fruit ice6 = new Fruit("ice9",R.drawable.icecream_09);
            fruitList.add(ice6);
        }
    }
}
