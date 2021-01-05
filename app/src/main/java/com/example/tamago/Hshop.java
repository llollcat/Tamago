package com.example.tamago;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class Hshop extends Activity {


    private SharedPreferences tosaveorload;

    void save(String name, int value, String string, boolean opcode) {
        tosaveorload = getSharedPreferences("Stat", Context.MODE_PRIVATE);
        if (opcode) {
            SharedPreferences.Editor editor = tosaveorload.edit();
            editor.putString(name, string);
            editor.apply();

        } else {
            SharedPreferences.Editor editor = tosaveorload.edit();
            editor.putInt(name, value);
            editor.apply();

        }


    }

    @SuppressLint("SetTextI18n")
    void setmoneytext(TextView money) {
        money.setText(Double.toString(stat.money));

    }

    Stat stat = new Stat();


    void load() {


        stat.hunger = tosaveorload.getInt("hunger", stat.maxStat);
        stat.thirst = tosaveorload.getInt("thirst", stat.maxStat);
        stat.boredom = tosaveorload.getInt("boredom", stat.maxStat);
        stat.health = tosaveorload.getInt("health", stat.maxStat);
        stat.money = Double.parseDouble(tosaveorload.getString("money", "1000.00"));
        Log.i("?!?", "run: данные должны быть загруженны");

    }

    void buttonl(final TextView money, int bid, int tid, final double coast, final int t, final int hun, final int heal, final int bor) {
        Button it1 = findViewById(bid);
        it1.setText(tid);
        it1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (stat.setMoney(coast, getApplicationContext())) {
                    setmoneytext(money);
                    stat.setThirst(-t);
                    stat.setHunger(-hun);
                    stat.setHealth(-heal);
                    stat.setBoredom(-bor);
                }

            }
        });

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        tosaveorload = getSharedPreferences("Stat", Context.MODE_PRIVATE);
        stat.money = Double.parseDouble(tosaveorload.getString("money", "1000.00"));
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
                int difference = Stat.maxStat - stat.boredom;
                stat.setThirst(difference);
                stat.setHunger(difference);
                stat.setHealth(difference / 2);
                stat.boredom = Stat.maxStat;


            }
        });
        //Товары в магазине
        buttonl(money, R.id.it2, R.string.HIT2, 0, -10, -5, 20, -5);
        buttonl(money, R.id.it3, R.string.HIT3, 20, 0, 0, 10, -10);
        buttonl(money, R.id.it4, R.string.HIT4, 60, -10, 0, 45, -10);
        buttonl(money, R.id.it5, R.string.HIT5, 150, 5, 0, 70, 0);
        buttonl(money, R.id.it6, R.string.HIT6, 170, 0, 0, 85, 0);
        buttonl(money, R.id.it7, R.string.HIT7, 180, -5, -5, 100, -5);
        buttonl(money, R.id.it8, R.string.HIT8, 500, Stat.maxStat, Stat.maxStat, Stat.maxStat, Stat.maxStat);


    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    @Override
    protected void onPause() {
        super.onPause();
        save("hunger", stat.hunger, "", false);
        save("thirst", stat.thirst, "", false);
        save("boredom", stat.boredom, "", false);
        save("health", stat.health, "", false);
        save("money", 0, Double.toString(stat.getMoney()), true);
        Log.i("?!?", "pause: Данные должны быть сохранены ");
    }
}
