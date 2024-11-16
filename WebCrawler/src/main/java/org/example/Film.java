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

    public void setRegista(String regista) { this.regista = regista; }
    public void setAnno_Rilascio(int anno_Rilascio) { this.anno_Rilascio = anno_Rilascio; }
    public void setDurata_Minuti(int durata_Minuti) { this.durata_Minuti = durata_Minuti; }

}
