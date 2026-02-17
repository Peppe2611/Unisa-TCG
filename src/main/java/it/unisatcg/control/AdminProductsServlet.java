package it.unisatcg.control;

import it.unisatcg.model.Prodotto;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/manage-products")
public class AdminProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("utente") == null || !((it.unisatcg.model.Utente)session.getAttribute("utente")).isAdmin()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=unauthorized");
            return;
        }

        ProdottoDAO prodottoDAO = new ProdottoDAOImp();
        try {
            List<Prodotto> prodotti = prodottoDAO.doRetrieveAll();
            request.setAttribute("products", prodotti);
            request.getRequestDispatcher("/admin/gestione_prodotti.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Errore nel recupero catalogo admin", e);
        }
    }
}