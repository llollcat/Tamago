package com.example.tamago;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class Game extends Activity {

    private final SharedPreferences sharedStatistic = getSharedPreferences(Constants.SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE);

    Integer decrease = 0;
    Boolean work = false;
    Boolean loadpls = false;
    Boolean killtread = false;
    GameStatistic gameStatistic = new GameStatistic();
    Mood mood = new Mood();
    Boolean yasdoh = false;


    public void save(String name, int value, String string, boolean isSavingString) {
        SharedPreferences.Editor statisticEditor = getSharedPreferences(Constants.SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE).edit();
        if (isSavingString) statisticEditor.putString(name, string);

        else statisticEditor.putInt(name, value);
        statisticEditor.apply();
    }


    @Override
    public void onBackPressed() {


    }

    //Тред, который выполняет все основные операции
    void treaddecreaser() {
        new Thread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {

                ProgressBar GHunger = findViewById(R.id.GHunger);
                ProgressBar GThirst = findViewById(R.id.GThirst);
                ProgressBar GBoredom = findViewById(R.id.GBoredom);
                ProgressBar GHealth = findViewById(R.id.GHealth);
                TextView time = findViewById(R.id.GTime);
                TextView money = findViewById(R.id.GMoney);
                TextView emoji = findViewById(R.id.GEnt);
                int isneed = 1;
                while (!killtread) {


                    if (loadpls) {
                        gameStatistic.setPlayTime(sharedStatistic.getInt("pasttime", 0));
                        gameStatistic.setHunger(sharedStatistic.getInt("hunger", GameStatistic.maxStat));
                        gameStatistic.setThirst(sharedStatistic.getInt("thirst", GameStatistic.maxStat));
                        gameStatistic.setBoredom( sharedStatistic.getInt("boredom", GameStatistic.maxStat));
                        gameStatistic.setHealth(sharedStatistic.getInt("health", GameStatistic.maxStat));
                        gameStatistic.decreaseMoney(Double.parseDouble(Objects.requireNonNull(sharedStatistic.getString("money", "750.0"))));
                        decrease = sharedStatistic.getInt("decrease", 1);
                        Log.i("?!?", "run: данные должны быть загруженны");
                        loadpls = false;
                    }

                    if (work) {


                        gameStatistic.setBoredom(decrease);
                        GBoredom.setProgress(gameStatistic.getBoredom());

                        gameStatistic.setHunger(decrease);
                        GHunger.setProgress(gameStatistic.getHunger());

                        gameStatistic.setThirst(decrease);
                        GThirst.setProgress(gameStatistic.getThirst());

                        gameStatistic.setHealth(decrease);
                        GHealth.setProgress(gameStatistic.getHealth());


                        time.setText((Integer.toString(gameStatistic.getPlayTime())));

                        money.setText(Double.toString(gameStatistic.getMoney()));


                        // установщик emoji и умиратель

                        if (gameStatistic.getHunger() <= 0 || gameStatistic.getThirst() <= 0 || gameStatistic.getBoredom() <= 0 || gameStatistic.getHealth() <= 0) {
                            sharedStatistic.edit().clear().apply();

                            SharedPreferences best = getSharedPreferences(Constants.SHARED_PREFERENCE_BEST_SCORE, Context.MODE_PRIVATE);
                            yasdoh = true;


                            killtread = true;
                            if (gameStatistic.getPlayTime() > best.getInt(Constants.SHARED_PREFERENCE_BEST_SCORE, 0)) {

                                SharedPreferences.Editor editor = best.edit();
                                editor.putInt(Constants.SHARED_PREFERENCE_BEST_SCORE, gameStatistic.getPlayTime());
                                editor.apply();


                            }

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), R.string.End, Toast.LENGTH_LONG).show();
                                }
                            });


                            startActivity(new Intent(Game.this, MainActivity.class));

                        // todo переделать
                        } else {
                            if (gameStatistic.getHunger() < 40 || gameStatistic.getThirst() < 40) {
                                emoji.setText(mood.dissatisfaction);
                            } else {
                                if (gameStatistic.getHealth() < 40) {
                                    emoji.setText((mood.pain));

                                } else {
                                    if (gameStatistic.getBoredom() < 40) {
                                        emoji.setText(mood.sleep);
                                    } else {
                                        if (gameStatistic.getHunger() < 75 && gameStatistic.getThirst() < 75 && gameStatistic.getHealth() < 75 && gameStatistic.getBoredom() < 75) {
                                            emoji.setText(mood.normal);
                                        } else {
                                            emoji.setText(mood.joy);
                                        }
                                    }

                                }
                            }
                        }


                        gameStatistic.setPlayTime(decrease);
                        if (isneed >= 60 * decrease) {
                            decrease++;
                        }
                        isneed++;


                        try {
                            Thread.sleep(1000);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }


                }


            }
        }).start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Установка вида
        {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);

        }

        setContentView(R.layout.maingame);
        treaddecreaser();

        ImageButton GWork = findViewById(R.id.GWork);
        GWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameStatistic.decreaseMoney(-1.0);

            }
        });


        ImageButton GSFood = findViewById(R.id.GSFood);
        GSFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Game.this, DFshop.class));
            }
        });


        ImageButton GSHealth = findViewById(R.id.GSHealth);
        GSHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Game.this, Hshop.class));
            }
        });


        ImageButton GPause = findViewById(R.id.GPause);
        GPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Game.this, Pause.class));
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();


        // Загрузка и начало работы цикла в треде
        loadpls = true;
        work = true;

    }


    @Override
    protected void onPause() {
        super.onPause();

        work = false; // отключение цикла в треде
        if (!yasdoh) {
            save("pasttime", gameStatistic.getPlayTime(), "", false);
            save("hunger", gameStatistic.getHunger(), "", false);
            save("thirst", gameStatistic.getThirst(), "", false);
            save("boredom", gameStatistic.getBoredom(), "", false);
            save("health", gameStatistic.getHealth(), "", false);
            save("money", 0, Double.toString(gameStatistic.getMoney()), true);
            save("decrease", decrease, "", false);
        }


    }


}

