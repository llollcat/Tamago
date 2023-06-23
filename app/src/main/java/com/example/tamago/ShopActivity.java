package com.example.tamago;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public abstract class ShopActivity extends Activity {
    protected GameStatistic gameStatistic;

    @SuppressLint("SetTextI18n")
    protected void setMoneyText() {
        TextView money = findViewById(R.id.shopMoney);
        money.setText(gameStatistic.getStringMoney());
    }

    private ArrayList<ShopItem> shopItems = new ArrayList<>();

    protected abstract void init();


    protected void addNewItem(ShopItem shopItem) {
        shopItems.add(shopItem);

    }


    @SuppressLint("SourceLockedOrientationActivity")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        gameStatistic = GameStatistic.getInstance();

        setContentView(R.layout.shop);
        ListView listView = findViewById(R.id.shopItemList);
        init();
        ArrayAdapter<ShopItem> adapter = new ArrayAdapter<ShopItem>(this, android.R.layout.simple_list_item_1, shopItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemPosition, long l) {
                if (shopItems.get(itemPosition).isEnoughMoney(gameStatistic)) {
                    shopItems.get(itemPosition).beSold(gameStatistic);
                    setMoneyText();
                } else
                    Toast.makeText(getApplicationContext(), R.string.End, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setMoneyText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameStatistic.save();
    }


}
