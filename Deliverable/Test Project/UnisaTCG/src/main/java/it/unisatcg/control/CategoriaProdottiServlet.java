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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/visualizza-prodotti")
public class CategoriaProdottiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ProdottoDAO prodottoDAO = new ProdottoDAOImp();
        CategoriaDAO categoriaDAO = new CategoriaDAOImp();

        List<Prodotto> products = List.of();
        List<Categoria> categorie;

        List<String> disponibilita = new ArrayList<>(List.of());
        disponibilita.add("Tutto");
        disponibilita.add("Disponibile");
        disponibilita.add("Esaurito");

        int categoriaSelezionataId = -1;
        // Recupero parametri
        String categoriaIdStr = request.getParameter("categoriaId");
        String disponibilitasel = request.getParameter("disponibilita");

        // Valore di default se il parametro è assente
        if (disponibilitasel == null || disponibilitasel.isEmpty()) {
            disponibilitasel = "Tutto";
        }

        try {
            categorie = categoriaDAO.doRetrieveAll();

            if (categoriaIdStr != null && !categoriaIdStr.trim().isEmpty()) {
                int categoriaId = Integer.parseInt(categoriaIdStr);
                products = prodottoDAO.doRetrieveByCategoriaDisp(categoriaId, disponibilitasel);
                categoriaSelezionataId = categoriaId;
            } else {
                // Filtra tutti i prodotti in base alla disponibilità
                products = prodottoDAO.doRetrieveAllDisp(disponibilitasel);
            }
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            throw new ServletException("Errore database", e);
        }

        // Passaggio attributi alla JSP
        request.setAttribute("products", products);
        request.setAttribute("categorie", categorie);
        request.setAttribute("categoriaSelezionata", categoriaSelezionataId);
        request.setAttribute("disponibilitasel", disponibilitasel); // Essenziale per la JSP

        RequestDispatcher dispatcher = request.getRequestDispatcher("/products.jsp");
        dispatcher.forward(request, response);
    }
}