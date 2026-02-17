package it.unisatcg.model.dao;

import it.unisatcg.model.Recensione;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAOImp implements RecensioneDAO {

    @Override
    public synchronized void doSave(Recensione recensione) throws SQLException {
        String insertSQL = "INSERT INTO recensione (prodotto_id, utente_id, voto, testo, data_recensione) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setInt(1, recensione.getProdottoId());
            ps.setInt(2, recensione.getUtenteId());


            ps.setInt(3, recensione.getVoto());

            ps.setString(4, recensione.getTesto());
            ps.setDate(5, recensione.getDataRecensione());
            ps.executeUpdate();
        }
    }

    @Override
    public List<Recensione> doRetrieveByProdotto(int prodottoId) throws SQLException {
        List<Recensione> recensioni = new ArrayList<>();
        String selectSQL = "SELECT r.*, u.nome FROM recensione r JOIN utente u ON r.utente_id = u.id WHERE r.prodotto_id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, prodottoId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Recensione bean = new Recensione();
                    bean.setId(rs.getInt("id"));
                    bean.setProdottoId(rs.getInt("prodotto_id"));
                    bean.setUtenteId(rs.getInt("utente_id"));
                    bean.setNomeUtente(rs.getString("nome"));

                    // CORRETTO: usa setVoto()
                    bean.setVoto(rs.getInt("voto"));

                    bean.setTesto(rs.getString("testo"));
                    bean.setDataRecensione(rs.getDate("data_recensione"));
                    recensioni.add(bean);
                }
            }
        }
        return recensioni;
    }

    public List<Recensione> doRetrieveAll() throws SQLException {
        List<Recensione> recensioni = new ArrayList<>();
        String selectSQL = "SELECT r.*, u.nome FROM recensione r LEFT JOIN utente u ON r.utente_id = u.id";

        try (Connection connection = DBConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(selectSQL)) {
            while (rs.next()) {
                Recensione bean = new Recensione();
                bean.setId(rs.getInt("id"));
                bean.setProdottoId(rs.getInt("prodotto_id"));
                bean.setUtenteId(rs.getInt("utente_id"));
                try { bean.setNomeUtente(rs.getString("nome")); } catch(SQLException e) { bean.setNomeUtente("Utente " + rs.getInt("utente_id")); }


                bean.setVoto(rs.getInt("voto"));

                bean.setTesto(rs.getString("testo"));
                bean.setDataRecensione(rs.getDate("data_recensione"));
                recensioni.add(bean);
            }
        }
        return recensioni;
    }

    public void doDelete(int recensioneId) throws SQLException {
        String deleteSQL = "DELETE FROM recensione WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
            ps.setInt(1, recensioneId);
            ps.executeUpdate();
        }
    }
}