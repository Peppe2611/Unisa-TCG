package it.unisatcg.control;

import it.unisatcg.model.Recensione;
import it.unisatcg.model.dao.RecensioneDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

// ATTENZIONE: L'annotazione deve corrispondere al link nella dashboard
@WebServlet("/admin/gestione-recensioni")
public class AdminRecensioniServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RecensioneDAOImp dao = new RecensioneDAOImp();
        try {
            // Recupera la lista dal DB
            List<Recensione> recensioni = dao.doRetrieveAll();

            // La passa alla JSP
            request.setAttribute("recensioni", recensioni);

            // Apre la pagina
            request.getRequestDispatcher("/admin/gestione_recensioni.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Errore nel recupero recensioni");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Gestisce la cancellazione
        String idStr = request.getParameter("id");
        if(idStr != null){
            int id = Integer.parseInt(idStr);
            RecensioneDAOImp dao = new RecensioneDAOImp();
            try {
                dao.doDelete(id);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("gestione-recensioni");
    }
}