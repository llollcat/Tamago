package com.example.tamago;

public class GeneralShopActivity extends ShopActivity{
    @Override
    protected void init() {
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.HIT2),  0, -10, -5, 20, -5));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.HIT3),  20, 0, 0, 10, -10));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.HIT4), 60, -10, 0, 45, -10));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.HIT5), 150, 5, 0, 70, 0));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.HIT6), 170, 0, 0, 85, 0));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.HIT7), 180, -5, -5, 100, -5));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.HIT8), 500, GameStatistic.MAX_STAT, GameStatistic.MAX_STAT, GameStatistic.MAX_STAT, GameStatistic.MAX_STAT));

    }
}
