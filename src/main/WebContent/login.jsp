<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>Accedi - UnisaTCG</title>
</head>
<body>
<%@include file="common/header.jspf"%>
<main class="container auth-form-container">
  <div class="auth-form-box">
    <h2>Accedi</h2>

    <%-- Messaggio di Successo --%>
    <%
      if ("success".equals(request.getParameter("registration"))) {
    %>
    <div class="success-message">
      <strong>Registrazione completata!</strong> Ora puoi effettuare il login.
    </div>
    <%
      }
    %>

    <%-- Logica Errori Accorpata (Risolve la barra rossa vuota) --%>
    <%
      String error = request.getParameter("error");
      String errorMessage = null;

      if (error != null) {
        if ("invalid_credentials".equals(error)) {
          errorMessage = "<strong>Errore:</strong> Email o password non validi.";
        } else if ("db_error".equals(error)) {
          errorMessage = "<strong>Si è verificato un errore del server.</strong> Riprova più tardi.";
        } else if ("unauthorized".equals(error)) {
          errorMessage = "<strong>Accesso Negato:</strong> Devi essere un amministratore per visualizzare quella pagina.";
        }
      }

      // Stampiamo il div SOLO se abbiamo effettivamente un messaggio da mostrare
      if (errorMessage != null) {
    %>
    <div class="error-message">
      <%= errorMessage %>
    </div>
    <%
      }
    %>

    <form id="login-form" action="login" method="post" style="margin-top: 20px;">
      <div class="form-group">
        <label for="email">Email:</label>
        <input type="email" id="email" name="email" required>
      </div>
      <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
      </div>
      <button type="submit" class="btn btn-primary">Accedi</button>
      <p class="form-link">Non hai un account? <a href="register.jsp">Registrati</a></p>
    </form>
  </div>
</main>

<%@include file="common/footer.jspf"%>
</body>
</html>