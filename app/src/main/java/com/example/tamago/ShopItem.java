package com.example.tamago;


import android.support.annotation.NonNull;

public class ShopItem {
    protected ShopItem() {
    }

    public ShopItem(String name, int coast, int thirst, int hunger, int health, int boredom) {
        this.name = name;
        this.coast = coast;
        this.thirst = thirst;
        this.hunger = hunger;
        this.health = health;
        this.boredom = boredom;
    }


    protected String name;

    public String getName() {
        return name;
    }


    protected int coast = 0;

    public int getCoast() {
        return coast;
    }


    protected int thirst = 0;

    public int getThirst() {
        return thirst;
    }


    protected int hunger = 0;

    public int getHunger() {
        return hunger;
    }


    protected int health = 0;

    public int getHealth() {
        return health;
    }


    protected int boredom = 0;

    public int getBoredom() {
        return boredom;
    }


    public Boolean isEnoughMoney(GameStatistic gameStatistic) {
        return gameStatistic.getMoney() >= coast;
    }

    // класс изменяет статистику в соответствии соответствии со своими харрактеристиками
    public void beSold(GameStatistic gameStatistic) {
        gameStatistic.setMoney(gameStatistic.getMoney() - getCoast());
        gameStatistic.setThirst(gameStatistic.getThirst() + getThirst());
        gameStatistic.setHunger(gameStatistic.getHunger() + getHunger());
        gameStatistic.setHealth(gameStatistic.getHealth() + getHealth());
        gameStatistic.setBoredom(gameStatistic.getBoredom() + getBoredom());
    }


    @NonNull
    @Override
    public String toString() {
        return name + " | " + coast;
    }
}
