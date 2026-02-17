package it.unisatcg.control;

import it.unisatcg.model.Prodotto;
import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@WebServlet("/vendi")
@MultipartConfig(maxFileSize = 16177215) // Max 16MB per l'immagine
public class VenditaServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 1. Controllo se l'utente è loggato
        Utente utente = (Utente) request.getSession().getAttribute("utente");
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 2. Recupero parametri dal form
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));
            int categoriaId = Integer.parseInt(request.getParameter("categoriaId"));
            String specifiche = request.getParameter("specifiche");

            // 3. Gestione Immagine
            Part filePart = request.getPart("foto");
            byte[] fotoBytes = null;
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    fotoBytes = inputStream.readAllBytes();
                }
            }

            // 4. Creazione Oggetto Prodotto
            Prodotto prodotto = new Prodotto();
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setQuantita(quantita);
            prodotto.setCategoriaId(categoriaId);
            prodotto.setSpecifiche(specifiche);
            prodotto.setFoto(fotoBytes);
            prodotto.setVenditoreId(utente.getId()); // IMPORTANTE: Collego l'utente!
            prodotto.setDisponibile(true);

            // 5. Salvataggio nel DB
            ProdottoDAO dao = new ProdottoDAOImp();
            dao.doSave(prodotto);

            // 6. Redirect al successo
            response.sendRedirect("visualizza-prodotti?msg=success_vendita");

            // Nel catch della VenditaServlet
        } catch (Exception e) { // Cattura Exception generica per sicurezza
            e.printStackTrace(); // STAMPA L'ERRORE IN CONSOLE
            System.out.println("ERRORE SALVATAGGIO: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore: " + e.getMessage());
        }
    }
}