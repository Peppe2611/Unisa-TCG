package it.unisatcg.control;

import it.unisatcg.model.ArticoloCarrello;
import it.unisatcg.model.Carrello;
import it.unisatcg.model.Prodotto;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/visualizza-carrello")
public class VisualizzaCarrelloServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (carrello != null && !carrello.getArticoli().isEmpty()) {
            ProdottoDAO prodottoDAO = new ProdottoDAOImp();
            List<ArticoloCarrello> daRimuovere = new ArrayList<>();

            try {
                for (ArticoloCarrello articolo : carrello.getArticoli()) {
                    Prodotto pDB = prodottoDAO.doRetrieveByKey(articolo.getProdotto().getId());
                    if (pDB == null || pDB.getQuantita() <= 0) {
                        daRimuovere.add(articolo);
                    } else if (articolo.getQuantita() > pDB.getQuantita()) {
                        articolo.setQuantita(pDB.getQuantita());
                        request.setAttribute("messaggioCarrello", "Quantità aggiornate in base alla disponibilità.");
                    }
                }
                daRimuovere.forEach(a -> carrello.rimuoviArticolo(a.getProdotto().getId()));
                if(!daRimuovere.isEmpty()) request.setAttribute("messaggioCarrello", "Alcuni articoli non più disponibili sono stati rimossi.");
            } catch (SQLException e) { e.printStackTrace(); }
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
        dispatcher.forward(request, response);
    }
}