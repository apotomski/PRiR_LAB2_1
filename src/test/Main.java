package test;

import java.util.Random;

public class Main {
    static int ilosc_latarek = 20;
    static Random random = new Random();

    public static void main(String[] args) {
        for(int i = 1; i <= ilosc_latarek; i++) {
            new Latarka(random.nextInt(5), random.nextInt(200), i, random.nextInt(3) + 1).start();
        }
    }
}
