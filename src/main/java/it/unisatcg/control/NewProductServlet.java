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

@WebServlet("/New_Product")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // Supporto BLOB fino a 5MB [cite: 178]
public class NewProductServlet extends HttpServlet {

    // Carica le categorie prima di mostrare la pagina [cite: 259]
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
        try {
            Prodotto p = new Prodotto();
            p.setNome(request.getParameter("nome"));
            p.setDescrizione(request.getParameter("descrizione"));
            p.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
            p.setQuantita(Integer.parseInt(request.getParameter("quantita")));
            p.setCategoriaId(Integer.parseInt(request.getParameter("categoriaId")));
            p.setDisponibile(Boolean.parseBoolean(request.getParameter("disponibile")));
            p.setSpecifiche(request.getParameter("specifiche"));

            // Gestione foto BLOB [cite: 273, 276]
            Part filePart = request.getPart("foto");
            if (filePart != null && filePart.getSize() > 0) {
                try (InputStream is = filePart.getInputStream()) {
                    p.setFoto(is.readAllBytes());
                }
            }

            new ProdottoDAOImp().doSave(p);
            response.sendRedirect("admin/manage-products");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errore", "Errore: " + e.getMessage());
            doGet(request, response); // Ricarica le categorie in caso di errore
        }
    }
}