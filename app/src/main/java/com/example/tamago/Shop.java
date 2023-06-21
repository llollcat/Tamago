package com.example.tamago;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

public class Shop extends Activity {
    GameStatistic gameStatistic;

    @SuppressLint("SetTextI18n")
    protected void setMoneyText() {
        TextView money = findViewById(R.id.shopMoney);
        money.setText(gameStatistic.getStringMoney());
    }


    protected void addNewItem(){


    }


    @SuppressLint("SourceLockedOrientationActivity")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gameStatistic = GameStatistic.getInstance(getApplicationContext());

        setContentView(R.layout.shop);
        setMoneyText();

        ListView listView = findViewById(R.id.shopItemList);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameStatistic.save();
    }


}
