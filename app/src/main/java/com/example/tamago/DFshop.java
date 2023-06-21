package com.example.tamago;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class DFshop extends Activity {
    GameStatistic gameStatistic;





    void shopItemButton(final TextView money, int bid, int tid, final int coast, final int t, final int hun, final int heal, final int bor) {
        Button button = findViewById(bid);
        button.setText(tid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameStatistic.decreaseMoney(coast)) {
                    gameStatistic.decreaseThirst(-t);
                    gameStatistic.decreaseHunger(-hun);
                    gameStatistic.decreaseHealth(-heal);
                    gameStatistic.decreaseBoredom(-bor);
                    TextView money = findViewById(R.id.dfmoney);
                    money.setText(gameStatistic.getStringMoney());
                }


            }
        });

    }


    @SuppressLint("SourceLockedOrientationActivity")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gameStatistic = GameStatistic.getInstance(getApplicationContext());


        gameStatistic.load();
        setContentView(R.layout.dfshop);
        TextView money = findViewById(R.id.dfmoney);
        money.setText(gameStatistic.getStringMoney());


        // список еды из магазина
        shopItemButton(money, R.id.it1, R.string.FIT1, 20, 30, 0, 0, 0);
        shopItemButton(money, R.id.it2, R.string.FIT2, 26, 3, 10, 0, 0);
        shopItemButton(money, R.id.it3, R.string.FIT3, 40, -5, 27, -2, 0);
        shopItemButton(money, R.id.it4, R.string.FIT4, 100, 0, 50, -2, 5);
        shopItemButton(money, R.id.it5, R.string.FIT5, 100, 5, 40, 5, 0);
        shopItemButton(money, R.id.it6, R.string.FIT6, 125, 2, 0, -2, 50);
        shopItemButton(money, R.id.it7, R.string.FIT7, 150, 5, 75, -5, 0);
        shopItemButton(money, R.id.it8, R.string.FIT8, 200, 25, 75, 0, 0);

    }

    @Override
    protected void onResume() {
        super.onResume();
        gameStatistic.load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameStatistic.save();
    }
}
