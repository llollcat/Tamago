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


public class Game extends Activity {
    Integer decrease = 0;
    Boolean work = false;
    Boolean loadpls = false;
    Boolean killtread = false;
    Stat stat = new Stat();
    Ent ent = new Ent();
    Boolean yasdoh = false;


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
                tosaveorload = getSharedPreferences("Stat", Context.MODE_PRIVATE);
                int isneed = 1;
                while (!killtread) {


                    if (loadpls) {
                        stat.pasttime = tosaveorload.getInt("pasttime", 0);
                        stat.hunger = tosaveorload.getInt("hunger", Stat.maxStat);
                        stat.thirst = tosaveorload.getInt("thirst", Stat.maxStat);
                        stat.boredom = tosaveorload.getInt("boredom", Stat.maxStat);
                        stat.health = tosaveorload.getInt("health", Stat.maxStat);
                        stat.money = Double.parseDouble(tosaveorload.getString("money", "750.0"));
                        decrease = tosaveorload.getInt("decrease", 1);
                        Log.i("?!?", "run: данные должны быть загруженны");
                        loadpls = false;
                    }

                    if (work) {


                        stat.setBoredom(decrease);
                        GBoredom.setProgress(stat.getBoredom());

                        stat.setHunger(decrease);
                        GHunger.setProgress(stat.getHunger());

                        stat.setThirst(decrease);
                        GThirst.setProgress(stat.getThirst());

                        stat.setHealth(decrease);
                        GHealth.setProgress(stat.getHealth());


                        time.setText((Integer.toString(stat.getPasttime())));

                        money.setText(Double.toString(stat.getMoney()));


                        // установщик emoji и умиратель

                        if (stat.hunger <= 0 || stat.thirst <= 0 || stat.boredom <= 0 || stat.health <= 0) {
                            tosaveorload.edit().clear().apply();

                            SharedPreferences best = getSharedPreferences("BestR", Context.MODE_PRIVATE);
                            yasdoh = true;
                            Log.i("?!?", "run: Умер мужык ");


                            killtread = true;
                            if (stat.pasttime > best.getInt("BestR", 0)) {

                                SharedPreferences.Editor editor = best.edit();
                                editor.putInt("BestR", stat.pasttime);
                                editor.apply();


                            }

                            runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(getApplicationContext(), R.string.End, Toast.LENGTH_LONG).show();
                                }
                            });


                            startActivity(new Intent(Game.this, MainActivity.class));


                        } else {
                            if (stat.hunger < 40 || stat.thirst < 40) {
                                emoji.setText(ent.discontent);
                            } else {
                                if (stat.health < 40) {
                                    emoji.setText((ent.pain));

                                } else {
                                    if (stat.boredom < 40) {
                                        emoji.setText(ent.sleep);
                                    } else {
                                        if (stat.hunger < 75 && stat.thirst < 75 && stat.health < 75 && stat.boredom < 75) {
                                            emoji.setText(ent.normal);
                                        } else {
                                            emoji.setText(ent.joy);
                                        }
                                    }

                                }
                            }
                        }


                        stat.setPasttime(decrease);
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
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        }

        Log.i("?!?", "onCreate: ");
        setContentView(R.layout.maingame);
        treaddecreaser();

        ImageButton GWork = findViewById(R.id.GWork);
        GWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stat.setMoney(-1.0, Game.this);

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


        Log.i("?!?", "resume: ");
        // Загрузка и начало работы цикла в треде
        loadpls = true;
        work = true;

    }


    @Override
    protected void onPause() {
        super.onPause();

        work = false; // отключение цикла в треде
        if (!yasdoh) {
            save("pasttime", stat.pasttime, "", false);
            save("hunger", stat.hunger, "", false);
            save("thirst", stat.thirst, "", false);
            save("boredom", stat.boredom, "", false);
            save("health", stat.health, "", false);
            save("money", 0, Double.toString(stat.getMoney()), true);
            save("decrease", decrease, "", false);
            Log.i("?!?", "pause: Данные должны быть сохранены ");
        }


    }


}

