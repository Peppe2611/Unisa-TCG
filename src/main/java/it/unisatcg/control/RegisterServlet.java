package it.unisatcg.control;

import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.UtenteDAO;
import it.unisatcg.model.dao.UtenteDAOImp;
import org.mindrot.jbcrypt.BCrypt;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UtenteDAO utenteDAO = new UtenteDAOImp();
        String email = request.getParameter("email");
        String telefono = request.getParameter("telefono");

        try {
            // Verifica unicità (Requisiti RAD UC6)
            if (utenteDAO.doRetrieveByEmail(email) != null) {
                response.sendRedirect("register.jsp?error=email_exists");
                return;
            }

            if (utenteDAO.doRetrieveByTelefono(telefono) != null) {
                response.sendRedirect("register.jsp?error=telefono_exists");
                return;
            }

            String nome = request.getParameter("nome");
            String password = request.getParameter("password");
            String indirizzo = request.getParameter("indirizzo");
            String cap = request.getParameter("cap");

            // Hashing sicuro della password
            String salt = BCrypt.gensalt(12);
            String passwordHash = BCrypt.hashpw(password, salt);

            Utente utente = new Utente();
            utente.setNome(nome);
            utente.setEmail(email);
            utente.setPasswordHash(passwordHash);
            utente.setTelefono(telefono);
            utente.setIndirizzo(indirizzo);
            utente.setCap(cap);
            utente.setAdmin(false); // Default: Acquirente

            utenteDAO.doSave(utente);
            response.sendRedirect("login.jsp?registration=success");

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=db_error");
        }
    }
}