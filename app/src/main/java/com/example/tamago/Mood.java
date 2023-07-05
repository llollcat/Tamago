package com.example.tamago;

public class Mood {

    private Mood() {// конструктор не требуется
    }

    private static final String normal = "(^_^)";  // 75

    private static final String joy = "(*´▽`*)"; // >75

    private static final String dissatisfaction = "(￣︿￣)"; // голод или жажда

    private static final String pain = "(~>_<~)";// здоровье

    private static final String sleep = "(￣o￣) zzZZzzZ";  // скука

    public static String getMood(GameStatistic gameStatistic) {

        if (gameStatistic.getHunger() < 40 || gameStatistic.getThirst() < 40)
            return dissatisfaction;

        if (gameStatistic.getHealth() < 40)
            return pain;

        if (gameStatistic.getBoredom() < 40)
            return sleep;

        if (gameStatistic.getHunger() < 75 && gameStatistic.getThirst() < 75 && gameStatistic.getHealth() < 75 && gameStatistic.getBoredom() < 75)
            return normal;

        return joy;
    }
}

