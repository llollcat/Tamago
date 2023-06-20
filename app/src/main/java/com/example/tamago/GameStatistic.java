package com.example.tamago;

public class GameStatistic {

    public final static int maxStat = 100;
    private Integer playTime = 0;
    private Integer hunger = maxStat;
    private Integer thirst = maxStat;
    private Integer health = maxStat;
    private Integer boredom = maxStat;
    private Double money;

    public int getHunger() {
        return hunger;
    }

    public synchronized void setHunger(int hunger) {
        this.hunger -= hunger;
        if (this.hunger > maxStat) {
            this.hunger = maxStat;
        }
    }


    public int getThirst() {
        return thirst;
    }

    public synchronized void setThirst(int thirst) {
        this.thirst -= thirst;
        if (this.thirst > maxStat) {
            this.thirst = maxStat;
        }
    }


    public int getBoredom() {
        return boredom;
    }

    public synchronized void setBoredom(int boredom) {
        this.boredom -= boredom;
        if (this.boredom > maxStat) {
            this.boredom = maxStat;
        }
    }


    public int getHealth() {
        return health;
    }

    public synchronized void setHealth(int health) {
        this.health -= health;
        if (this.health > maxStat) {
            this.health = maxStat;
        }
    }


    public double getMoney() {
        return money;
    }

    public synchronized boolean decreaseMoney(double money) {
        if ((this.money - money) >= 0) {
            this.money -= money;
            return false;
        }
        if (this.money > 9999999.00) {
            this.money = 9999999.00;
        }
        return true;

    }


    public int getPlayTime() {
        return playTime;
    }

    public synchronized void setPlayTime(int playTime) {

        this.playTime += playTime;
    }


}
