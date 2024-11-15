package org.example;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scraper scrap = new Scraper();
//        System.out.println("Url pagina:");
//        Scanner scanner = new Scanner(System.in);
//        String url = scanner.nextLine();
        String url = "imdb.com/chart/top/";
        scrap.setlist("https://" + url);
    }
}