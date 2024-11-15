package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Scraper {
    private Set<String> visited = new HashSet<>();
    private Queue<String> toCrawl = new LinkedList<>();
    public List<Film> films = new ArrayList<>();

    public void setlist(String startingUrl){
        try {
            Document doc = Jsoup.connect(startingUrl).get();
            prendiLinks(doc);
        } catch (IOException e) {
            System.out.println("Errore in " + startingUrl);
        }

    }

    public void insertList(){
        while(!toCrawl.isEmpty()){
            String url = toCrawl.poll();
            try {
                Document doc = Jsoup.connect(url).get();
                prendiDati(doc);
            } catch (IOException e) {
                System.out.println("Errore in " + url);
            }
        }
    }

    private void prendiLinks(Document document) {
        Elements links = document.select("a.ipc-title-link-wrapper");
        for (Element link : links) {
            // Ottengo Url Assoluto
            String linkUrl = link.absUrl("href");
            String titolo = estraiTitolo(link.select("h3.ipc-title__text").text());
            int posizione = estraiPosizione(link.select("h3.ipc-title__text").text());

            films.add(new Film(linkUrl,titolo,posizione));

            if (!visited.contains(linkUrl)) {
                toCrawl.add(linkUrl);
            }
        }
    }

    private String estraiTitolo(String titolo){
        String[] parti = titolo.split(" ");
        StringBuilder risposta = new StringBuilder();
        for (int i = 1; i < parti.length; i++) {
            risposta.append(parti[i]);
        }
        return risposta.toString();
    }
    private int estraiPosizione(String titolo){
        String[] parti = titolo.split(" ");
        String numero = parti[0];
        String cleanedString = numero.replaceAll("[^0-9]", "");
        int number = Integer.parseInt(cleanedString);
        return number;
    }
    private void prendiDati(Document document) {

    }

}
