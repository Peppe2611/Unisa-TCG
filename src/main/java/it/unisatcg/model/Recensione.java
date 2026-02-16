package it.unisatcg.model;

import java.sql.Date;

public class Recensione {
    private int id;
    private int prodottoId;
    private int utenteId;
    private String nomeUtente; // Campo extra per visualizzazione
    private int voto; // Da 1 a 5
    private String testo;
    private Date dataRecensione;

    public Recensione() {}

    // Getter e Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProdottoId() { return prodottoId; }
    public void setProdottoId(int prodottoId) { this.prodottoId = prodottoId; }

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public String getNomeUtente() { return nomeUtente; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }

    public int getVoto() { return voto; }
    public void setVoto(int voto) { this.voto = voto; }

    public String getTesto() { return testo; }
    public void setTesto(String testo) { this.testo = testo; }

    public Date getDataRecensione() { return dataRecensione; }
    public void setDataRecensione(Date dataRecensione) { this.dataRecensione = dataRecensione; }
}