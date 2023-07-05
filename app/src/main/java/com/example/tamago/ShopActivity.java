package com.example.tamago;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public abstract class ShopActivity extends Activity {
    protected GameStatistic gameStatistic;

    private final ArrayList<ShopItem> shopItems = new ArrayList<>();

    protected abstract void init();


    protected void addNewItem(ShopItem shopItem) {
        shopItems.add(shopItem);

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);


        gameStatistic = GameStatistic.getInstance();


        ListView listView = findViewById(R.id.shop_item_list_listView);
        init();

        ArrayAdapter<ShopItem> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, shopItems);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((adapterView, view, itemPosition, l) -> {
            if (shopItems.get(itemPosition).isEnoughMoney(gameStatistic)) {
                shopItems.get(itemPosition).beSold(gameStatistic);
                TextView money = findViewById(R.id.shop_money_textView);
                money.setText(String.format(Locale.getDefault(), "%d", gameStatistic.getMoney()));
            } else
                Toast.makeText(getApplicationContext(), R.string.End, Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView money = findViewById(R.id.shop_money_textView);
        money.setText(String.format(Locale.getDefault(), "%d", gameStatistic.getMoney()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameStatistic.save();
    }


}
