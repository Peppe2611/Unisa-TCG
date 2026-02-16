import it.unisatcg.model.Utente;
import it.unisatcg.model.dao.UtenteDAOImp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/updateProfilo")
public class UpdateProfiloServlet extends HttpServlet {

    private static final Logger logger =
            Logger.getLogger(UpdateProfiloServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Utente utenteSession = (Utente) session.getAttribute("utente");

        if (utenteSession == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            utenteSession.setNome(request.getParameter("nome"));
            utenteSession.setEmail(request.getParameter("email"));
            utenteSession.setTelefono(request.getParameter("telefono"));
            utenteSession.setIndirizzo(request.getParameter("indirizzo"));
            utenteSession.setCap(request.getParameter("cap"));

            UtenteDAOImp utenteDAO = new UtenteDAOImp();
            utenteDAO.doUpdate(utenteSession);

            session.setAttribute("utente", utenteSession);

            response.sendRedirect("profile.jsp?success=updated");

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Errore durante aggiornamento profilo", e);
            response.sendRedirect("profile.jsp?error=db_error");
        }
    }
}
