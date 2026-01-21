package me.sfclog.entitymodels.utils;

public class RandomUtils {

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public static int getRandomNumber(double min, double max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
