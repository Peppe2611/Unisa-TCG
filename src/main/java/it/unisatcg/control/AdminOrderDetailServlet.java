package it.unisatcg.control;

import it.unisatcg.model.Ordine;
import it.unisatcg.model.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/order-detail")
public class AdminOrderDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        try {
            OrdineDAO ordineDAO = new OrdineDAOImp();
            DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAOImp();

            Ordine ordine = ordineDAO.doRetrieveByKey(id);
            List<Map<String, Object>> articoli = dettaglioDAO.doRetrieveFullDetailsByOrdine(id);

            request.setAttribute("ordine", ordine);
            request.setAttribute("articoli", articoli);
            request.getRequestDispatcher("/admin/admin_order_detail.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}