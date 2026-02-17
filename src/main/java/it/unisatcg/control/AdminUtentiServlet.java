package it.unisatcg.control;

import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.UtenteDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/gestione-utenti")
public class AdminUtentiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UtenteDAOImp dao = new UtenteDAOImp();
        try {
            List<Utente> utenti = dao.doRetrieveAll();
            request.setAttribute("utenti", utenti);
            request.getRequestDispatcher("/admin/gestione_utenti.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Errore database");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int userId = Integer.parseInt(request.getParameter("userId"));
        UtenteDAOImp dao = new UtenteDAOImp();

        try {
            if ("ban".equals(action)) {
                dao.doUpdateBan(userId, true);
            } else if ("unban".equals(action)) {
                dao.doUpdateBan(userId, false);
            }
            response.sendRedirect("gestione-utenti");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Errore aggiornamento ban");
        }
    }
}