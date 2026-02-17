package it.unisatcg.control;

import it.unisatcg.model.Prodotto;
import it.unisatcg.model.Utente;
import it.unisatcg.model.Recensione;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import it.unisatcg.model.dao.UtenteDAO;
import it.unisatcg.model.dao.UtenteDAOImp;
import it.unisatcg.model.dao.RecensioneDAO;
import it.unisatcg.model.dao.RecensioneDAOImp;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/dettaglio-prodotto")
public class ProductDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int prodottoId = -1;
        try {
            prodottoId = Integer.parseInt(request.getParameter("id"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID prodotto non valido.");
            return;
        }

        ProdottoDAO prodottoDAO = new ProdottoDAOImp();
        try {
            Prodotto prodotto = prodottoDAO.doRetrieveByKey(prodottoId);
            if (prodotto == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato.");
                return;
            }

            request.setAttribute("prodotto", prodotto);

            if (prodotto.getVenditoreId() != 1) {
                UtenteDAO utenteDAO = new UtenteDAOImp();
                Utente venditore = utenteDAO.doRetrieveByKey(prodotto.getVenditoreId());
                if (venditore != null) {
                    request.setAttribute("nomeVenditore", venditore.getNome());
                }
            }

            RecensioneDAO recensioneDAO = new RecensioneDAOImp();
            List<Recensione> recensioni = recensioneDAO.doRetrieveByProdotto(prodottoId);
            request.setAttribute("recensioni", recensioni);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/product_detail.jsp");
            dispatcher.forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Errore durante il recupero del prodotto dal database.", e);
        }
    }
}