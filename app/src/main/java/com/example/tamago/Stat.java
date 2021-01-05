package com.example.tamago;

import android.content.Context;
import android.widget.Toast;

public class Stat {

    public final static int maxStat = 100;
    public Integer pasttime = 0;
    public Integer hunger = maxStat;
    public Integer thirst = maxStat;
    public Integer health = maxStat;
    public Integer boredom = maxStat;
    public Double money;

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

    public synchronized boolean setMoney(double money, Context context) {
        double check = this.money;
        if ((check - money) >= 0) {
            this.money -= money;
        } else {
            Toast.makeText(context, R.string.SMoneyError, Toast.LENGTH_LONG).show();
            return (false);
        }


        if (this.money > 9999999.00) {
            this.money = 9999999.00;
        }
        return (true);

    }


    public int getPasttime() {
        return pasttime;
    }

    public synchronized void setPasttime(int pasttime) {

        this.pasttime += pasttime;
    }


}
