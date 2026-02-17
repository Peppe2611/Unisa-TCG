package it.unisatcg.control;

import it.unisatcg.model.Prodotto;
import it.unisatcg.model.Categoria;
import it.unisatcg.model.dao.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/new-product")
@MultipartConfig(maxFileSize = 16177215) // Supporto file fino a ~16MB
public class NewProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Carichiamo la lista categorie per il menu a tendina
            CategoriaDAO catDAO = new CategoriaDAOImp();
            List<Categoria> lista = catDAO.doRetrieveAll();
            request.setAttribute("listaCategorie", lista);
            request.getRequestDispatcher("/admin/new_product.jsp").forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Errore caricamento categorie", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        int quantita = Integer.parseInt(request.getParameter("quantita"));

        // MODIFICA: Recuperiamo l'ID dal menu a tendina (name="categoria")
        int categoriaId = Integer.parseInt(request.getParameter("categoria"));

        // Gestione Foto
        Part filePart = request.getPart("foto");
        byte[] fotoBytes = null;

        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                fotoBytes = inputStream.readAllBytes();
            }
        }

        Prodotto prodotto = new Prodotto();
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setPrezzo(prezzo);
        prodotto.setQuantita(quantita);
        prodotto.setCategoriaId(categoriaId);
        prodotto.setFoto(fotoBytes);

        // Imposta un venditore di default (es. 1 admin)
        prodotto.setVenditoreId(1);

        ProdottoDAO prodottoDAO = new ProdottoDAOImp();
        try {
            prodottoDAO.doSave(prodotto);
            // Reindirizza alla lista prodotti dopo il salvataggio
            response.sendRedirect(request.getContextPath() + "/admin/gestione-prodotti");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore salvataggio prodotto: " + e.getMessage());
        }
    }
}