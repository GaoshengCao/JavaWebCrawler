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

    // Set to store visited URLs to avoid re-crawling them
    private Set<String> visited = new HashSet<>();
    // Queue to store URLs to be crawled
    private Queue<String> toCrawl = new LinkedList<>();

    // Max number of pages to crawl
    private static final int MAX_PAGES_TO_CRAWL = 100;

    public void crawl(String startingUrl) {
        toCrawl.add(startingUrl);

        while (!toCrawl.isEmpty() && visited.size() < MAX_PAGES_TO_CRAWL) {
            String url = toCrawl.poll();
            if (!visited.contains(url)) {
                System.out.println("Crawling: " + url);
                visited.add(url);
                try {
                    // Fetch and parse the page using Jsoup
                    Document doc = Jsoup.connect(url).get();
                    // Extract links from the page
                    extractLinks(doc);
                } catch (IOException e) {
                    System.out.println("Failed to crawl " + url);
                }
            }
        }
    }

    // Method to extract all links from a page and add them to the crawl queue
    private void extractLinks(Document document) {
        Elements links = document.select("a[href]");  // Select all anchor tags with href attribute
        for (Element link : links) {
            String linkUrl = link.absUrl("href"); // Get absolute URL
            if (!visited.contains(linkUrl)) {
                toCrawl.add(linkUrl);
            }
        }
    }
}
