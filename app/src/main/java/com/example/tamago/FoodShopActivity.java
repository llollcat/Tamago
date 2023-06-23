package com.example.tamago;


public final class FoodShopActivity extends ShopActivity {
    @Override
    protected void init() {
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.FIT1), 20, 30,0,0,0));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.FIT2), 26, 3, 10, 0, 0));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.FIT3), 40, -5, 27, -2, 0));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.FIT4), 100, 0, 50, -2, 5));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.FIT5), 100, 5, 40, 5, 0));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.FIT6), 125, 2, 0, -2, 50));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.FIT7), 150, 5, 75, -5, 0));
        addNewItem(new ShopItem(MainActivity.getAppContext().getString(R.string.FIT8), 200, 25, 75, 0, 0));

    }
}
