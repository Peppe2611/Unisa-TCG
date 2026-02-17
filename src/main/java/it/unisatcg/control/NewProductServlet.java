package it.unisatcg.control;

import it.unisatcg.model.Categoria;
import it.unisatcg.model.Prodotto;
import it.unisatcg.model.dao.CategoriaDAO;
import it.unisatcg.model.dao.CategoriaDAOImp;
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
import java.util.List;

@WebServlet("/admin/new-product")
@MultipartConfig(maxFileSize = 16177215) // Max upload size ~16MB
public class NewProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
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

        // Gestione errori di parsing per numeri
        double prezzo = 0;
        int quantita = 0;
        int categoriaId = 0;
        try {
            prezzo = Double.parseDouble(request.getParameter("prezzo"));
            quantita = Integer.parseInt(request.getParameter("quantita"));
            categoriaId = Integer.parseInt(request.getParameter("categoriaId")); // Assicurati che nella JSP il select abbia name="categoriaId"
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Puoi gestire l'errore o reindirizzare
        }

        String specifiche = request.getParameter("specifiche");
        String disponibileParam = request.getParameter("disponibile");
        boolean isDisponibile = "true".equals(disponibileParam);

        // --- GESTIONE FILE IMMAGINE ---
        // IMPORTANTE: Nella JSP il campo deve chiamarsi name="foto"
        Part filePart = request.getPart("foto");
        byte[] fotoBytes = null;

        if (filePart != null && filePart.getSize() > 0) {
            try (InputStream inputStream = filePart.getInputStream()) {
                fotoBytes = inputStream.readAllBytes();
            }
        }
        // ------------------------------

        Prodotto prodotto = new Prodotto();
        prodotto.setNome(nome);
        prodotto.setDescrizione(descrizione);
        prodotto.setPrezzo(prezzo);
        prodotto.setQuantita(quantita);
        prodotto.setCategoriaId(categoriaId);
        prodotto.setSpecifiche(specifiche);
        prodotto.setDisponibile(isDisponibile);
        prodotto.setFoto(fotoBytes); // Setta i byte dell'immagine

        // Imposta un venditore di default (es. 1) o prendilo dalla sessione
        prodotto.setVenditoreId(1);

        ProdottoDAO prodottoDAO = new ProdottoDAOImp();
        try {
            prodottoDAO.doSave(prodotto);
            response.sendRedirect(request.getContextPath() + "/admin/gestione-prodotti"); // Reindirizza alla lista corretta
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore salvataggio prodotto: " + e.getMessage());
        }
    }
}