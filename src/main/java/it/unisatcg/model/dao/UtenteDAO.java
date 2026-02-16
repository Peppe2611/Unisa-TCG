package it.unisatcg.model.dao;
import it.unisatcg.model.Utente;
import java.sql.SQLException;
import java.util.List;

public interface UtenteDAO {
    void doSave(Utente utente) throws SQLException;
    Utente doRetrieveByEmail(String email) throws SQLException;
    Utente doRetrieveByTelefono(String telefono) throws SQLException;
    Utente doRetrieveByKey(int id) throws SQLException;
    List<Utente> doRetrieveAll() throws SQLException;
    void doUpdate(Utente utente) throws SQLException;
    void doDelete(int id) throws SQLException;
    void updatePassword(Utente utente) throws SQLException;
}