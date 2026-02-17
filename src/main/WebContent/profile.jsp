<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unisatcg.model.Utente" %>
<%
    // Recupera l'utente dalla sessione
    Utente utente = (Utente) session.getAttribute("utente");

    // Se l'utente non è loggato, reindirizza al login
    if (utente == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="it">
<head>
    <title>Profilo Utente - UnisaTCG</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<%@ include file="common/header.jspf" %>

<main class="container">
    <div class="profile-container">
        <h1 class="page-title">Il mio Profilo</h1>

        <div class="profile-card">
            <div class="profile-info">
                <p><strong>Nome:</strong> <%= utente.getNome() %></p>

                <p><strong>Email:</strong> <%= utente.getEmail() %></p>
                <p><strong>Telefono:</strong> <%= utente.getTelefono() != null ? utente.getTelefono() : "Non inserito" %></p>
                <p><strong>Indirizzo:</strong> <%= utente.getIndirizzo() != null ? utente.getIndirizzo() : "Non inserito" %></p>
                <p><strong>CAP:</strong> <%= utente.getCap() != null ? utente.getCap() : "Non inserito" %></p>
            </div>

            <div class="profile-actions" style="margin-top: 20px; display: flex; gap: 10px; flex-wrap: wrap;">
                <%-- Pulsante Modifica Profilo --%>
                <a href="modifica_profilo.jsp" class="btn btn-primary">Modifica Profilo</a>

                <%-- NUOVO: Pulsante Storico Ordini (Verde) --%>
                <a href="${pageContext.request.contextPath}/storico-ordini" class="btn btn-primary" style="background-color: #28a745; border-color: #28a745;">
                    📦 I Miei Ordini
                </a>

                <%-- Pulsante Logout --%>
                <a href="${pageContext.request.contextPath}/LogoutServlet" class="btn btn-danger">Logout</a>
            </div>
        </div>
    </div>
</main>

<%@ include file="common/footer.jspf" %>

</body>
</html>