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
  <div class="filter-container" style="margin-bottom: 30px; text-align: center;">
    <form action="visualizza-prodotti" method="get" id="filter-form">
      <label for="category-select" style="font-weight: bold; margin-right: 10px;">Categoria:</label>
      <select name="categoriaId" id="category-select" onchange="this.form.submit()" style="padding: 8px; border-radius: 5px;">
        <option value="">Tutte le Categorie</option>
        <%
          List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");
          Integer selectedCat = (Integer) request.getAttribute("categoriaSelezionata");
          for (Categoria cat : categorie) {
        %>
        <option value="<%= cat.getId() %>" <%= (selectedCat != null && selectedCat == cat.getId()) ? "selected" : "" %>>
          <%= cat.getNome() %>
        </option>
        <% } %>
      </select>

      <label for="disp-select" style="font-weight: bold; margin-right: 10px;">Disponibilità:</label>
      <select name="disponibilita" id="disp-select" onchange="this.form.submit()" style="padding: 8px; border-radius: 5px;">
        <%
          String currentDisp = (String) request.getAttribute("disponibilitasel");
          String[] options = {"Tutto", "Disponibile", "Esaurito"};
          for (String opt : options) {
        %>
        <option value="<%= opt %>" <%= opt.equals(currentDisp) ? "selected" : "" %>><%= opt %></option>
        <% } %>
      </select>
    </form>
  </div>

  <%
    @SuppressWarnings("unchecked")
    List<Prodotto> products = (List<Prodotto>) request.getAttribute("products");

    if (products == null || products.isEmpty()) {
  %>
  <p style="text-align: center;">Nessun prodotto trovato...</p>
  <%
  } else {
  %>
  <div class="product-grid">
    <%
      for (Prodotto prodotto : products) {
    %>
    <div class="product-card">
      <a href="dettaglio-prodotto?id=<%= prodotto.getId() %>">
        <img src="${pageContext.request.contextPath}/ImageServlet?id=<%= prodotto.getId() %>" alt="Immagine di <%= prodotto.getNome() %>">
        <h3><%= prodotto.getNome() %></h3>
      </a>
      <p class="price">€ <%= String.format("%.2f", prodotto.getPrezzo()) %></p>
      <p class="product-description"><%= prodotto.getDescrizione() %></p>
      <% if (prodotto.getQuantita() > 0) { %>
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
    <%
      }
    %>
  </div>
  <%
    }
  %>
</main>
<script>
  document.getElementById('category-select').addEventListener('change', function() {
    document.getElementById('category-filter-form').submit();
  });
</script>
<%@include file="common/footer.jspf"%>
</body>
</html>