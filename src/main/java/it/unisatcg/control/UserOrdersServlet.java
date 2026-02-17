package it.unisatcg.control;

import it.unisatcg.model.Ordine;
import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.OrdineDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/storico-ordini")
public class UserOrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        OrdineDAOImp ordineDAO = new OrdineDAOImp();
        try {
            List<Ordine> ordini = ordineDAO.doRetrieveByUtente(utente.getId());
            request.setAttribute("ordini", ordini);
            request.getRequestDispatcher("/user_orders.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Errore Database");
        }
    }
}