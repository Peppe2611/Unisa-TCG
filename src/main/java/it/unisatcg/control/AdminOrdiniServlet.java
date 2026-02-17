package it.unisatcg.control;

import it.unisatcg.model.Ordine;
import it.unisatcg.model.dao.OrdineDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/admin/manage-orders")
public class AdminOrdiniServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Allineato il nome attributo a "ordini" come richiesto dalla tua JSP
            request.setAttribute("ordini", new OrdineDAOImp().doRetrieveAll());
            request.getRequestDispatcher("/admin/admin_orders.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Errore recupero ordini", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");

            new OrdineDAOImp().updateStatus(id, status);

            response.sendRedirect("manage-orders?success=updated");
        } catch (Exception e) {
            throw new ServletException("Errore aggiornamento stato", e);
        }
    }
}