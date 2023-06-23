package com.example.tamago;


import android.support.annotation.NonNull;

public class ShopItem {
    protected String name;
    protected int coast = 0;
    protected int thirst = 0;
    protected int hunger = 0;
    protected int health = 0;
    protected int boredom = 0;
    protected ShopItem(){};
    public ShopItem(String name, int coast, int thirst, int hunger, int health, int boredom){
        this.name = name;
        this.coast = coast;
        this.thirst = thirst;
        this.hunger = hunger;
        this.health = health;
        this.boredom = boredom;
    }
    public String getName() {
        return name;
    }

    public int getCoast() {
        return coast;
    }

    public int getThirst() {
        return thirst;
    }

    public int getHunger() {
        return hunger;
    }

    public int getHealth() {
        return health;
    }

    public int getBoredom() {
        return boredom;
    }

    public Boolean isEnoughMoney(GameStatistic gameStatistic) {
        return gameStatistic.getMoney() >= coast;
    }

    public void beSold(GameStatistic gameStatistic) {
        // класс изменяет статистику в соответствии соответствии со своими харрактеристиками
        gameStatistic.setMoney(gameStatistic.getMoney() - getCoast());
        gameStatistic.setThirst(gameStatistic.getThirst() + getThirst());
        gameStatistic.setHunger(gameStatistic.getHunger() + getHunger());
        gameStatistic.setHealth(gameStatistic.getHealth() + getHealth());
        gameStatistic.setBoredom(gameStatistic.getBoredom() + getBoredom());
    }

    @NonNull
    @Override
    public String toString() {
        return name + " | " + Integer.toString(coast);
    }
}
