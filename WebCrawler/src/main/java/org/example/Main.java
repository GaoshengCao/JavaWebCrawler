package org.example;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        System.out.println("Url pagina:");
        Scanner scanner = new Scanner(System.in);
        String url = scanner.nextLine();
        crawler.crawl("https://" + url);
    }
}