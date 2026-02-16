package it.unisatcg.model.dao;

import it.unisatcg.model.DettaglioOrdine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DettaglioOrdineDAOImp implements DettaglioOrdineDAO {

    @Override
    public void doSave(DettaglioOrdine dettaglio) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            doSave(dettaglio, connection);
        }
    }

    @Override
    public void doSave(DettaglioOrdine dettaglio, Connection con) throws SQLException {
        String sql = "INSERT INTO dettaglioordine (ordine_id, prodotto_id, quantita, prezzo_unitario, indirizzo, cap) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, dettaglio.getOrdineId());
            ps.setInt(2, dettaglio.getProdottoId());
            ps.setInt(3, dettaglio.getQuantita());
            ps.setDouble(4, dettaglio.getPrezzoUnitario());
            ps.setString(5, dettaglio.getIndirizzo());
            ps.setString(6, dettaglio.getCap());
            ps.executeUpdate();
        }
    }

    @Override
    public List<DettaglioOrdine> doRetrieveByOrdine(int ordineId) throws SQLException {
        List<DettaglioOrdine> dettagli = new ArrayList<>();
        String sql = "SELECT * FROM dettaglio_ordine WHERE ordine_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, ordineId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DettaglioOrdine d = new DettaglioOrdine();
                    d.setOrdineId(rs.getInt("ordine_id"));
                    d.setProdottoId(rs.getInt("prodotto_id"));
                    d.setQuantita(rs.getInt("quantita"));
                    d.setPrezzoUnitario(rs.getDouble("prezzo_unitario"));
                    d.setIndirizzo(rs.getString("indirizzo"));
                    d.setCap(rs.getString("cap"));
                    dettagli.add(d);
                }
            }
        }
        return dettagli;
    }
}