<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisatcg.model.Utente" %>
<% List<Utente> utenti = (List<Utente>) request.getAttribute("utenti"); %>
<%@ include file="../common/header.jspf" %>

<main class="container">
  <h1 class="page-title">Gestione Utenti</h1>
  <table class="management-table" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
    <thead>
    <tr style="background-color: #f8f9fa; color: #333;">
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">ID</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Nome</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Email</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Ruolo</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Stato</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Azioni</th>
    </tr>
    </thead>
    <tbody>
    <% if(utenti != null) { for(Utente u : utenti) { %>
    <tr style="border-bottom: 1px solid #dee2e6;">
      <td style="padding: 12px;"><%= u.getId() %></td>

      <td style="padding: 12px;"><%= u.getNome() %></td>
      <td style="padding: 12px;"><%= u.getEmail() %></td>
      <td style="padding: 12px;"><%= u.isAdmin() ? "<strong>Admin</strong>" : "Utente" %></td>

      <td style="padding: 12px; color: <%= u.isBanned() ? "red" : "green" %>;">
        <%= u.isBanned() ? "BANNATO" : "Attivo" %>
      </td>
      <td style="padding: 12px;">
        <% if(!u.isAdmin()) { %>
        <form action="gestione-utenti" method="post" style="display:inline;">
          <input type="hidden" name="userId" value="<%= u.getId() %>">
          <% if(u.isBanned()) { %>
          <input type="hidden" name="action" value="unban">
          <button type="submit" class="btn btn-success">Sblocca</button>
          <% } else { %>
          <input type="hidden" name="action" value="ban">
          <button type="submit" class="btn btn-danger" style="background-color: #dc3545; color: white;">BANNA</button>
          <% } %>
        </form>
        <% } %>
      </td>
    </tr>
    <% }} %>
    </tbody>
  </table>
</main>
<%@ include file="../common/footer.jspf" %>