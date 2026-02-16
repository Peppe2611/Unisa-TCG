package it.unisatcg.control; // MODIFICA: Allineato al nuovo package del progetto

import it.unisatcg.model.dao.UtenteDAO;
import it.unisatcg.model.dao.UtenteDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/check-email")
public class CheckEmailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        UtenteDAO utenteDAO = new UtenteDAOImp();
        boolean exists = false;

        try {
            if (email != null && !email.trim().isEmpty()) {
                exists = (utenteDAO.doRetrieveByEmail(email) != null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // MODIFICA: Rimosso sendRedirect. Nelle chiamate AJAX, un redirect invia HTML invece di JSON,
            // mandando in crash lo script 'RegistrationValidator.js'. Usiamo un errore HTTP 500.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore database");
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        // Restituisce il JSON richiesto dallo script JS
        response.getWriter().write("{\"exists\": " + exists + "}");
    }
}