package it.unisatcg.model.dao;

import it.unisatcg.model.Ordine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAOImp implements OrdineDAO {

    @Override
    public void doSave(Ordine ordine) throws SQLException {
        try (Connection connection = DBConnection.getConnection()) {
            doSave(ordine, connection);
        }
    }

    @Override
    public void doSave(Ordine ordine, Connection con) throws SQLException {
        String insertSQL = "INSERT INTO ordine (cliente_id, data_ordine, totale) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, ordine.getClienteId());
            ps.setTimestamp(2, Timestamp.valueOf(ordine.getDataOrdine()));
            ps.setDouble(3, ordine.getTotale());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ordine.setId(rs.getInt(1));
                }
            }
        }
    }

    @Override
    public Ordine doRetrieveByKey(int id) throws SQLException {
        String sql = "SELECT * FROM ordine WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Ordine() {{
                        setId(rs.getInt("id"));
                        setClienteId(rs.getInt("cliente_id"));
                        setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                        setTotale(rs.getDouble("totale"));
                    }};
                }
            }
        }
        return null;
    }

    @Override
    public List<Ordine> doRetrieveByUtente(int clienteId) throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT * FROM ordine WHERE cliente_id = ? ORDER BY data_ordine DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, clienteId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Ordine o = new Ordine();
                    o.setId(rs.getInt("id"));
                    o.setClienteId(rs.getInt("cliente_id"));
                    o.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                    o.setTotale(rs.getDouble("totale"));
                    ordini.add(o);
                }
            }
        }
        return ordini;
    }

    @Override
    public List<Ordine> doRetrieveAll() throws SQLException {
        List<Ordine> ordini = new ArrayList<>();
        String sql = "SELECT * FROM ordine ORDER BY data_ordine DESC";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Ordine o = new Ordine();
                o.setId(rs.getInt("id"));
                o.setClienteId(rs.getInt("cliente_id"));
                o.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                o.setTotale(rs.getDouble("totale"));
                ordini.add(o);
            }
        }
        return ordini;
    }

    @Override public void doUpdate(Ordine o) throws SQLException { /* Implementazione standard */ }
    @Override public void doDelete(int id) throws SQLException { /* Implementazione standard */ }
}