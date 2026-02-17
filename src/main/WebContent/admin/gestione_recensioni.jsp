<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisatcg.model.Recensione" %>
<%
  List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");
%>
<%@ include file="../common/header.jspf" %>

<main class="container">
  <h1 class="page-title">Moderazione Recensioni</h1>

  <% if (recensioni == null || recensioni.isEmpty()) { %>
  <div style="text-align: center; margin-top: 50px;">
    <h3>Nessuna recensione trovata.</h3>
    <p>Assicurati che ci siano dati nella tabella <code>recensione</code> del database.</p>
  </div>
  <% } else { %>

  <table class="management-table" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
    <thead>
    <tr style="background-color: #f8f9fa; color: #333;">
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">ID</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Prod.</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Utente</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Voto</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Testo</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Data</th>
      <th style="padding: 12px; border-bottom: 2px solid #dee2e6;">Azioni</th>
    </tr>
    </thead>
    <tbody>
    <% for(Recensione r : recensioni) { %>
    <tr style="border-bottom: 1px solid #dee2e6;">
      <td style="padding: 12px;"><%= r.getId() %></td>
      <td style="padding: 12px;"><%= r.getProdottoId() %></td>
      <td style="padding: 12px;">
        <%= r.getNomeUtente() != null ? r.getNomeUtente() : ("ID: " + r.getUtenteId()) %>
      </td>
      <%-- CORRETTO: usa getVoto() --%>
      <td style="padding: 12px; font-weight: bold; color: #f39c12;"><%= r.getVoto() %>/5</td>
      <td style="padding: 12px; max-width: 300px; font-style: italic;">"<%= r.getTesto() %>"</td>
      <td style="padding: 12px;"><%= r.getDataRecensione() %></td>
      <td style="padding: 12px;">
        <form action="${pageContext.request.contextPath}/admin/gestione-recensioni" method="post" onsubmit="return confirm('Cancellare questa recensione?');">
          <input type="hidden" name="id" value="<%= r.getId() %>">
          <button type="submit" class="btn btn-danger" style="background-color: #dc3545; color: white; padding: 5px 10px; border: none; border-radius: 4px; cursor: pointer;">Elimina</button>
        </form>
      </td>
    </tr>
    <% } %>
    </tbody>
  </table>
  <% } %>
</main>
<%@ include file="../common/footer.jspf" %>