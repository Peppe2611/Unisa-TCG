<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unisatcg.model.Utente" %>
<%
    Utente utente = (Utente) session.getAttribute("utente");
    if (utente == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Modifica Profilo - UnisaTCG</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<%@ include file="/common/header.jspf" %>

<main class="container">
    <div class="auth-form-container">
        <div class="auth-form-box" style="max-width: 600px;">
            <h1 class="page-title">Modifica Profilo</h1>

            <form action="${pageContext.request.contextPath}/update-profile" method="post">

                <div class="form-group">
                    <label>Nome</label>
                    <input type="text" name="nome" value="<%= utente.getNome() %>" required class="form-control">
                </div>

                <%-- CAMPO COGNOME RIMOSSO --%>

                <div class="form-group">
                    <label>Email</label>
                    <input type="email" name="email" value="<%= utente.getEmail() %>" required class="form-control">
                </div>

                <div class="form-group">
                    <label>Telefono</label>
                    <input type="text" name="telefono" value="<%= utente.getTelefono() != null ? utente.getTelefono() : "" %>" class="form-control">
                </div>

                <div class="form-group">
                    <label>Indirizzo</label>
                    <input type="text" name="indirizzo" value="<%= utente.getIndirizzo() != null ? utente.getIndirizzo() : "" %>" class="form-control">
                </div>

                <div class="form-group">
                    <label>CAP</label>
                    <input type="text" name="cap" value="<%= utente.getCap() != null ? utente.getCap() : "" %>" class="form-control">
                </div>

                <hr style="margin: 20px 0; border: 0; border-top: 1px solid #eee;">

                <div class="form-group">
                    <label>Nuova Password (lascia vuoto se non vuoi cambiarla)</label>
                    <input type="password" name="password" class="form-control">
                </div>

                <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 10px;">Salva Modifiche</button>

                <div style="text-align: center; margin-top: 15px;">
                    <a href="profile.jsp" style="color: #666;">Annulla</a>
                </div>
            </form>
        </div>
    </div>
</main>

<%@ include file="/common/footer.jspf" %>
</body>
</html>