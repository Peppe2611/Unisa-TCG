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

@WebServlet("/admin/gestione-prodotti")
public class AdminProductsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO prodottoDAO = new ProdottoDAOImp();
        try {
            List<Prodotto> prodotti = prodottoDAO.doRetrieveAll();
            request.setAttribute("products", prodotti);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/gestione_prodotti.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile caricare i prodotti");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        // GESTIONE ELIMINAZIONE
        if ("delete".equals(action)) {
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    ProdottoDAO dao = new ProdottoDAOImp();
                    dao.doDelete(id);

                    // Redirect per ricaricare la lista pulita
                    response.sendRedirect("gestione-prodotti?deleted=true");
                    return;
                } catch (SQLException | NumberFormatException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'eliminazione");
                    return;
                }
            }
        }


        doGet(request, response);
    }
}