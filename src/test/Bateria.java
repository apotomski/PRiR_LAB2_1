package test;

import java.util.Random;

public class Bateria {
    static int ENERGIOOSZCZEDNA = 1;
    static int STANDARDOWA = 2;
    static int MALO_WYDAJNA = 3;

    int rodzaj;
    int dlugosc_dzialania;
    boolean czy_wadliwa;
    public Bateria(int rodzaj) {
        this.rodzaj = rodzaj;
        if(this.rodzaj == ENERGIOOSZCZEDNA)
            this.dlugosc_dzialania = 3;
        else if(this.rodzaj == STANDARDOWA)
            this.dlugosc_dzialania = 2;
        else if(this.rodzaj == MALO_WYDAJNA)
            this.dlugosc_dzialania = 1;
        else
            this.dlugosc_dzialania = 0;
        Random rand = new Random();
        if(rand.nextInt(20) == 10)
            this.czy_wadliwa = true;
        else
            this.czy_wadliwa = false;
    }

    public int zmien_baterie(int numer) {
        if(this.czy_wadliwa) {
            System.out.println("Latarka nr " + numer + " trafila na wadliwa baterie trzeba wymienic na nowa");
            return 1;
        } else {
            System.out.println("Zmiana bateri w latarce nr " + numer);
            return 2;
        }
    }

    public int dlugosc_dzialania() {
        return this.dlugosc_dzialania;
    }
}
