package it.unisatcg.control;

import it.unisatcg.model.Recensione;
import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.RecensioneDAO;
import it.unisatcg.model.dao.RecensioneDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

@WebServlet("/RecensioneServlet")
public class RecensioneServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Controllo se l'utente è loggato
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int prodottoId = Integer.parseInt(request.getParameter("prodottoId"));
            int voto = Integer.parseInt(request.getParameter("voto"));
            String testo = request.getParameter("testo");

            Recensione recensione = new Recensione();
            recensione.setProdottoId(prodottoId);
            recensione.setUtenteId(utente.getId());
            recensione.setVoto(voto);
            recensione.setTesto(testo);
            recensione.setDataRecensione(new Date(System.currentTimeMillis()));

            RecensioneDAO dao = new RecensioneDAOImp();
            dao.doSave(recensione);

            // Torna alla pagina del prodotto
            response.sendRedirect("dettaglio-prodotto?id=" + prodottoId);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel salvataggio della recensione");
        }
    }
}