<%@ page import="it.unisatcg.model.Prodotto" %>
<%@ page import="it.unisatcg.model.Categoria" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>Tutti i Prodotti - UnisaTCG</title>
</head>
<body>
<%@include file="common/header.jspf"%>
<main class="container">
  <h2 class="page-title">Tutti i Prodotti</h2>

  <%-- Filtri Categoria --%>
  <div class="filter-container" style="margin-bottom: 30px; text-align: center;">
    <form action="visualizza-prodotti" method="get" id="filter-form">
      <label for="category-select" style="font-weight: bold; margin-right: 10px;">Categoria:</label>
      <select name="categoriaId" id="category-select" onchange="this.form.submit()" style="padding: 8px; border-radius: 5px;">
        <option value="">Tutte le Categorie</option>
        <%
          List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
          Integer selectedCat = (Integer) request.getAttribute("categoriaSelezionata");
          if(categorie != null) {
            for (Categoria cat : categorie) {
        %>
        <option value="<%= cat.getId() %>" <%= (selectedCat != null && selectedCat == cat.getId()) ? "selected" : "" %>>
          <%= cat.getNome() %>
        </option>
        <% } } %>
      </select>
    </form>
  </div>

  <div class="product-grid">
    <%
      List<Prodotto> products = (List<Prodotto>) request.getAttribute("products");
      if (products == null || products.isEmpty()) {
    %>
    <p style="text-align: center;">Nessun prodotto trovato.</p>
    <% } else {
      for (Prodotto prodotto : products) { %>
    <div class="product-card">
      <a href="dettaglio-prodotto?id=<%= prodotto.getId() %>">
        <img src="${pageContext.request.contextPath}/ImageServlet?id=<%= prodotto.getId() %>" alt="<%= prodotto.getNome() %>">
        <h3><%= prodotto.getNome() %></h3>
      </a>
      <p class="price">€ <%= String.format("%.2f", prodotto.getPrezzo()) %></p>

      <% if (prodotto.getQuantita() > 0) { %>
      <%-- FIX: Action verso la servlet centralizzata con parametro 'azione' --%>
      <form action="gestione-carrello" method="post">
        <input type="hidden" name="azione" value="aggiungi">
        <input type="hidden" name="prodottoId" value="<%= prodotto.getId() %>">
        <input type="hidden" name="quantita" value="1">
        <button type="submit" class="btn btn-primary">Aggiungi al Carrello</button>
      </form>
      <% } else { %>
      <button type="button" class="btn btn-primary" disabled style="background-color: grey;">Esaurito</button>
      <% } %>
    </div>
    <% } } %>
  </div>
</main>
<%@include file="common/footer.jspf"%>
</body>
</html>