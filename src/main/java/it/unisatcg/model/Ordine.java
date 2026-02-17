package it.unisatcg.model;

import java.time.LocalDateTime;

public class Ordine {
    private int id;
    private int clienteId;
    private LocalDateTime dataOrdine;
    private double totale;
    private String status;

    public Ordine() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }
    public LocalDateTime getDataOrdine() { return dataOrdine; }
    public void setDataOrdine(LocalDateTime dataOrdine) { this.dataOrdine = dataOrdine; }
    public double getTotale() { return totale; }
    public void setTotale(double totale) { this.totale = totale; }
    public String getStatus() {return status;}
    public void setStatus(String status){this.status = status;}
}