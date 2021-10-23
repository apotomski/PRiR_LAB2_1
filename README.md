# PRiR_LAB2_1
Projekt 1 na laboratoria nr 2. Symulator latarki na podstawie symulatora lotniska. Każda latarka ma określoną moc świecenia oraz % bateri a także ilości zapasowych bateri.
Baterie mogą być różnego rodzaju: energoszczędna, standardowa i mało wydajna. Długość świecenia a także tempo rozładowywania się bateri zależy od jej rodzaju.
Dodatkowo każda bateri ma 5% szans na to, że będzie wadliwa i nie będzie zdatna do użytku. Każda latarka podczas świecenia ma 10% szans na awarię co wymus naprawę na tej latarce.
Na udaną naprawę mamy 33% ale jeśli uda nam ją się naprawić jej moc świecenia spada o 5%; Jeśli nie uda nam się jej naprawić zostaje ona wyrzucona. Baterie w latarce będą
wymieniane dopuki posiadamy zapasowe baterie. Jeśli nie mamy zapasowych bateri latarka już nie zaświeci.

Opis kodu

Main:
stworzenie x latarek i powołanie ich uruchomienie ich.

  public class Main {
      static int ilosc_latarek = 20;
      static Random random = new Random();

      public static void main(String[] args) {
          for(int i = 1; i <= ilosc_latarek; i++) {
              new Latarka(random.nextInt(5), random.nextInt(200), i, random.nextInt(3) + 1).start();
          }
      }
  }
  
Latarka:
statusy operacji na latarkach oraz zmienne latarki

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
 
Konstruktor
 
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
      
 Metoda run dokonująca odpowiednich operacji w zależności od statusu sprawdzanego w switchu,
 pod 1 sprawdza czy posiada zapasowe baterie jeśli tak to zmienia pod 2 swieci co powoduje uśpienie wątku na określony czas i zużywa baterię pod 4 jest obsługa awari
 zaś pod 5 próba naprawy
  
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
        
  Bateria
  
  zmienne potrzebne do działania bateri i rodzaje bateri
  
    static int ENERGIOOSZCZEDNA = 1;
    static int STANDARDOWA = 2;
    static int MALO_WYDAJNA = 3;

    int rodzaj;
    int dlugosc_dzialania;
    boolean czy_wadliwa;
    
 Konstruktor ustawia wartości i losuje cy bateria jest wadliwa
 
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
        
  Metoda zmiany bateri
  
      public int zmien_baterie(int numer) {
            if(this.czy_wadliwa) {
                System.out.println("Latarka nr " + numer + " trafila na wadliwa baterie trzeba wymienic na nowa");
                return 1;
            } else {
                System.out.println("Zmiana bateri w latarce nr " + numer);
                return 2;
            }
        }
