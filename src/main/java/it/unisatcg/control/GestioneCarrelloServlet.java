package it.unisatcg.control;

import it.unisatcg.model.ArticoloCarrello;
import it.unisatcg.model.Carrello;
import it.unisatcg.model.Prodotto;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/gestione-carrello")
public class GestioneCarrelloServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Carrello carrello = (Carrello) session.getAttribute("carrello");
        if (carrello == null) {
            carrello = new Carrello();
            session.setAttribute("carrello", carrello);
        }

        String azione = request.getParameter("azione");
        String ajax = request.getParameter("ajax");

        try {
            int prodottoId = Integer.parseInt(request.getParameter("prodottoId"));

            if ("aggiungi".equals(azione)) {
                int quantita = Integer.parseInt(request.getParameter("quantita"));
                ProdottoDAO prodottoDAO = new ProdottoDAOImp();
                Prodotto prodotto = prodottoDAO.doRetrieveByKey(prodottoId);
                if (prodotto != null) {
                    carrello.aggiungiArticolo(prodotto, quantita);
                }
            } else if ("rimuovi".equals(azione)) {
                carrello.rimuoviArticolo(prodottoId);
            } else if ("aggiorna".equals(azione)) {
                int quantita = Integer.parseInt(request.getParameter("quantita"));
                carrello.aggiornaQuantita(prodottoId, quantita);

                // Gestione risposta AJAX per aggiornamento dinamico (DG_2)
                if ("true".equals(ajax)) {
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    double subtotale = carrello.getArticoli().stream()
                            .filter(a -> a.getProdotto().getId() == prodottoId)
                            .mapToDouble(ArticoloCarrello::getPrezzoTotale).findFirst().orElse(0);

                    String json = String.format("{\"totaleCarrello\": %.2f, \"subtotaleArticolo\": %.2f}",
                            carrello.getTotaleComplessivo(), subtotale);
                    response.getWriter().write(json.replace(",", "."));
                    return;
                }
            }
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Dopo l'aggiunta/rimozione, torna al carrello
        response.sendRedirect("visualizza-carrello");
    }
}