<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>Accedi - UnisaTCG</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<%@ include file="common/header.jspf" %>

<main class="container">
  <div class="auth-form-container">
    <div class="auth-form-box">
      <h1 class="page-title">Accedi</h1>

      <%-- BLOCCO PER MOSTRARE GLI ERRORI (Login errato / Ban) --%>
      <%
        String error = (String) request.getAttribute("error");
        if (error != null && !error.isEmpty()) {
      %>
      <div style="background-color: #f8d7da; color: #721c24; padding: 10px; border: 1px solid #f5c6cb; border-radius: 4px; margin-bottom: 15px; text-align: center;">
        <%= error %>
      </div>
      <% } %>

      <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="form-group">
          <label for="email">Email</label>
          <input type="email" id="email" name="email" placeholder="latuamail@esempio.com" required>
        </div>

        <div class="form-group">
          <label for="password">Password</label>
          <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="btn btn-primary" style="width: 100%;">Accedi</button>

        <div class="auth-links">
          <p>Non hai un account? <a href="${pageContext.request.contextPath}/register.jsp">Registrati</a></p>
        </div>
      </form>
    </div>
  </div>
</main>

<%@ include file="common/footer.jspf" %>
</body>
</html>