package it.unisatcg.model;

public class DettaglioOrdine {
    private int ordineId;
    private int prodottoId;
    private int quantita;
    private double prezzoUnitario;
    private String indirizzo;
    private String cap;


    // NUOVO CAMPO (Serve solo per visualizzare il nome nella pagina, non va salvato in dettaglio_ordine)
    private String nomeProdotto;

    public DettaglioOrdine() {}

    public int getOrdineId() { return ordineId; }
    public void setOrdineId(int ordineId) { this.ordineId = ordineId; }

    public int getProdottoId() { return prodottoId; }
    public void setProdottoId(int prodottoId) { this.prodottoId = prodottoId; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public double getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(double prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }

    // Getter e Setter per il NOME PRODOTTO
    public String getNomeProdotto() { return nomeProdotto; }
    public void setNomeProdotto(String nomeProdotto) { this.nomeProdotto = nomeProdotto; }
}