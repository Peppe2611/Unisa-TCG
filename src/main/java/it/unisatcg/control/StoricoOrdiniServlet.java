package it.unisatcg.control;

import it.unisatcg.model.Ordine;
import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.OrdineDAO; // Assumi che esista
import it.unisatcg.model.dao.OrdineDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/profilo")
public class StoricoOrdiniServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Inizializziamo il DAO degli ordini
        OrdineDAO ordineDAO = new OrdineDAOImp();

        try {
            // Recuperiamo solo gli ordini dell'utente loggato
            List<Ordine> ordiniUtente = ordineDAO.doRetrieveByUtente(utente.getId());
            request.setAttribute("ordini", ordiniUtente);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Errore nel recupero dello storico ordini.");
        }

        request.getRequestDispatcher("/profilo.jsp").forward(request, response);
    }
}