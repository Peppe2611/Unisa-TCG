package it.unisatcg.model.dao;

import it.unisatcg.model.DettaglioOrdine;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DettaglioOrdineDAO {
    void doSave(DettaglioOrdine dettaglio) throws SQLException;
    void doSave(DettaglioOrdine dettaglio, Connection connection) throws SQLException;
    List<DettaglioOrdine> doRetrieveByOrdine(int ordineId) throws SQLException;
    List<Map<String, Object>> doRetrieveFullDetailsByOrdine(int ordineId) throws SQLException;
}