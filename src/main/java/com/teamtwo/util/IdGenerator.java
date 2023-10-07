package com.teamtwo.util;

import java.util.Random;

public class IdGenerator {
    private static final Random rng = new Random();

    public static int generateId() {
        return rng.nextInt(9000) + 1000;
    }
}
