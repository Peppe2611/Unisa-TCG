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

@WebServlet("/update-profile")
public class UpdateProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Utente utente = (Utente) session.getAttribute("utente");

        // Sicurezza: se non è loggato, via al login
        if (utente == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Recupera i dati dal form
        String nome = request.getParameter("nome");

        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");
        String indirizzo = request.getParameter("indirizzo");
        String cap = request.getParameter("cap");
        String password = request.getParameter("password");


        utente.setNome(nome);
        utente.setEmail(email);
        utente.setTelefono(telefono);
        utente.setIndirizzo(indirizzo);
        utente.setCap(cap);

        UtenteDAO dao = new UtenteDAOImp();

        try {
            // 1. Aggiorna i dati anagrafici
            dao.doUpdate(utente);


            if (password != null && !password.isEmpty()) {
                String hash = BCrypt.hashpw(password, BCrypt.gensalt());
                utente.setPasswordHash(hash);
                dao.updatePassword(utente);
            }

            // Aggiorna l'utente in sessione con i nuovi dati
            session.setAttribute("utente", utente);

            // Torna al profilo con successo
            response.sendRedirect("profile.jsp?success=true");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(500, "Errore durante l'aggiornamento del profilo");
        }
    }
}