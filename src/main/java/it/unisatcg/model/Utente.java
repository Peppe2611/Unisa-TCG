package it.unisatcg.model;

public class Utente {
    private int id;
    private String nome;
    // Rimosso cognome se non lo usi, oppure puoi aggiungerlo se serve
    private String email;
    private String passwordHash;
    private String telefono;
    private String indirizzo;
    private String cap;
    private boolean is_admin;
    private boolean isBanned; // NUOVO CAMPO

    public Utente() {}

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    // Se vuoi usare il cognome in futuro, aggiungi qui il campo e i metodi get/set
    // Per ora la JSP userà solo getNome()

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getIndirizzo() { return indirizzo; }
    public void setIndirizzo(String indirizzo) { this.indirizzo = indirizzo; }

    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }

    public boolean isAdmin() { return is_admin; }
    public void setAdmin(boolean isAdmin) { this.is_admin = isAdmin; }

    // GETTER E SETTER PER IL BAN (Fondamentali)
    public boolean isBanned() { return isBanned; }
    public void setBanned(boolean banned) { isBanned = banned; }
}