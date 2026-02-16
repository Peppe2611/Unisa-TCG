package it.unisatcg.model.dao;

import it.unisatcg.model.Prodotto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAOImp implements ProdottoDAO {

    Prodotto extractProdottoFromResultSet(ResultSet rs) throws SQLException {
        Prodotto prodotto = new Prodotto();
        prodotto.setId(rs.getInt("id"));
        prodotto.setNome(rs.getString("nome"));
        prodotto.setDescrizione(rs.getString("descrizione"));
        prodotto.setPrezzo(rs.getDouble("prezzo"));
        prodotto.setQuantita(rs.getInt("quantita"));
        prodotto.setCategoriaId(rs.getInt("categoria_id"));
        prodotto.setSpecifiche(rs.getString("specifiche"));
        // Aggiungere supporto BLOB QUA
        return prodotto;
    }

    @Override
    public synchronized void doSave(Prodotto prodotto) throws SQLException {
        String insertSQL = "INSERT INTO prodotto (nome, descrizione, prezzo, quantita, categoria_id, specifiche) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setInt(4, prodotto.getQuantita());
            ps.setInt(5, prodotto.getCategoriaId());
            ps.setString(6, prodotto.getSpecifiche());
            ps.executeUpdate();
        }
    }

    @Override
    public Prodotto doRetrieveByKey(int id) throws SQLException {
        String selectSQL = "SELECT * FROM prodotto WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return extractProdottoFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Prodotto> doRetrieveAll() throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        String selectSQL = "SELECT * FROM prodotto";
        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(selectSQL)) {
            while (rs.next()) {
                prodotti.add(extractProdottoFromResultSet(rs));
            }
        }
        return prodotti;
    }

    @Override
    public void doUpdate(Prodotto prodotto) throws SQLException {
        String updateSQL = "UPDATE prodotto SET nome=?, descrizione=?, prezzo=?, quantita=?, categoria_id=?, specifiche=? WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateSQL)) {
            ps.setString(1, prodotto.getNome());
            ps.setString(2, prodotto.getDescrizione());
            ps.setDouble(3, prodotto.getPrezzo());
            ps.setInt(4, prodotto.getQuantita());
            ps.setInt(5, prodotto.getCategoriaId());
            ps.setString(6, prodotto.getSpecifiche());
            ps.setInt(7, prodotto.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void doUpdateQuantita(int prodottoId, int quantitaDaSottrarre, Connection con) throws SQLException {
        String updateSQL = "UPDATE prodotto SET quantita = quantita - ? WHERE id = ?";

        try (PreparedStatement ps = con.prepareStatement(updateSQL)) {
            ps.setInt(1, quantitaDaSottrarre);
            ps.setInt(2, prodottoId);
            ps.executeUpdate();
        }

    }

    @Override
    public void doDelete(int id) throws SQLException {
        String deleteSQL = "DELETE FROM prodotto WHERE id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(deleteSQL)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Prodotto> doRetrieveByCategoria(int categoriaId) throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        String selectSQL = "SELECT * FROM prodotto WHERE categoria_id = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, categoriaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prodotti.add(extractProdottoFromResultSet(rs));
                }
            }
        }
        return prodotti;
    }

    @Override
    public List<Prodotto> doRetrieveByCategoriaDisp(int categoriaId, String disp) throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        String selectSQL = "SELECT * FROM prodotto WHERE categoria_id = ?";

        if (disp.equals("Disponibile")) {
            selectSQL += " AND quantita > 0";
        } else if (disp.equals("Esaurito")) {
            selectSQL += " AND quantita = 0";
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setInt(1, categoriaId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    prodotti.add(extractProdottoFromResultSet(rs));
                }
            }
        }
        return prodotti;
    }

    @Override
    public List<Prodotto> doRetrieveAllDisp(String disp) throws SQLException {
        List<Prodotto> prodotti = new ArrayList<>();
        String selectSQL = "SELECT * FROM prodotto";

        if ("Disponibile".equals(disp)) {
            selectSQL += " WHERE quantita > 0";
        } else if ("Esaurito".equals(disp)) {
            selectSQL += " WHERE quantita = 0";
        }

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                prodotti.add(extractProdottoFromResultSet(rs));
            }
        }
        return prodotti;
    }
}