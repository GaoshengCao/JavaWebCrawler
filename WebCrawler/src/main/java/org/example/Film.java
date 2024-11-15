package org.example;

public class Film {
    String URL;
    String titolo;
    String regista;
    int posizione;
    int anno_Rilascio;
    int durata_Minuti;

    public Film(String url, String titolo, int posizione) {
        this.URL = url;
        this.titolo = titolo;
        this.posizione = posizione;
    }

}
