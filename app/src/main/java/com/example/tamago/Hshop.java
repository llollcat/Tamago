package com.example.tamago;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

public class Hshop extends Activity {
    GameStatistic gameStatistic;

    void shopItemButton(final TextView money, int bid, int tid, final int coast, final int t, final int hun, final int heal, final int bor) {
        Button it1 = findViewById(bid);
        it1.setText(tid);
        it1.setOnClickListener(new View.OnClickListener() {
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
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        gameStatistic = GameStatistic.getInstance(getApplicationContext());


        setContentView(R.layout.dfshop);
        TextView money = findViewById(R.id.dfmoney);
        money.setText(gameStatistic.getStringMoney());


        // сон
        Button button = findViewById(R.id.it1);
        button.setText(R.string.HIT1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int difference = GameStatistic.MAX_STAT - gameStatistic.getBoredom();
                gameStatistic.decreaseThirst(difference);
                gameStatistic.decreaseHunger(difference);
                gameStatistic.decreaseHealth(difference / 2);
                gameStatistic.setBoredom(GameStatistic.MAX_STAT);


            }
        });
        //Товары в магазине
        shopItemButton(money, R.id.it2, R.string.HIT2, 0, -10, -5, 20, -5);
        shopItemButton(money, R.id.it3, R.string.HIT3, 20, 0, 0, 10, -10);
        shopItemButton(money, R.id.it4, R.string.HIT4, 60, -10, 0, 45, -10);
        shopItemButton(money, R.id.it5, R.string.HIT5, 150, 5, 0, 70, 0);
        shopItemButton(money, R.id.it6, R.string.HIT6, 170, 0, 0, 85, 0);
        shopItemButton(money, R.id.it7, R.string.HIT7, 180, -5, -5, 100, -5);
        shopItemButton(money, R.id.it8, R.string.HIT8, 500, GameStatistic.MAX_STAT, GameStatistic.MAX_STAT, GameStatistic.MAX_STAT, GameStatistic.MAX_STAT);


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
