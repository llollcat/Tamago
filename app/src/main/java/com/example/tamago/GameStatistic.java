package com.example.tamago;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Objects;

public class GameStatistic {
    public final static Integer MAX_STAT = 100;
    public final static String SHARED_PREFERENCE_STATISTIC = "Statistic";

    public final static String SHARED_PREFERENCE_BEST_SCORE = "BestScore";
    private Integer statModifier;
    private Integer playTime = 0;
    private Integer hunger = MAX_STAT;
    private Integer thirst = MAX_STAT;
    private Integer health = MAX_STAT;
    private Integer boredom = MAX_STAT;
    private Double money;

    private Boolean isWasLoaded = false;

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

    public static synchronized GameStatistic getInstance(Context context) {
        if (instance == null) {
            instance = new GameStatistic();
            instance.sharedStatistic = context.getSharedPreferences(SHARED_PREFERENCE_STATISTIC, Context.MODE_PRIVATE);
            instance.sharedBestScore = context.getSharedPreferences(SHARED_PREFERENCE_BEST_SCORE, Context.MODE_PRIVATE);
            instance.load();
        }
        return instance;
    }

    public static synchronized GameStatistic getInstance() throws SharedPreferencesNotSetException {
        if (instance == null) {
            throw new SharedPreferencesNotSetException();
        }
        return instance;
    }


    public void forcedLoad() {
        this.setPlayTime(this.sharedStatistic.getInt("playTime", 0));
        this.setHunger(sharedStatistic.getInt("hunger", GameStatistic.MAX_STAT));
        this.setThirst(sharedStatistic.getInt("thirst", GameStatistic.MAX_STAT));
        this.setBoredom(sharedStatistic.getInt("boredom", GameStatistic.MAX_STAT));
        this.setHealth(sharedStatistic.getInt("health", GameStatistic.MAX_STAT));
        this.setMoney(Double.parseDouble(Objects.requireNonNull(sharedStatistic.getString("money", "1000.00"))));
        this.setStatModifier(sharedStatistic.getInt("statModifier", 1));
        isWasLoaded = true;
    }

    public void load() {
        if (!isWasLoaded) {
            forcedLoad();
        }
    }

    void save() {
        SharedPreferences.Editor statisticEditor = this.sharedStatistic.edit();
        statisticEditor.putInt("playTime", this.getPlayTime());
        statisticEditor.putInt("hunger", this.getHunger());
        statisticEditor.putInt("thirst", this.getThirst());
        statisticEditor.putInt("boredom", this.getBoredom());
        statisticEditor.putInt("health", this.getHealth());
        statisticEditor.putString("money", Double.toString(this.getMoney()));
        statisticEditor.apply();
    }


    public synchronized int getHunger() {
        return hunger;
    }

    public synchronized void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public synchronized void decreaseHunger(int decreaseBy) {
        this.hunger -= decreaseBy;
        if (this.hunger > MAX_STAT) {
            this.hunger = MAX_STAT;
        }
    }


    public synchronized int getThirst() {
        return thirst;
    }

    public synchronized void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public synchronized void decreaseThirst(int decreaseBy) {
        this.thirst -= decreaseBy;
        if (this.thirst > MAX_STAT) {
            this.thirst = MAX_STAT;
        }
    }


    public synchronized int getBoredom() {
        return boredom;
    }

    public synchronized void setBoredom(int boredom) {
        this.boredom = boredom;
    }

    public synchronized void decreaseBoredom(int decreaseBy) {
        this.boredom -= decreaseBy;
        if (this.boredom > MAX_STAT) {
            this.boredom = MAX_STAT;
        }
    }


    public synchronized int getHealth() {
        return health;
    }

    public synchronized void setHealth(int health) {
        this.health = health;
    }

    public synchronized void decreaseHealth(int decreaseBy) {
        this.health -= decreaseBy;
        if (this.health > MAX_STAT) {
            this.health = MAX_STAT;
        }
    }


    public synchronized double getMoney() {
        return money;
    }

    public synchronized void setMoney(double money) {
        if (money >= 0 && money < 9999999.00) {
            this.money = money;

        }
    }

    public synchronized Boolean decreaseMoney(double decreaseBy) {
        if ((this.money - decreaseBy) < 0)
            return false;

        this.money -= decreaseBy;
        if (this.money > 9999999.00) {
            this.money = 9999999.00;
        }
        return true;

    }


    public synchronized int getPlayTime() {
        return playTime;
    }

    public synchronized void setPlayTime(int playTime) {
        this.playTime = playTime;
    }

    public synchronized void increasePlayTime(int increaseBY) {

        this.playTime += increaseBY;
    }


    public synchronized Integer getStatModifier() {
        return statModifier;
    }

    public synchronized void setStatModifier(Integer statModifier) {
        this.statModifier = statModifier;
    }

    public synchronized void increaseStatDecrease(int increaseBY) {
        this.statModifier += increaseBY;
    }

    public synchronized void modifyAllStatByStatModifier() {
        decreaseBoredom(statModifier);
        decreaseHunger(statModifier);
        decreaseThirst(statModifier);
        decreaseHealth(statModifier);
        increasePlayTime(statModifier);
    }

    public synchronized Boolean isDie() {
        return (getHunger() <= 0 || getThirst() <= 0 || getBoredom() <= 0 || getHealth() <= 0);
    }

    public synchronized void wipeStatistic() {
        sharedStatistic.edit().clear().apply();
        forcedLoad(); // загружает пустые значения
    }

    public synchronized void writeBestScore() {
        if (getPlayTime() > sharedBestScore.getInt(SHARED_PREFERENCE_BEST_SCORE, 0)) {
            SharedPreferences.Editor editor = sharedBestScore.edit();
            editor.putInt(SHARED_PREFERENCE_BEST_SCORE, getPlayTime());
            editor.apply();
        }
    }

    public synchronized int getWrittenScore() {
        return sharedBestScore.getInt(SHARED_PREFERENCE_BEST_SCORE, 0);


    }


}
