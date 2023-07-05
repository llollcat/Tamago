package com.example.tamago;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;


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
    private Boolean stopThread = false;
    Thread mainThread = new Thread(new Runnable() {
        @Override
        public void run() {
            // элементы интерфейса с показателями персонажа
            while (!gameStatistic.isDie()) {
                gameStatistic.modifyAllStatByStatModifier();

                runOnUiThread(() -> {
                    GBoredom.setProgress(gameStatistic.getBoredom());
                    GHunger.setProgress(gameStatistic.getHunger());
                    GThirst.setProgress(gameStatistic.getThirst());
                    GHealth.setProgress(gameStatistic.getHealth());

                    time.setText(String.format(Locale.getDefault(), "%d", gameStatistic.getPlayTime()));
                    emoji.setText(Mood.getMood(gameStatistic));
                });


                if (gameStatistic.getPlayTime() >= 60 * gameStatistic.getStatModifier())
                    gameStatistic.setStatModifier(gameStatistic.getStatModifier()+1);


                try {
                    do {
                        Thread.sleep(1000);
                    } while (stopThread);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    finish();
                    System.exit(0);
                }

            }
            // Когда персонаж умер
            gameStatistic.writeBestScore();
            gameStatistic.wipeAllStatistic();
            runOnUiThread(() -> {
                Toast.makeText(getApplicationContext(), R.string.End, Toast.LENGTH_LONG).show();
                finish();
            });
            startActivity(new Intent(GameActivity.this, MainActivity.class));

        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_game);
        this.money = findViewById(R.id.money_textView);
        this.GHunger = findViewById(R.id.hunger_progressBar);
        this.GThirst = findViewById(R.id.thirst_progressBar);
        this.GHealth = findViewById(R.id.health_progressBar);
        this.GBoredom = findViewById(R.id.boredom_progressBar);
        this.time = findViewById(R.id.time_textView);
        this.emoji = findViewById(R.id.tamago_condition_textView);


        gameStatistic = GameStatistic.getInstance();
        mainThread.start();


        ImageButton GWork = findViewById(R.id.work_ImageButton);
        GWork.setOnClickListener(view -> {
            gameStatistic.setMoney(gameStatistic.getMoney()+1);
            money.setText(String.format(Locale.getDefault(), "%d", gameStatistic.getMoney()));
        });


        ImageButton GSFood = findViewById(R.id.sho_food_imageButton);
        GSFood.setOnClickListener(view -> startActivity(new Intent(GameActivity.this, ShopFoodActivity.class)));


        ImageButton GSHealth = findViewById(R.id.shop_general_imageButton);
        GSHealth.setOnClickListener(view -> startActivity(new Intent(GameActivity.this, ShopGeneralActivity.class)));


        ImageButton GPause = findViewById(R.id.pause_imageButton);
        GPause.setOnClickListener(view -> startActivity(new Intent(GameActivity.this, PauseActivity.class)));
    }


    @Override
    protected void onResume() {
        super.onResume();
        GBoredom.setProgress(gameStatistic.getBoredom());
        GHunger.setProgress(gameStatistic.getHunger());
        GThirst.setProgress(gameStatistic.getThirst());
        GHealth.setProgress(gameStatistic.getHealth());
        time.setText(String.format(Locale.getDefault(), "%d", gameStatistic.getPlayTime()));
        emoji.setText(Mood.getMood(gameStatistic));
        money.setText(String.format(Locale.getDefault(), "%d", gameStatistic.getMoney()));

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

