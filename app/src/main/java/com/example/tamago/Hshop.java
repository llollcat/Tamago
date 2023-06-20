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

public class Hshop extends Activity {


    private final SharedPreferences sharedStatistic = getSharedPreferences(Constants.SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE);

    public void save(String name, int value, String string, boolean isSavingString) {
        SharedPreferences.Editor statisticEditor = getSharedPreferences(Constants.SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE).edit();
        if (isSavingString)
            statisticEditor.putString(name, string);

        else
            statisticEditor.putInt(name, value);
        statisticEditor.apply();
    }

    @SuppressLint("SetTextI18n")
    void setmoneytext(TextView money) {
        money.setText(Double.toString(gameStatistic.getMoney()));

    }

    GameStatistic gameStatistic = new GameStatistic();


    void load() {


        gameStatistic.setHunger(sharedStatistic.getInt("hunger", GameStatistic.maxStat));
        gameStatistic.setThirst(sharedStatistic.getInt("thirst", GameStatistic.maxStat));
        gameStatistic.setBoredom(sharedStatistic.getInt("boredom", GameStatistic.maxStat));
        gameStatistic.setHealth(sharedStatistic.getInt("health", GameStatistic.maxStat));
        gameStatistic.decreaseMoney(Double.parseDouble(Objects.requireNonNull(sharedStatistic.getString("money", "1000.00"))));

    }

    void buttonl(final TextView money, int bid, int tid, final double coast, final int t, final int hun, final int heal, final int bor) {
        Button it1 = findViewById(bid);
        it1.setText(tid);
        it1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameStatistic.decreaseMoney(coast)) {
                    setmoneytext(money);
                    gameStatistic.setThirst(-t);
                    gameStatistic.setHunger(-hun);
                    gameStatistic.setHealth(-heal);
                    gameStatistic.setBoredom(-bor);
                }

            }
        });

    }


    @SuppressLint("SourceLockedOrientationActivity")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        gameStatistic.decreaseMoney(Double.parseDouble(Objects.requireNonNull(sharedStatistic.getString("money", "1000.00"))));
        setContentView(R.layout.dfshop);
        TextView money = findViewById(R.id.dfmoney);
        setmoneytext(money);
        load();


        // сон
        Button it1 = findViewById(R.id.it1);
        it1.setText(R.string.HIT1);
        it1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int difference = GameStatistic.maxStat - gameStatistic.getBoredom();
                gameStatistic.setThirst(difference);
                gameStatistic.setHunger(difference);
                gameStatistic.setHealth(difference / 2);
                gameStatistic.setBoredom(GameStatistic.maxStat);


            }
        });
        //Товары в магазине
        buttonl(money, R.id.it2, R.string.HIT2, 0, -10, -5, 20, -5);
        buttonl(money, R.id.it3, R.string.HIT3, 20, 0, 0, 10, -10);
        buttonl(money, R.id.it4, R.string.HIT4, 60, -10, 0, 45, -10);
        buttonl(money, R.id.it5, R.string.HIT5, 150, 5, 0, 70, 0);
        buttonl(money, R.id.it6, R.string.HIT6, 170, 0, 0, 85, 0);
        buttonl(money, R.id.it7, R.string.HIT7, 180, -5, -5, 100, -5);
        buttonl(money, R.id.it8, R.string.HIT8, 500, GameStatistic.maxStat, GameStatistic.maxStat, GameStatistic.maxStat, GameStatistic.maxStat);


    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        save("hunger", gameStatistic.getHunger(), "", false);
        save("thirst", gameStatistic.getThirst(), "", false);
        save("boredom", gameStatistic.getBoredom(), "", false);
        save("health", gameStatistic.getHealth(), "", false);
        save("money", 0, Double.toString(gameStatistic.getMoney()), true);
    }
}
