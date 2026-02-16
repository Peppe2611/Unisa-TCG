package it.unisatcg.model.dao;
import it.unisatcg.model.Recensione;
import java.sql.SQLException;
import java.util.List;

public interface RecensioneDAO {
    void doSave(Recensione recensione) throws SQLException;
    List<Recensione> doRetrieveByProdotto(int prodottoId) throws SQLException;
}