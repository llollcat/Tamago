package com.example.tamago;

import android.content.Context;
import android.content.SharedPreferences;


public class GameStatistic {
    public final static Integer MAX_STAT = 100;
    public final static String SHARED_PREFERENCE_STATISTIC = "Statistic";
    public final static String SHARED_PREFERENCE_BEST_SCORE = "BestScore";


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

    protected void load() {
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


    private Integer hunger = MAX_STAT;

    public Integer getHunger() {
        return hunger;
    }

    public void setHunger(Integer hunger) {
        this.hunger = hunger;
    }


    private Integer thirst = MAX_STAT;

    public Integer getThirst() {
        return thirst;
    }

    public void setThirst(Integer thirst) {
        this.thirst = thirst;
    }


    private Integer boredom = MAX_STAT;

    public Integer getBoredom() {
        return boredom;
    }

    public void setBoredom(Integer boredom) {
        this.boredom = boredom;
    }


    private Integer health = MAX_STAT;

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }


    private Integer money;

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        if (money >= 0 && money < 9999999)
            this.money = money;
    }


    private Integer playTime = 0;

    public Integer getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Integer playTime) {
        this.playTime = playTime;
    }

    private Integer statModifier = 1;

    public Integer getStatModifier() {
        return statModifier;
    }

    public void setStatModifier(Integer statModifier) {
        this.statModifier = statModifier;
    }

    public void modifyAllStatByStatModifier() {
        setBoredom(getBoredom() - statModifier);
        setHunger(getHunger() - statModifier);
        setThirst(getThirst() - statModifier);
        setHealth(getHealth() - statModifier);
        setPlayTime(getPlayTime() + statModifier);
    }


    public void wipeAllStatistic() {
        sharedStatistic.edit().clear().apply();
        // загружает пустые значения
        forcedLoad();
    }


    public void writeBestScore() {
        if (getPlayTime() > sharedBestScore.getInt(SHARED_PREFERENCE_BEST_SCORE, 0)) {
            SharedPreferences.Editor editor = sharedBestScore.edit();
            editor.putInt(SHARED_PREFERENCE_BEST_SCORE, getPlayTime());
            editor.apply();
        }
    }


    public Integer getWrittenScore() {
        return sharedBestScore.getInt(SHARED_PREFERENCE_BEST_SCORE, 0);
    }


    public Boolean isDie() {
        return (getHunger() <= 0 || getThirst() <= 0 || getBoredom() <= 0 || getHealth() <= 0);
    }
}
