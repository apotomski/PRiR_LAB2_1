package test;

import java.util.Random;

public class Latarka extends Thread{

    static int ZMIANA_BATERI = 1;
    static int SWIECI = 2;
    static int KONIEC = 3;
    static int AWARIA = 4;
    static int NAPRAWA = 5;

    int liczba_zapasowych_bateri;
    int procent_bateri;
    float moc;
    int numer;
    int stan;
    int rodzaj_bateri;
    Bateria b;
    Random rand;

    public Latarka(int liczba_zapasowych_bateri,float moc, int numer, int rodzaj_bateri) {
        this.liczba_zapasowych_bateri = liczba_zapasowych_bateri;
        this.procent_bateri = 100;
        this.moc = moc;
        this.numer = numer;
        this.stan = 2;
        this.b = new Bateria(rodzaj_bateri);
        this.rodzaj_bateri = rodzaj_bateri;
        rand = new Random();
    }

    public void run() {
        while(this.stan != KONIEC) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch(this.stan) {
                case 1: {// zmiana bateri
                    if(this.liczba_zapasowych_bateri > 0) {
                        System.out.println("Dla latarki nr " + this.numer + " pozostalo " + this.liczba_zapasowych_bateri + " zapasowych bateri");
                        this.liczba_zapasowych_bateri--;
                        this.stan = this.b.zmien_baterie(this.numer);
                    } else {
                        System.out.println("Brak zapasowych bateri dla latarki nr " + this.numer);
                        this.stan = KONIEC;
                    }
                } break;
                case 2: {// swieci
                    this.procent_bateri -= 15 - this.b.dlugosc_dzialania() * 5;
                    System.out.println("Latarka nr " + this.numer + " z moca " + this.moc + " pozostalo " + this.procent_bateri + "% bateri");
                    try {
                        Thread.sleep(1000 * this.b.dlugosc_dzialania());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(rand.nextInt(10) == 4) {
                        this.stan = AWARIA;
                    } else if(this.procent_bateri <= 20) {
                        System.out.println("W latarce nr " + this.numer + " pozostalo mniej niz 10% bateri, wymieniam na zapasowa");
                        this.stan = ZMIANA_BATERI;
                    }
                } break;
                case 4: {// awaria
                    System.out.println("Latarka nr " + this.numer + " ulegla awari, rozpoczynam naprawe");
                    this.stan = NAPRAWA;
                } break;
                case 5: {// naprawa
                    if(rand.nextInt(3) == 1) {
                        System.out.println("Latarka nr " + this.numer + " zostala naprawiona ale jej moc spadla o 5%");
                        this.moc *= 0.95;
                        this.stan = SWIECI;
                    } else {
                        System.out.println("Latarka nr " + this.numer + " jest nienaprawialna");
                        this.stan = KONIEC;
                    }
                } break;
            }
        }
    }

}
