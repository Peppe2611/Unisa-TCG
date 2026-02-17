package it.unisatcg.model.dao;

import it.unisatcg.model.Ordine;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class OrdineDAOImp implements it.unisatcg.model.dao.OrdineDAO {

    @Override
    public void doSave(Ordine ordine) throws SQLException {
        try (Connection connection = it.unisatcg.model.dao.DBConnection.getConnection()) {
            doSave(ordine, connection);
        }
    }

    @Override
    public void doSave(Ordine ordine, Connection con) throws SQLException {

        String insertSQL =
                "INSERT INTO ordine (cliente_id, data_ordine, totale, status) " +
                        "VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, ordine.getClienteId());
            ps.setTimestamp(2, Timestamp.valueOf(ordine.getDataOrdine()));
            ps.setDouble(3, ordine.getTotale());
            ps.setString(4, ordine.getStatus());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    ordine.setId(rs.getInt(1));
                }
            }
        }
    }

    // =========================================================
    // RETRIEVE BY KEY
    // =========================================================

    @Override
    public Ordine doRetrieveByKey(int id) throws SQLException {

        String sql = "SELECT * FROM ordine WHERE id = ?";

        try (Connection conn = it.unisatcg.model.dao.DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    Ordine ordine = new Ordine();
                    ordine.setId(rs.getInt("id"));
                    ordine.setClienteId(rs.getInt("cliente_id"));
                    ordine.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                    ordine.setTotale(rs.getDouble("totale"));
                    ordine.setStatus(rs.getString("status"));  // ✅ NUOVO

                    return ordine;
                }
            }
        }

        return null;
    }

    // =========================================================
    // RETRIEVE BY UTENTE
    // =========================================================

    @Override
    public List<Ordine> doRetrieveByUtente(int clienteId) throws SQLException {

        List<Ordine> ordini = new ArrayList<>();

        String sql =
                "SELECT * FROM ordine WHERE cliente_id = ? " +
                        "ORDER BY data_ordine DESC";

        try (Connection conn = it.unisatcg.model.dao.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clienteId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {

                    Ordine o = new Ordine();
                    o.setId(rs.getInt("id"));
                    o.setClienteId(rs.getInt("cliente_id"));
                    o.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                    o.setTotale(rs.getDouble("totale"));
                    o.setStatus(rs.getString("status"));   // ✅ NUOVO

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

        try (Connection conn = it.unisatcg.model.dao.DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {

                Ordine o = new Ordine();
                o.setId(rs.getInt("id"));
                o.setClienteId(rs.getInt("cliente_id"));
                o.setDataOrdine(rs.getTimestamp("data_ordine").toLocalDateTime());
                o.setTotale(rs.getDouble("totale"));
                o.setStatus(rs.getString("status"));   // ✅ NUOVO

                ordini.add(o);
            }
        }

        return ordini;
    }

    // =========================================================
    // UPDATE ORDINE
    // =========================================================

    @Override
    public void doUpdate(Ordine ordine) throws SQLException {

        String sql =
                "UPDATE ordine SET cliente_id = ?, data_ordine = ?, totale = ?, status = ? " +
                        "WHERE id = ?";

        try (Connection conn = it.unisatcg.model.dao.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, ordine.getClienteId());
            ps.setTimestamp(2, Timestamp.valueOf(ordine.getDataOrdine()));
            ps.setDouble(3, ordine.getTotale());
            ps.setString(4, ordine.getStatus());   // ✅ NUOVO
            ps.setInt(5, ordine.getId());

            ps.executeUpdate();
        }
    }

    // =========================================================
    // UPDATE SOLO STATUS (ADMIN)
    // =========================================================

    @Override
    public void updateStatus(int id, String status) throws SQLException {

        String sql = "UPDATE ordine SET status = ? WHERE id = ?";

        try (Connection conn = it.unisatcg.model.dao.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, id);

            ps.executeUpdate();
        }
    }

    // =========================================================
    // DELETE
    // =========================================================

    @Override
    public void doDelete(int id) throws SQLException {

        String sql = "DELETE FROM ordine WHERE id = ?";

        try (Connection conn = it.unisatcg.model.dao.DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
