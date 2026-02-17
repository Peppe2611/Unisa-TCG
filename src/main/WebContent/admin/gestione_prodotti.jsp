<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisatcg.model.Prodotto" %>
<%
  // Recupero la lista dalla request
  List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("products");
%>
<%@ include file="../common/header.jspf" %>

<main class="container">
  <h1 class="page-title">Gestione Catalogo</h1>

  <%-- Tasto Aggiungi --%>
  <div style="text-align: right; margin-bottom: 20px;">
    <a href="${pageContext.request.contextPath}/admin/new-product" class="btn btn-success" style="padding: 10px 20px; text-decoration: none;">
      + Aggiungi Prodotto
    </a>
  </div>

  <table class="management-table" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
    <thead>
    <%-- FIX COLORE TESTO: Aggiunto color: #333 per rendere visibile il testo su sfondo chiaro --%>
    <tr style="background-color: #f8f9fa; text-align: left; color: #333333;">
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6; color: #333333;">Foto</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6; color: #333333;">Nome</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6; color: #333333;">Prezzo</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6; color: #333333;">Qtà</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6; color: #333333;">Azioni</th>
    </tr>
    </thead>
    <tbody>
    <% if (prodotti != null && !prodotti.isEmpty()) {
      for (Prodotto p : prodotti) { %>
    <tr style="border-bottom: 1px solid #dee2e6;">
      <td style="padding: 12px;">
        <%-- Immagine: gestisce il caso null --%>
        <% if(p.getFoto() != null && p.getFoto().length > 0) { %>
        <img src="${pageContext.request.contextPath}/ImageServlet?id=<%= p.getId() %>"
             alt="<%= p.getNome() %>"
             style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;">
        <% } else { %>
        <span style="color: #999; font-size: 0.8em;">No Foto</span>
        <% } %>
      </td>
      <td style="padding: 12px;"><%= p.getNome() %></td>
      <td style="padding: 12px;"><%= String.format("%.2f", p.getPrezzo()) %> €</td>
      <td style="padding: 12px;"><%= p.getQuantita() %></td>
      <td style="padding: 12px;">
        <%-- Link di modifica corretto --%>
        <a href="${pageContext.request.contextPath}/admin/edit-product?id=<%= p.getId() %>"
           class="btn btn-primary"
           style="padding: 5px 10px; font-size: 0.9em; text-decoration: none;">
          Modifica
        </a>
      </td>
    </tr>
    <% } } else { %>
    <tr>
      <td colspan="5" style="text-align: center; padding: 20px;">Nessun prodotto trovato nel database.</td>
    </tr>
    <% } %>
    </tbody>
  </table>
</main>

<%@ include file="../common/footer.jspf" %>