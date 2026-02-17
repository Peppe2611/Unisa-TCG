<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisatcg.model.Prodotto" %>
<%
  List<Prodotto> prodotti = (List<Prodotto>) request.getAttribute("products");
  String deleted = request.getParameter("deleted");
%>
<%@ include file="../common/header.jspf" %>

<main class="container">
  <h1 class="page-title">Gestione Catalogo</h1>

  <%-- Messaggio di conferma eliminazione --%>
  <% if ("true".equals(deleted)) { %>
  <div style="background-color: #d4edda; color: #155724; padding: 10px; border-radius: 4px; margin-bottom: 20px; border: 1px solid #c3e6cb;">
    Prodotto eliminato con successo.
  </div>
  <% } %>

  <div style="text-align: right; margin-bottom: 20px;">
    <a href="${pageContext.request.contextPath}/admin/new-product" class="btn btn-success" style="padding: 10px 20px; text-decoration: none;">
      + Aggiungi Prodotto
    </a>
  </div>

  <table class="management-table" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
    <thead>
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
      <td style="padding: 12px; display: flex; gap: 10px; align-items: center;">
        <%-- TASTO MODIFICA --%>
        <a href="${pageContext.request.contextPath}/admin/edit-product?id=<%= p.getId() %>"
           class="btn btn-primary"
           style="padding: 5px 10px; font-size: 0.9em; text-decoration: none; background-color: #007bff; border: none;">
          Modifica
        </a>

        <%-- TASTO ELIMINA (Form per inviare POST) --%>
        <form action="${pageContext.request.contextPath}/admin/gestione-prodotti" method="post" style="margin: 0;">
          <input type="hidden" name="action" value="delete">
          <input type="hidden" name="id" value="<%= p.getId() %>">
          <button type="submit"
                  onclick="return confirm('Sei sicuro di voler eliminare definitivamente questo prodotto?');"
                  style="background-color: #dc3545; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer; font-size: 0.9em;">
            Elimina
          </button>
        </form>
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