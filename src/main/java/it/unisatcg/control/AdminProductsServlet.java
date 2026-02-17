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
            // 1. Recupera tutti i prodotti dal database
            List<Prodotto> prodotti = prodottoDAO.doRetrieveAll();

            // 2. Li salva nella richiesta per la JSP
            request.setAttribute("products", prodotti);

            // 3. Invia tutto alla pagina di visualizzazione (nota l'underscore nel nome file)
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/gestione_prodotti.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Impossibile caricare i prodotti");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}