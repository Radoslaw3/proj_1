package controller;

import model.Dron;
import model.Komputer;
import model.Sklep;
import pliki.OperacjePlikowe;
import pliki.PlikiBinarne;
import pliki.PlikiJson;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

public class Menu {

    public void wyswietlMenu() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj sposob zapisu plikow");
        System.out.println("1 - JSON");
        System.out.println("2 - binarnie");

        String wyborPliku = scanner.nextLine();
        OperacjePlikowe pliki;
        switch (wyborPliku) {
            case "1":
                pliki = new PlikiJson();
                break;
            case "2":
                pliki = new PlikiBinarne();
                break;
            default:
                pliki = new PlikiJson();
                break;
        }

        Sklep sklep;
        try {
            sklep = pliki.wczytaj();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Blad odczytu, startujemy z pustym sklepem");
            sklep = new Sklep("www.amazon.com");
        }


        System.out.println("1 - dodaj komputer");
        System.out.println("2 - usuń");
        System.out.println("3 - wyswietl liste produktow");
        System.out.println("4 - dodaj dron");
        System.out.println("5 - wyswietl posortowane cenowo");
        System.out.println("6 - wyswietl posortowane po nazwie");
        System.out.println("q - wyjdz");
        String wybor;
        do {
            System.out.print("Podaj wybor: ");
            wybor = scanner.nextLine();

            switch (wybor) {
                case "1": {
                    // wczytywanie informacji o komputerze
                    System.out.print("Podaj nazwe: ");
                    String nazwa = scanner.nextLine();
                    System.out.print("Podaj cene: ");
                    BigDecimal cena = scanner.nextBigDecimal();
                    scanner.nextLine();
                    System.out.print("Podaj marke: ");
                    String marka = scanner.nextLine();
                    System.out.print("Podaj ilosc GB ramu: ");
                    int ram = scanner.nextInt();
                    scanner.nextLine(); // 3\n
                    // stworzyc obiekt typu Komputer
                    Komputer komputer = new Komputer(nazwa, cena, marka, ram);

                    // wywolac metode dodaj klasy Sklep
                    sklep.dodaj(komputer);

                    break;
                }
                case "2":
                    // wczytaj id do usuniecia
                    System.out.print("Podaj id do usuniecia: ");
                    long id = scanner.nextLong();
                    scanner.nextLine();

                    // wywolac metode usun klasy Sklep
                    sklep.usun(id);
                    break;
                case "3":
                    // wywolac metode wyswietl klasy Sklep
                    sklep.wyswietl();
                    break;
                case "4": {
                    System.out.print("Podaj nazwe: ");
                    String nazwa = scanner.nextLine();
                    System.out.print("Podaj cene: ");
                    BigDecimal cena = scanner.nextBigDecimal();
                    scanner.nextLine();
                    System.out.print("Podaj zasieg: ");
                    double zasieg = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Ma kamere?: ");
                    boolean czyMaKamere = scanner.nextBoolean();
                    scanner.nextLine();
                    Dron dron = new Dron(nazwa, cena, zasieg, czyMaKamere);
                    sklep.dodaj(dron);
                    break;
                }
                case "5":
                    sklep.wyswietlPosortowaneCenowo();
                    break;
                case "6":
                    sklep.wyswietlPosortowanePoNazwie();
                    break;
                case "q":
                case "Q":
                    System.out.println("koniec");
                    // zapis do pliku
                    try {
                        pliki.zapisz(sklep);
                    } catch (Exception e) {
                        System.out.println("Blad zapisu do pliku");
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Bledna opcja, sprobuj jeszcze raz.");
                    break;
            }
        } while (!wybor.equalsIgnoreCase("q"));
    }
}