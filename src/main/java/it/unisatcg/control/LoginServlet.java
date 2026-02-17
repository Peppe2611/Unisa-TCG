package it.unisatcg.control;

import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.UtenteDAO;
import it.unisatcg.model.dao.UtenteDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.mindrot.jbcrypt.BCrypt;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UtenteDAO utenteDAO = new UtenteDAOImp();

        try {
            Utente utente = utenteDAO.doRetrieveByEmail(email);

            // 1. Verifica se l'utente esiste e se la password è corretta
            if (utente != null && BCrypt.checkpw(password, utente.getPasswordHash())) {

                // 2. CONTROLLO BAN (Aggiunto qui)
                if (utente.isBanned()) {
                    request.setAttribute("error", "Il tuo account è stato sospeso. Contatta l'amministrazione.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }

                // 3. Se non è bannato, procedi col login
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);

                // Se è admin va alla dashboard, altrimenti alla home
                if (utente.isAdmin()) {
                    response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
                } else {
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                }

            } else {
                // Credenziali errate
                request.setAttribute("error", "Email o password errati.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Errore database");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }
}