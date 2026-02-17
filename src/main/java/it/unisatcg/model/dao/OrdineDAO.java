package it.unisatcg.model.dao;

import it.unisatcg.model.DettaglioOrdine;
import it.unisatcg.model.Ordine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrdineDAO {
    void doSave(Ordine ordine) throws SQLException;
    void doSave(Ordine ordine, Connection con) throws SQLException; // Per transazioni
    Ordine doRetrieveByKey(int id) throws SQLException;
    List<Ordine> doRetrieveByUtente(int clienteId) throws SQLException;
    List<Ordine> doRetrieveAll() throws SQLException;
    void doUpdate(Ordine ordine) throws SQLException;
    void doDelete(int id) throws SQLException;
    void updateStatus(int id, String status) throws SQLException;
    public List<DettaglioOrdine> doRetrieveDettagli(int ordineId) throws SQLException;
}