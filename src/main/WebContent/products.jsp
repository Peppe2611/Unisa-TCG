<%@ page import="it.unisatcg.model.Prodotto" %>
<%@ page import="it.unisatcg.model.Categoria" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="common/header.jspf" %>

<main class="container">
  <div class="dashboard-container">
    <%-- LOGICA PER IL TITOLO DINAMICO --%>
    <%
      String searchQuery = (String) request.getAttribute("searchQuery");
      if (searchQuery != null && !searchQuery.trim().isEmpty()) {
    %>
    <h1 class="page-title">Risultati ricerca per: <span style="color: #f39c12;">"<%= searchQuery %>"</span></h1>
    <div style="text-align:center; margin-bottom: 20px;">
      <a href="visualizza-prodotti" class="btn btn-primary" style="background-color: #6c757d; font-size: 0.9rem;">Mostra tutti i prodotti</a>
    </div>
    <% } else { %>
    <h1 class="page-title">Catalogo Prodotti</h1>
    <p>Esplora la nostra collezione completa</p>
    <% } %>

    <%-- BARRA DEI FILTRI (STILE DASHBOARD) --%>
    <div class="checkout-box" style="margin-bottom: 40px; padding: 20px; background-color: #f8f9fa;">
      <form action="visualizza-prodotti" method="get" id="filter-form" style="display: flex; flex-wrap: wrap; justify-content: center; align-items: center; gap: 30px;">

        <%-- Filtro Categoria --%>
        <div style="display: flex; align-items: center; gap: 10px;">
          <label for="category-select" style="font-weight: bold;">Categoria:</label>
          <select name="categoriaId" id="category-select" onchange="this.form.submit()" style="padding: 10px; border-radius: 5px; border: 1px solid #ccc; min-width: 200px;">
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
        </div>

        <%-- Filtro Disponibilità --%>
        <div style="display: flex; align-items: center; gap: 10px; background: white; padding: 8px 15px; border-radius: 5px; border: 1px solid #ddd;">
          <%
            Boolean escludiEsauriti = (Boolean) request.getAttribute("escludiEsauriti");
            if (escludiEsauriti == null) escludiEsauriti = false;
          %>
          <input type="checkbox" name="soloDisponibili" id="soloDisponibili" value="true"
                 onchange="this.form.submit()" <%= escludiEsauriti ? "checked" : "" %>
                 style="width: 20px; height: 20px; cursor: pointer;">
          <label for="soloDisponibili" style="font-weight: bold; cursor: pointer; margin-bottom: 0;">Nascondi esauriti</label>
        </div>

        <%-- Hidden field per mantenere la ricerca attiva durante il filtraggio --%>
        <% if (searchQuery != null) { %>
        <input type="hidden" name="search" value="<%= searchQuery %>">
        <% } %>
      </form>
    </div>

    <%-- GRIGLIA PRODOTTI --%>
    <div class="product-grid" style="display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 25px;">
      <%
        List<Prodotto> products = (List<Prodotto>) request.getAttribute("products");
        if (products == null || products.isEmpty()) {
      %>
      <div style="grid-column: 1 / -1; text-align: center; padding: 50px;">
        <h3 style="color: #666;">Nessun prodotto trovato.</h3>
        <p>Prova a cambiare i filtri o <a href="visualizza-prodotti" style="color: #007bff;">resetta la ricerca</a>.</p>
      </div>
      <% } else {
        for (Prodotto prodotto : products) { %>
      <div class="product-card" style="border: 1px solid #eee; border-radius: 10px; padding: 15px; transition: transform 0.2s; background: white; text-align: center;">
        <a href="dettaglio-prodotto?id=<%= prodotto.getId() %>" style="text-decoration: none; color: inherit;">
          <div style="height: 200px; display: flex; align-items: center; justify-content: center; overflow: hidden; margin-bottom: 15px;">
            <img src="${pageContext.request.contextPath}/ImageServlet?id=<%= prodotto.getId() %>"
                 alt="<%= prodotto.getNome() %>"
                 style="max-width: 100%; max-height: 100%; object-fit: contain;">
          </div>
          <h3 style="font-size: 1.1rem; margin: 10px 0; min-height: 2.4em;"><%= prodotto.getNome() %></h3>
        </a>

        <p class="price" style="font-size: 1.3rem; font-weight: bold; color: #2c3e50; margin-bottom: 15px;">
          € <%= String.format("%.2f", prodotto.getPrezzo()) %>
        </p>

        <% if (prodotto.getQuantita() > 0) { %>
        <form action="gestione-carrello" method="post">
          <input type="hidden" name="azione" value="aggiungi">
          <input type="hidden" name="prodottoId" value="<%= prodotto.getId() %>">
          <input type="hidden" name="quantita" value="1">
          <button type="submit" class="btn btn-success" style="width: 100%;">Aggiungi al Carrello</button>
        </form>
        <% } else { %>
        <div style="padding: 10px; background-color: #ffeef0; border-radius: 5px;">
          <span class="out-of-stock" style="color: #dc3545; font-weight: bold; text-transform: uppercase; font-size: 0.85rem;">Esaurito</span>
        </div>
        <% } %>
      </div>
      <% } } %>
    </div>
  </div>
</main>

<%@ include file="common/footer.jspf" %>