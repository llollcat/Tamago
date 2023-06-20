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
    private final SharedPreferences sharedStatistic = getSharedPreferences(Constants.SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE);
    GameStatistic gameStatistic = new GameStatistic();

    public void save(String name, int value, String string, boolean isSavingString) {
        SharedPreferences.Editor statisticEditor = getSharedPreferences(Constants.SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE).edit();
        if (isSavingString)
            statisticEditor.putString(name, string);

        else
            statisticEditor.putInt(name, value);
        statisticEditor.apply();
    }

    void load() {
        gameStatistic.setHunger(sharedStatistic.getInt("hunger", GameStatistic.maxStat));
        gameStatistic.setThirst(sharedStatistic.getInt("thirst", GameStatistic.maxStat));
        gameStatistic.setBoredom(sharedStatistic.getInt("boredom", GameStatistic.maxStat));
        gameStatistic.setHealth(sharedStatistic.getInt("health", GameStatistic.maxStat));
        gameStatistic.decreaseMoney(Double.parseDouble(Objects.requireNonNull(sharedStatistic.getString("money", "1000.00"))));

    }

    @SuppressLint("SetTextI18n")
    void setMoneyText(TextView money) {
        money.setText(Double.toString(gameStatistic.getMoney()));
    }




    void buttonl(final TextView money, int bid, int tid, final double coast, final int t, final int hun, final int heal, final int bor) {
        Button it1 = findViewById(bid);
        it1.setText(tid);
        it1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (gameStatistic.decreaseMoney(coast)) {
                    setMoneyText(money);
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

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        gameStatistic.decreaseMoney(Double.parseDouble(Objects.requireNonNull(sharedStatistic.getString("money", "1000.00"))));
        setContentView(R.layout.dfshop);
        TextView money = findViewById(R.id.dfmoney);
        setMoneyText(money);
        load();

        // список еды из магазина
        buttonl(money, R.id.it1, R.string.FIT1, 20.0, 30, 0, 0, 0);
        buttonl(money, R.id.it2, R.string.FIT2, 26.0, 3, 10, 0, 0);
        buttonl(money, R.id.it3, R.string.FIT3, 40.0, -5, 27, -2, 0);
        buttonl(money, R.id.it4, R.string.FIT4, 100.0, 0, 50, -2, 5);
        buttonl(money, R.id.it5, R.string.FIT5, 100.0, 5, 40, 5, 0);
        buttonl(money, R.id.it6, R.string.FIT6, 125.0, 2, 0, -2, 50);
        buttonl(money, R.id.it7, R.string.FIT7, 150.0, 5, 75, -5, 0);
        buttonl(money, R.id.it8, R.string.FIT8, 200.0, 25, 75, 0, 0);

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
