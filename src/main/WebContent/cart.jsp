<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="it.unisatcg.model.Carrello, it.unisatcg.model.ArticoloCarrello, it.unisatcg.model.Prodotto" %>
<%
  Carrello carrello = (Carrello) session.getAttribute("carrello");
  if (carrello == null) carrello = new Carrello();
  String messaggio = (String) request.getAttribute("messaggioCarrello");
%>
<!DOCTYPE html>
<html lang="it">
<head>
  <title>Il Tuo Carrello - UnisaTCG</title>
</head>
<body>
<%@ include file="common/header.jspf" %>
<main class="container">
  <h1 class="page-title">Il Tuo Carrello</h1>
  <% if (messaggio != null) { %><div class="info-message" style="background:#fff3cd; padding:10px; border-radius:5px;"><%= messaggio %></div><% } %>

  <% if (carrello.getArticoli().isEmpty()) { %>
  <p style="text-align: center; margin-top:50px;">Il carrello è vuoto. <a href="visualizza-prodotti">Torna al catalogo</a></p>
  <% } else { %>
  <div class="cart-items-container" style="margin-top:20px;">
    <% for (ArticoloCarrello articolo : carrello.getArticoli()) {
      Prodotto p = articolo.getProdotto(); %>
    <div class="cart-item" style="border-bottom: 1px solid var(--border-color); padding: 20px 0; display: flex; justify-content: space-between; align-items: center;">
      <div>
        <h3><%= p.getNome() %></h3>
        <p>Prezzo unitario: €<%= String.format("%.2f", p.getPrezzo()) %></p>

        <form action="gestione-carrello" method="post" style="display:inline;">
          <input type="hidden" name="azione" value="aggiorna">
          <input type="hidden" name="prodottoId" value="<%= p.getId() %>">
          <label>Quantità: </label>
          <input type="number" name="quantita" value="<%= articolo.getQuantita() %>" min="1" max="<%= p.getQuantita() %>" onchange="this.form.submit()">
        </form>
      </div>
      <div style="text-align: right;">
        <p>Subtotale: <strong>€<%= String.format("%.2f", articolo.getPrezzoTotale()) %></strong></p>

        <form action="gestione-carrello" method="post">
          <input type="hidden" name="azione" value="rimuovi">
          <input type="hidden" name="prodottoId" value="<%= p.getId() %>">
          <button type="submit" class="btn btn-danger" style="background:#dc3545; color:white; border:none; padding:5px 10px; cursor:pointer;">Rimuovi</button>
        </form>
      </div>
    </div>
    <% } %>
  </div>
  <div class="cart-summary" style="margin-top: 40px; text-align: right; background: white; padding: 20px; border-radius: 8px;">
    <h2>Totale: €<%= String.format("%.2f", carrello.getTotaleComplessivo()) %></h2>
      <% if (session.getAttribute("utente") == null) { %>
    <%-- Se non è loggato, lo mandiamo alla login con l'errore specifico --%>
    <a href="login.jsp?error=checkout_required" class="btn btn-primary">Vai al Checkout</a>
      <% } else { %>
    <%-- Se è loggato, procede normalmente alla pagina del form --%>
    <a href="checkout.jsp" class="btn btn-primary">Vai al Checkout</a>
      <% } %>
  <% } %>
</main>
<%@ include file="common/footer.jspf" %>
</body>
</html>