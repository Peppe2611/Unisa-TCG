package it.unisatcg.control;

import it.unisatcg.model.*;
import it.unisatcg.model.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
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
            connection.setAutoCommit(false);

            try {
                OrdineDAO ordineDAO = new OrdineDAOImp();
                DettaglioOrdineDAO dettaglioDAO = new DettaglioOrdineDAOImp();
                ProdottoDAO prodottoDAO = new ProdottoDAOImp();

                // 1. Salva l'ordine
                Ordine ordine = new Ordine();
                ordine.setClienteId(utente.getId());
                ordine.setDataOrdine(java.time.LocalDateTime.now());
                ordine.setTotale(carrello.getTotaleComplessivo());
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
                    prodottoDAO.doUpdateQuantita(articolo.getProdotto().getId(), articolo.getQuantita(), connection);
                }

                connection.commit();
                session.removeAttribute("carrello");
                response.sendRedirect("conferma-ordine.jsp?orderId=" + ordine.getId());

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException("Errore durante il processo di acquisto", e);
        }
    }
}