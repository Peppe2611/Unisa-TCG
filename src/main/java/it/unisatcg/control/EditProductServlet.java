package it.unisatcg.control;

import it.unisatcg.model.Categoria;
import it.unisatcg.model.Prodotto;
import it.unisatcg.model.dao.CategoriaDAO;
import it.unisatcg.model.dao.CategoriaDAOImp;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import jakarta.servlet.RequestDispatcher;
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
import java.util.List;

@WebServlet("/admin/edit-product")
@MultipartConfig(maxFileSize = 16177215)
public class EditProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("id");
        if (idStr == null || idStr.isEmpty()) {
            response.sendRedirect("gestione-prodotti");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            ProdottoDAO prodottoDAO = new ProdottoDAOImp();
            Prodotto prodotto = prodottoDAO.doRetrieveByKey(id);

            if (prodotto == null) {
                response.sendRedirect("gestione-prodotti");
                return;
            }

            // MODIFICA QUI: Carichiamo anche le categorie per il menu a tendina
            CategoriaDAO categoriaDAO = new CategoriaDAOImp();
            List<Categoria> categorie = categoriaDAO.doRetrieveAll();
            request.setAttribute("categorie", categorie);

            request.setAttribute("prodotto", prodotto);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/edit_product.jsp");
            dispatcher.forward(request, response);

        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore nel recupero dati");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ProdottoDAO prodottoDAO = new ProdottoDAOImp();

            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String descrizione = request.getParameter("descrizione");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            // Ora questo valore arriverà dal menu a tendina, quindi sarà sempre corretto
            int categoriaId = Integer.parseInt(request.getParameter("categoria"));

            // Gestione Foto (come prima)
            Part filePart = request.getPart("foto");
            byte[] fotoBytes = null;

            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream inputStream = filePart.getInputStream()) {
                    fotoBytes = inputStream.readAllBytes();
                }
            } else {
                Prodotto vecchioProdotto = prodottoDAO.doRetrieveByKey(id);
                if (vecchioProdotto != null) {
                    fotoBytes = vecchioProdotto.getFoto();
                }
            }

            Prodotto prodotto = new Prodotto();
            prodotto.setId(id);
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setQuantita(quantita);
            prodotto.setCategoriaId(categoriaId);
            prodotto.setFoto(fotoBytes);

            String specifiche = request.getParameter("specifiche");
            prodotto.setSpecifiche(specifiche);

            prodottoDAO.doUpdate(prodotto);

            response.sendRedirect("gestione-prodotti?success=true");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore durante l'aggiornamento: " + e.getMessage());
        }
    }
}