package it.unisatcg.model.dao;

import it.unisatcg.model.Utente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtenteDAOImp implements UtenteDAO {

    private Utente extractUtenteFromResultSet(ResultSet rs) throws SQLException {
        Utente utente = new Utente();
        utente.setId(rs.getInt("id"));
        utente.setNome(rs.getString("nome"));
        utente.setEmail(rs.getString("email"));
        utente.setPasswordHash(rs.getString("password_hash"));
        utente.setTelefono(rs.getString("telefono"));
        utente.setIndirizzo(rs.getString("indirizzo"));
        utente.setCap(rs.getString("cap"));
        utente.setAdmin(rs.getBoolean("is_admin"));

        // AGGIUNTA FONDAMENTALE PER IL BAN

        try {
            utente.setBanned(rs.getBoolean("is_banned"));
        } catch (SQLException e) {
            // Se la colonna non esiste ancora nel DB, ignoriamo l'errore per non bloccare tutto
            utente.setBanned(false);
        }

        return utente;
    }

    @Override
    public synchronized void doSave(Utente utente) throws SQLException {
        String insertSQL = "INSERT INTO utente (nome, email, password_hash, telefono, indirizzo, cap, is_admin, is_banned) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(insertSQL)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getEmail());
            ps.setString(3, utente.getPasswordHash());
            ps.setString(4, utente.getTelefono());
            ps.setString(5, utente.getIndirizzo());
            ps.setString(6, utente.getCap());
            ps.setBoolean(7, utente.isAdmin());
            ps.setBoolean(8, utente.isBanned());
            ps.executeUpdate();
        }
    }

    // Metodo NUOVO per gestire il ban
    public void doUpdateBan(int userId, boolean banStatus) throws SQLException {
        String updateSQL = "UPDATE utente SET is_banned = ? WHERE id = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateSQL)) {
            ps.setBoolean(1, banStatus);
            ps.setInt(2, userId);
            ps.executeUpdate();
        }
    }

    @Override
    public List<Utente> doRetrieveAll() throws SQLException {
        List<Utente> utenti = new ArrayList<>();
        String selectSQL = "SELECT * FROM utente";
        try (Connection connection = DBConnection.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(selectSQL)) {
            while (rs.next()) {
                utenti.add(extractUtenteFromResultSet(rs));
            }
        }
        return utenti;
    }



    @Override
    public Utente doRetrieveByEmail(String email) throws SQLException {
        String selectSQL = "SELECT * FROM utente WHERE email = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return extractUtenteFromResultSet(rs);
            }
        }
        return null;
    }

    @Override
    public Utente doRetrieveByTelefono(String telefono) throws SQLException {
        String selectSQL = "SELECT * FROM utente WHERE telefono = ?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(selectSQL)) {
            ps.setString(1, telefono);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return extractUtenteFromResultSet(rs);
            }
        }
        return null;
    }

    @Override
    public void doUpdate(Utente utente) throws SQLException {
        String updateSQL = "UPDATE utente SET nome=?, email=?, telefono=?, indirizzo=?, cap=?, is_admin=?, is_banned=? WHERE id=?";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement ps = connection.prepareStatement(updateSQL)) {
            ps.setString(1, utente.getNome());
            ps.setString(2, utente.getEmail());
            ps.setString(3, utente.getTelefono());
            ps.setString(4, utente.getIndirizzo());
            ps.setString(5, utente.getCap());
            ps.setBoolean(6, utente.isAdmin());
            ps.setBoolean(7, utente.isBanned());
            ps.setInt(8, utente.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void updatePassword(Utente utente) throws SQLException {
        String sql = "UPDATE utente SET password_hash = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, utente.getPasswordHash());
            ps.setInt(2, utente.getId());
            ps.executeUpdate();
        }
    }

    @Override
    public void doDelete(int id) throws SQLException {
        String sql = "DELETE FROM utente WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Utente doRetrieveByKey(int id) throws SQLException {
        String sql = "SELECT * FROM utente WHERE id = ?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return extractUtenteFromResultSet(rs);
            }
        }
        return null;
    }
}