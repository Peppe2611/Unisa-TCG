package it.unisatcg.control;

import it.unisatcg.model.Ordine;
import it.unisatcg.model.dao.OrdineDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import it.unisatcg.model.dao.OrdineDAOImp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/ordini")
public class AdminOrdiniServlet extends HttpServlet {

    private OrdineDAO ordineDAO = new OrdineDAOImp();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            List<Ordine> ordini = ordineDAO.doRetrieveAll();
            request.setAttribute("ordini", ordini);

            request.getRequestDispatcher("/admin/admin_orders.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String status = request.getParameter("status");

            ordineDAO.updateStatus(id, status);

            response.sendRedirect(request.getContextPath() + "/admin/ordini");

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}