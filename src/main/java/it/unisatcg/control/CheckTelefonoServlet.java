package it.unisatcg.control; // MODIFICA: Allineato al nuovo package

import it.unisatcg.model.dao.UtenteDAO;
import it.unisatcg.model.dao.UtenteDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/check-telefono")
public class CheckTelefonoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String telefono = request.getParameter("telefono");
        UtenteDAO utenteDAO = new UtenteDAOImp();
        boolean exists = false;

        try {
            if (telefono != null && !telefono.trim().isEmpty()) {
                exists = (utenteDAO.doRetrieveByTelefono(telefono) != null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // MODIFICA: Sostituito redirect con sendError per compatibilità AJAX
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore database");
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"exists\": " + exists + "}");
    }
}