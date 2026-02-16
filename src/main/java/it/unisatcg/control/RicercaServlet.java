package it.unisatcg.control;

import it.unisatcg.model.Prodotto;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ricerca")
public class RicercaServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("q");

        ProdottoDAO prodottoDAO = new ProdottoDAOImp();
        List<Prodotto> prodotti;

        try {
            if (query != null && !query.trim().isEmpty()) {
                prodotti = prodottoDAO.doRetrieveByNome(query);
            } else {
                prodotti = prodottoDAO.doRetrieveAll();
            }

            // Usiamo la stessa pagina "products.jsp" per mostrare i risultati
            request.setAttribute("products", prodotti);
            request.setAttribute("searchQuery", query);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/products.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante la ricerca");
        }
    }
}