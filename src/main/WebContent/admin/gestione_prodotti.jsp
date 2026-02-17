<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List, it.unisatcg.model.Prodotto" %>
<% List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("products"); %>
<!DOCTYPE html>
<html>
<head>
  <title>Gestione Prodotti - UnisaTCG</title>
</head>
<body>
<%@ include file="../common/header.jspf" %>
<main class="container">
  <h1 class="page-title">Gestione Catalogo</h1>
  <a href="new_product.jsp" class="btn btn-success" style="margin-bottom: 20px;">+ Aggiungi Prodotto</a>

  <table class="management-table">
    <thead>
    <tr>
      <th>Foto</th>
      <th>Nome</th>
      <th>Prezzo</th>
      <th>Qtà</th>
      <th>Azioni</th>
    </tr>
    </thead>
    <tbody>
    <% if (prodotti != null) { for (Prodotto p : prodotti) { %>
    <tr>
      <td>
        <%-- Richiamo dinamico dell'immagine BLOB --%>
        <img src="${pageContext.request.contextPath}/ImageServlet?id=<%= p.getId() %>"
             alt="Prodotto" style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;">
      </td>
      <td><%= p.getNome() %></td>
      <td><%= String.format("%.2f", p.getPrezzo()) %> €</td>
      <td><%= p.getQuantita() %></td>
      <td>
        <a href="edit-product?id=<%= p.getId() %>" class="btn btn-primary">Modifica</a>
      </td>
    </tr>
    <% } } %>
    </tbody>
  </table>
</main>
<%@ include file="../common/footer.jspf" %>
</body>
</html>