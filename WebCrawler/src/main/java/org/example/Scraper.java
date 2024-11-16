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
            prendiAnnoRilascio(doc);
            prendiAnnoRilascio(doc);
            prendiDurata(doc);
        } catch (IOException e) {
            System.out.println("Errore in " + startingUrl);
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
            risposta.append(" ");
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
    private void prendiAnnoRilascio(Document document) {
        Elements links = document.select("span.cli-title-metadata-item");
        Queue<Integer> anno = new LinkedList<>();
        int n = 0;
        for (Element link : links) {
            if( n == 0){
                int annoUscita = Integer.parseInt(link.text());
                anno.add(annoUscita);
                n = 2;
            }else
            {
                n--;
            }
        }
        for (int i = 0; i < films.size(); i++) {
            Film film = films.get(i);
            film.setAnno_Rilascio(anno.poll());
            films.set(i, film);
        }
    }

    //TODO
    private void prendiDurata(Document document) {
        Elements links = document.select("span.cli-title-metadata-item");
        Queue<Integer> durata = new LinkedList<>();
        int n = 1;
        for (Element link : links) {
            if( n == 0){
                String durata_testo = link.text();

                String[] parti = durata_testo.split(" ");
                String ora = parti[0];
                String minuti = parti[1];
                String cleanedString = ora.replaceAll("[^0-9]", "");
                String cleanedString1 = minuti.replaceAll("[^0-9]", "");
                int h = Integer.parseInt(cleanedString);
                int min = Integer.parseInt(cleanedString1);

                durata.add(h*60 + min);
                n = 2;
            }else
            {
                n--;
            }
        }
        for (int i = 0; i < films.size(); i++) {
            Film film = films.get(i);
            film.setDurata_Minuti(durata.poll());
            films.set(i, film);
        }
    }
    //TODO
    private void prendiRegista() {
        for (int i = 0; i < films.size(); i++){
            Film film = films.get(i);
            try{
                Document doc = Jsoup.connect(film.URL).get();
                Elements name = doc.select("a.ipc-metadata-list-item__list-content-item ipc-metadata-list-item__list-content-item--link");
                for (Element link : name) {
                    
                }
            }catch (Exception e){
                System.out.println("Errore in " + film.URL);
            }


        }
//        Elements links = document.select("span.sc-5bc66c50-6 OOdsw cli-title-metadata-item");
//        Queue<Integer> anno = new LinkedList<>();
//        for (Element link : links) {
//            int annoUscita = Integer.parseInt(link.text());
//            anno.add(annoUscita);
//        }
//        for (int i = 0; i < films.size(); i++) {
//            Film film = films.get(i);
//            film.setAnno_Rilascio(anno.poll());
//            films.set(i, film);
//        }
    }

}