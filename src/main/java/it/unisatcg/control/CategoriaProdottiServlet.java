package it.unisatcg.control;

import it.unisatcg.model.Categoria;
import it.unisatcg.model.Prodotto;
import it.unisatcg.model.dao.CategoriaDAO;
import it.unisatcg.model.dao.CategoriaDAOImp;
import it.unisatcg.model.dao.ProdottoDAO;
import it.unisatcg.model.dao.ProdottoDAOImp;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/visualizza-prodotti")
public class CategoriaProdottiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProdottoDAO prodottoDAO = new ProdottoDAOImp();
        CategoriaDAO categoriaDAO = new CategoriaDAOImp();

        List<Prodotto> products;
        List<Categoria> categorie;

        // 1. Recupero parametri dalla JSP
        String categoriaIdStr = request.getParameter("categoriaId");
        String soloDisponibiliParam = request.getParameter("soloDisponibili");
        String searchQuery = request.getParameter("search"); // Per mantenere la ricerca se presente

        // Trasformiamo il parametro della checkbox in un booleano
        boolean escludiEsauriti = "true".equals(soloDisponibiliParam);
        int categoriaSelezionataId = -1;

        try {
            categorie = categoriaDAO.doRetrieveAll();

            // 2. Logica di recupero prodotti (Categoria o Tutti)
            if (categoriaIdStr != null && !categoriaIdStr.trim().isEmpty()) {
                categoriaSelezionataId = Integer.parseInt(categoriaIdStr);
                // Recuperiamo i prodotti della categoria (usiamo "Tutto" per far gestire il filtro dopo alla servlet)
                products = prodottoDAO.doRetrieveByCategoriaDisp(categoriaSelezionataId, "Tutto");
            } else {
                products = prodottoDAO.doRetrieveAllDisp("Tutto");
            }

            // 3. Applicazione del Filtro "Escludi Esauriti" (Logica Java)
            if (escludiEsauriti) {
                products = products.stream()
                        .filter(p -> p.getQuantita() > 0)
                        .collect(Collectors.toList());
            }

            // 4. (Opzionale) Ulteriore filtro se c'è una ricerca testuale attiva
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String queryLower = searchQuery.toLowerCase();
                products = products.stream()
                        .filter(p -> p.getNome().toLowerCase().contains(queryLower))
                        .collect(Collectors.toList());
            }

        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            throw new ServletException("Errore nel recupero dei prodotti", e);
        }

        // 5. Passaggio attributi alla JSP (nomi coerenti con la nuova JSP)
        request.setAttribute("products", products);
        request.setAttribute("categorie", categorie);
        request.setAttribute("categoriaSelezionata", categoriaSelezionataId);
        request.setAttribute("escludiEsauriti", escludiEsauriti);
        request.setAttribute("searchQuery", searchQuery);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/products.jsp");
        dispatcher.forward(request, response);
    }
}