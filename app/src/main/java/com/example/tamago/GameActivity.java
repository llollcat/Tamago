package com.example.tamago;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends Activity {
    GameStatistic gameStatistic;

    protected TextView money;
    protected ProgressBar GHunger;
    protected ProgressBar GThirst;
    protected ProgressBar GBoredom;
    protected ProgressBar GHealth;
    protected TextView time;
    protected TextView emoji;


    //Тред, который выполняет все основные операции

    Boolean stopThread = false;
    Thread mainThread = new Thread(new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {


            // элементы интерфейса с показателями персонажа


            while (true) {
                gameStatistic.modifyAllStatByStatModifier();
                if (gameStatistic.isDie()) {
                    gameStatistic.writeBestScore();
                    gameStatistic.wipeStatisticWithOutPlayTime();
                    gameStatistic.setPlayTime(0);

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(getApplicationContext(), R.string.End, Toast.LENGTH_LONG).show();
                            finish();
                        }
                    });
                    startActivity(new Intent(GameActivity.this, MainActivity.class));
                    break;
                }
                runOnUiThread(new Runnable() {
                    public void run() {
                        GBoredom.setProgress(gameStatistic.getBoredom());
                        GHunger.setProgress(gameStatistic.getHunger());
                        GThirst.setProgress(gameStatistic.getThirst());
                        GHealth.setProgress(gameStatistic.getHealth());
                        time.setText((Integer.toString(gameStatistic.getPlayTime())));
                        emoji.setText(Mood.getMood(gameStatistic));
                    }
                });


                if (gameStatistic.getTick() >= 60 * gameStatistic.getStatModifier())
                    gameStatistic.increaseStatDecrease(1);

                gameStatistic.increaseTickByOne();


                do {
                    try {
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (stopThread);


            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maingame);
        this.money = findViewById(R.id.GMoney);
        this.GHunger = findViewById(R.id.GHunger);
        this.GThirst = findViewById(R.id.GThirst);
        this.GHealth = findViewById(R.id.GHealth);
        this.GBoredom = findViewById(R.id.GBoredom);
        this.time = findViewById(R.id.GTime);
        this.emoji = findViewById(R.id.GEnt);


        gameStatistic = GameStatistic.getInstance();
        mainThread.start();

//        getActionBar().
        ImageButton GWork = findViewById(R.id.GWork);
        GWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameStatistic.decreaseMoney(-1);
                TextView money = findViewById(R.id.GMoney);
                money.setText(gameStatistic.getStringMoney());
            }
        });


        ImageButton GSFood = findViewById(R.id.GSFood);
        GSFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameActivity.this, FoodShopActivity.class));
            }
        });


        ImageButton GSHealth = findViewById(R.id.GSHealth);
        GSHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameActivity.this, GeneralShopActivity.class));
            }
        });


        ImageButton GPause = findViewById(R.id.GPause);
        GPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(GameActivity.this, PauseActivity.class));
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void onResume() {
        super.onResume();
        GBoredom.setProgress(gameStatistic.getBoredom());
        GHunger.setProgress(gameStatistic.getHunger());
        GThirst.setProgress(gameStatistic.getThirst());
        GHealth.setProgress(gameStatistic.getHealth());
        time.setText((Integer.toString(gameStatistic.getPlayTime())));
        emoji.setText(Mood.getMood(gameStatistic));
        money.setText(gameStatistic.getStringMoney());

        stopThread = false;

        if (!gameStatistic.isDie())
            gameStatistic.save();
    }


    @Override
    protected void onPause() {
        super.onPause();

        stopThread = true;

        if (!gameStatistic.isDie())
            gameStatistic.save();
    }
}

