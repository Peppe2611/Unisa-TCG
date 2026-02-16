package it.unisatcg.model.dao;

import it.unisatcg.model.Recensione;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RecensioneDAOImp implements RecensioneDAO {

    @Override
    public synchronized void doSave(Recensione recensione) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;

        String insertSQL = "INSERT INTO recensione (prodotto_id, utente_id, voto, testo, data_recensione) VALUES (?, ?, ?, ?, ?)";

        try {
            connection = DBConnection.getConnection();
            ps = connection.prepareStatement(insertSQL);
            ps.setInt(1, recensione.getProdottoId());
            ps.setInt(2, recensione.getUtenteId());
            ps.setInt(3, recensione.getVoto());
            ps.setString(4, recensione.getTesto());
            ps.setDate(5, recensione.getDataRecensione());

            ps.executeUpdate();
        } finally {
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }
    }

    @Override
    public List<Recensione> doRetrieveByProdotto(int prodottoId) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        List<Recensione> recensioni = new ArrayList<>();

        // Join per ottenere anche il nome dell'utente
        String selectSQL = "SELECT r.*, u.nome FROM recensione r JOIN utente u ON r.utente_id = u.id WHERE r.prodotto_id = ?";

        try {
            connection = DBConnection.getConnection();
            ps = connection.prepareStatement(selectSQL);
            ps.setInt(1, prodottoId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Recensione bean = new Recensione();
                bean.setId(rs.getInt("id"));
                bean.setProdottoId(rs.getInt("prodotto_id"));
                bean.setUtenteId(rs.getInt("utente_id"));
                bean.setNomeUtente(rs.getString("nome")); // Imposto il nome preso dalla join
                bean.setVoto(rs.getInt("voto"));
                bean.setTesto(rs.getString("testo"));
                bean.setDataRecensione(rs.getDate("data_recensione"));
                recensioni.add(bean);
            }
        } finally {
            if (ps != null) ps.close();
            if (connection != null) connection.close();
        }
        return recensioni;
    }
}