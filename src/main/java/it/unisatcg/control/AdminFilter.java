package it.unisatcg.control;

import it.unisatcg.model.Utente;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filtro di sicurezza per proteggere l'area amministrativa.
 * Configurato per agire su tutto ciò che si trova sotto la cartella /admin/
 */
@WebFilter(filterName = "AdminFilter", urlPatterns = {"/admin/*"})
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Recuperiamo l'utente dalla sessione
        Utente utente = (session != null) ? (Utente) session.getAttribute("utente") : null;

        // LOGICA DI CONTROLLO:
        // 1. Se l'utente non è loggato (null)
        // 2. O se l'utente è loggato ma NON è un amministratore
        if (utente == null || !utente.isAdmin()) {
            // "Buttiamo fuori" l'utente reindirizzandolo al login con un messaggio di errore
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=unauthorized");
        } else {
            // Se è admin, lo lasciamo passare alla risorsa richiesta
            chain.doFilter(request, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}