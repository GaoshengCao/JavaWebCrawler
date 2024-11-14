package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class Crawler {

    // Set in cui sono registrati gli URL visitati
    private Set<String> visited = new HashSet<>();
    // Coda di URL da visitare e la loro profondità rispetto a quella principale
    private Queue<CoppiaUrlProfondita> toCrawl = new LinkedList<>();

    // Numero di pagina da vedere
    private static final int MAX_PAGES_TO_CRAWL = 1000;
    // Profondità massima
    private static final int MAX_DEPTH = 3;

    public void crawl(String startingUrl) {
        // Inizio con Creando il primo URL e la sua profondità
        toCrawl.add(new CoppiaUrlProfondita(startingUrl, 0));

        while (!toCrawl.isEmpty() && visited.size() < MAX_PAGES_TO_CRAWL) {
            CoppiaUrlProfondita current = toCrawl.poll();
            String url = current.url;
            int depth = current.depth;

            // Controllo se è stato già visitato e se la profondità è acettabile
            if (!visited.contains(url) && depth <= MAX_DEPTH) {
                System.out.println("Link : " + url + " | Profontià : " + depth);
                visited.add(url);

                try {
                    // eseguo il get per ottenere la pagina
                    Document doc = Jsoup.connect(url).get();
                    // Extract links from the page if we haven't reached max depth
                    if (depth < MAX_DEPTH) {
                        extractLinks(doc, depth + 1);
                    }
                } catch (IOException e) {
                    System.out.println("Errore in " + url);
                }
            }
        }
    }

    // Metodo per ottenere i link della pagina e inserirli nella coda di pagine da visitare.
    private void extractLinks(Document document, int depth) {
        // Ottengo tutti gli elementi link
        Elements links = document.select("a[href]");
        for (Element link : links) {
            // Ottengo Url Assoluto
            String linkUrl = link.absUrl("href");
            if (!visited.contains(linkUrl)) {
                // Inserisco i link nella coda con la loro profondità
                toCrawl.add(new CoppiaUrlProfondita(linkUrl, depth));
            }
        }
    }

    // Coppia URL e profondità a cui si trova
    private static class CoppiaUrlProfondita {
        String url;
        int depth;

        CoppiaUrlProfondita(String url, int depth) {
            this.url = url;
            this.depth = depth;
        }
    }
}
