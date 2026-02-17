package it.unisatcg.control;


import it.unisatcg.model.Ordine;
import it.unisatcg.model.Utente;
import it.unisatcg.model.Carrello;
import it.unisatcg.model.ArticoloCarrello;
import it.unisatcg.model.DettaglioOrdine;

import it.unisatcg.model.dao.DBConnection;
import it.unisatcg.model.dao.OrdineDAO;
import it.unisatcg.model.dao.DettaglioOrdineDAO;
import it.unisatcg.model.dao.DettaglioOrdineDAOImp;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import it.unisatcg.model.dao.OrdineDAOImp;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;
        Carrello carrello = (session != null) ? (Carrello) session.getAttribute("carrello") : null;

        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        if (carrello == null || carrello.getArticoli().isEmpty()) {
            response.sendRedirect("cart.jsp");
            return;
        }

        try (Connection connection = DBConnection.getConnection()) {
            connection.setAutoCommit(false); // Inizio Transazione

            try {
                OrdineDAO ordineDAO = new OrdineDAOImp();
                DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAOImp();
                ProdottoDAO prodottoDAO = new ProdottoDAOImp();

                // 1. Salva l'ordine
                Ordine ordine = new Ordine();
                ordine.setClienteId(utente.getId());
                ordine.setDataOrdine(java.time.LocalDateTime.now());
                ordine.setTotale(carrello.getTotaleComplessivo());
                ordine.setStatus("Ordinato");
                ordineDAO.doSave(ordine, connection);

                // 2. Salva i dettagli e aggiorna il magazzino
                for (ArticoloCarrello articolo : carrello.getArticoli()) {
                    DettaglioOrdine dettaglio = new DettaglioOrdine();
                    dettaglio.setOrdineId(ordine.getId());
                    dettaglio.setProdottoId(articolo.getProdotto().getId());
                    dettaglio.setQuantita(articolo.getQuantita());
                    dettaglio.setPrezzoUnitario(articolo.getProdotto().getPrezzo());
                    dettaglio.setIndirizzo(utente.getIndirizzo());
                    dettaglio.setCap(utente.getCap());

                    dettaglioDAO.doSave(dettaglio, connection);
                    // Usiamo il metodo che avevamo preparato per scalare le scorte
                    prodottoDAO.doUpdateQuantita(articolo.getProdotto().getId(), articolo.getQuantita(), connection);
                }

                connection.commit(); // Conferma tutto
                session.removeAttribute("carrello");
                response.sendRedirect("conferma-ordine.jsp?orderId=" + ordine.getId());

            } catch (SQLException e) {
                connection.rollback(); // Annulla tutto in caso di errore
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore durante il processo di acquisto", e);
        }
    }
}