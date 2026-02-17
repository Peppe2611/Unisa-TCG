package it.unisatcg.control;

import it.unisatcg.model.Ordine;
import it.unisatcg.model.DettaglioOrdine;
import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.OrdineDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/dettaglio-ordine")
public class UserOrderDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        // Controllo Login
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("storico-ordini");
            return;
        }

        OrdineDAOImp dao = new OrdineDAOImp();
        try {
            int ordineId = Integer.parseInt(idStr);

            // 1. Recupera le info generali dell'ordine
            Ordine ordine = dao.doRetrieveByKey(ordineId);

            if (ordine == null) {
                response.sendError(404, "Ordine non trovato");
                return;
            }


            if (ordine.getClienteId() != utente.getId()) {
                response.sendError(403, "Non puoi vedere gli ordini degli altri.");
                return;
            }


            List<DettaglioOrdine> dettagli = dao.doRetrieveDettagli(ordineId);

            // Passiamo tutto alla JSP
            request.setAttribute("ordine", ordine);
            request.setAttribute("dettagli", dettagli);

            request.getRequestDispatcher("/user_order_detail.jsp").forward(request, response);

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            response.sendError(500, "Errore nel recupero dell'ordine");
        }
    }
}