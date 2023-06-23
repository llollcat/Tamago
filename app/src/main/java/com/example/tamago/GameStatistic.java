package com.example.tamago;

import android.content.Context;
import android.content.SharedPreferences;
//todo новый рефакторинг отказаться он decreese и ncreese некоторых
public class GameStatistic {
    public final static Integer MAX_STAT = 100;
    public final static String SHARED_PREFERENCE_STATISTIC = "Statistic";
    public final static String SHARED_PREFERENCE_BEST_SCORE = "BestScore";
    private Integer statModifier = 1;
    private Integer playTime = 0;
    private Integer hunger = MAX_STAT;
    private Integer thirst = MAX_STAT;
    private Integer health = MAX_STAT;
    private Integer boredom = MAX_STAT;
    private Integer money;


    public int getTick() {
        return tick;
    }
    public void increaseTickByOne() {
        ++tick;
    }
    public void setTick(int tick) {
        this.tick = tick;
    }

    private int tick = 1;
    private GameStatistic() {
    }

    private SharedPreferences sharedStatistic;
    private SharedPreferences sharedBestScore;

    private static GameStatistic instance = null;

    public static synchronized GameStatistic getInstance() {
        if (instance == null) {
            instance = new GameStatistic();
            instance.sharedStatistic = MainActivity.getAppContext().getSharedPreferences(SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE);
            instance.sharedBestScore = MainActivity.getAppContext().getSharedPreferences(SHARED_PREFERENCE_BEST_SCORE, Context.MODE_PRIVATE);
            instance.load();
        }
        return instance;
    }

    private Boolean isWasLoaded = false;
    public void forcedLoad() {
        this.setPlayTime(this.sharedStatistic.getInt("playTime", 0));
        this.setHunger(sharedStatistic.getInt("hunger", GameStatistic.MAX_STAT));
        this.setThirst(sharedStatistic.getInt("thirst", GameStatistic.MAX_STAT));
        this.setBoredom(sharedStatistic.getInt("boredom", GameStatistic.MAX_STAT));
        this.setHealth(sharedStatistic.getInt("health", GameStatistic.MAX_STAT));
        this.setMoney(sharedStatistic.getInt("money", 1000));
        this.setStatModifier(sharedStatistic.getInt("statModifier", 1));
        isWasLoaded = true;
    }

    public void load() {
        if (!isWasLoaded)
            forcedLoad();
    }

    void save() {
        SharedPreferences.Editor statisticEditor = this.sharedStatistic.edit();
        statisticEditor.putInt("playTime", this.getPlayTime());
        statisticEditor.putInt("hunger", this.getHunger());
        statisticEditor.putInt("thirst", this.getThirst());
        statisticEditor.putInt("boredom", this.getBoredom());
        statisticEditor.putInt("health", this.getHealth());
        statisticEditor.putInt("money", this.getMoney());
        statisticEditor.apply();
    }


    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void decreaseHunger(int decreaseBy) {
        this.hunger -= decreaseBy;
        if (this.hunger > MAX_STAT)
            this.hunger = MAX_STAT;

    }


    public int getThirst() {
        return thirst;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public void decreaseThirst(int decreaseBy) {
        this.thirst -= decreaseBy;
        if (this.thirst > MAX_STAT) {
            this.thirst = MAX_STAT;
        }
    }


    public int getBoredom() {
        return boredom;
    }

    public void setBoredom(int boredom) {
        this.boredom = boredom;
    }

    public void decreaseBoredom(int decreaseBy) {
        this.boredom -= decreaseBy;
        if (this.boredom > MAX_STAT) {
            this.boredom = MAX_STAT;
        }
    }


    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void decreaseHealth(int decreaseBy) {
        this.health -= decreaseBy;
        if (this.health > MAX_STAT) {
            this.health = MAX_STAT;
        }
    }


    public int getMoney() {
        return money;
    }

    public Boolean decreaseMoney(int decreaseBy) {
        if ((this.money - decreaseBy) < 0)
            return false;

        this.money -= decreaseBy;
        if (this.money > 9999999) {
            this.money = 9999999;
        }
        return true;

    }
    public void setMoney(int money) {
        if (money >= 0 && money < 9999999)
            this.money = money;


    }




    public int getPlayTime() {
        return playTime;
    }

    public void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public void increasePlayTime(int increaseBY) {

        this.playTime += increaseBY;
    }


    public Integer getStatModifier() {
        return statModifier;
    }

    public void setStatModifier(Integer statModifier) {
        this.statModifier = statModifier;
    }

    public void increaseStatDecrease(int increaseBY) {
        this.statModifier += increaseBY;
    }

    public void modifyAllStatByStatModifier() {
        decreaseBoredom(statModifier);
        decreaseHunger(statModifier);
        decreaseThirst(statModifier);
        decreaseHealth(statModifier);
        increasePlayTime(statModifier);
    }

    public Boolean isDie() {
        return (getHunger() <= 0 || getThirst() <= 0 || getBoredom() <= 0 || getHealth() <= 0);
    }

    public void wipeStatisticWithOutPlayTime() {
        sharedStatistic.edit().clear().apply();
        int temp = playTime;
        forcedLoad(); // загружает пустые значения
        playTime = temp;
    }

    public void writeBestScore() {
        if (getPlayTime() > sharedBestScore.getInt(SHARED_PREFERENCE_BEST_SCORE, 0)) {
            SharedPreferences.Editor editor = sharedBestScore.edit();
            editor.putInt(SHARED_PREFERENCE_BEST_SCORE, getPlayTime());
            editor.apply();
        }
    }

    public int getWrittenScore() {
        return sharedBestScore.getInt(SHARED_PREFERENCE_BEST_SCORE, 0);


    }
    public String getStringMoney(){
        return Integer.toString(money);
    }

}
