<%@ page import="it.unisatcg.model.Prodotto" %>
<%@ page import="it.unisatcg.model.Recensione" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  // Recupero il prodotto
  Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");

  // Recupero la lista delle recensioni (passata dalla Servlet)
  List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");

  // Formattatore per il prezzo
  NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);

  // Controllo utente loggato per il form (RINOMINATO PER EVITARE CONFLITTO CON HEADER)
  Object utenteLoggato = session.getAttribute("utente");
%>
<!DOCTYPE html>
<html lang="it">
<head>
  <title><%= (prodotto != null) ? prodotto.getNome() : "Prodotto" %> - UnisaTCG</title>
  <link rel="stylesheet" href="styles/style.css">
  <style>
    .reviews-container { margin-top: 40px; border-top: 1px solid #ddd; padding-top: 20px; }
    .single-review { background: #f9f9f9; padding: 15px; margin-bottom: 15px; border-radius: 5px; }
    .review-header { display: flex; justify-content: space-between; margin-bottom: 5px; font-weight: bold; }
    .review-rating { color: #f39c12; }
    .review-form { background: #f0f0f0; padding: 20px; border-radius: 8px; }
    .review-form textarea { width: 100%; height: 80px; margin: 10px 0; }
  </style>
</head>
<body>
<%@ include file="common/header.jspf" %>

<main class="container product-detail-section">
  <% if (prodotto != null) { %>
  <div class="product-detail-grid">
    <div class="product-detail-image">
      <img src="${pageContext.request.contextPath}/ImageServlet?id=<%= prodotto.getId() %>" alt="<%= prodotto.getNome() %>">
    </div>

    <div class="product-info">
      <h2><%= prodotto.getNome() %></h2>
      <p class="price"><%= currencyFormatter.format(prodotto.getPrezzo()) %></p>

      <h3>Descrizione</h3>
      <p><%= prodotto.getDescrizione() %></p>

      <h3>Specifiche</h3>
      <p><%= prodotto.getSpecifiche() %></p>

      <% if (prodotto.getQuantita() > 0) { %>
      <p>Disponibilità: <%= prodotto.getQuantita() %> unità</p>
      <form action="gestione-carrello" method="post" class="add-to-cart-section">
        <input type="hidden" name="azione" value="aggiungi">
        <input type="hidden" name="prodottoId" value="<%= prodotto.getId() %>">
        <div class="form-group">
          <label for="quantita">Quantità:</label>
          <input type="number" id="quantita" name="quantita" value="1" min="1" max="<%= prodotto.getQuantita() %>" style="width: 80px; padding: 5px;">
        </div>
        <button type="submit" class="btn btn-primary">Aggiungi al Carrello</button>
      </form>
      <% } else { %>
      <p class="out-of-stock" style="color: red; font-weight: bold;">Prodotto Esaurito</p>
      <% } %>
    </div>
  </div>

  <div class="reviews-container">
    <h3>Recensioni Utenti</h3>

    <% if (recensioni != null && !recensioni.isEmpty()) {
      for (Recensione r : recensioni) { %>
    <div class="single-review">
      <div class="review-header">
        <span><%= r.getNomeUtente() %></span>
        <span class="review-rating">Voto: <%= r.getVoto() %>/5</span>
      </div>
      <p><%= r.getTesto() %></p>
      <small style="color: #666;">Data: <%= r.getDataRecensione() %></small>
    </div>
    <% }
    } else { %>
    <p>Nessuna recensione presente per questo prodotto.</p>
    <% } %>

    <hr>

    <% if (utenteLoggato != null) { %>  <%-- USIAMO LA VARIABILE RINOMINATA --%>
    <div class="review-form">
      <h4>Lascia la tua recensione</h4>
      <form action="RecensioneServlet" method="post">
        <input type="hidden" name="prodottoId" value="<%= prodotto.getId() %>">

        <label for="voto">Voto:</label>
        <select name="voto" id="voto" required>
          <option value="5">5 - Eccellente</option>
          <option value="4">4 - Molto Buono</option>
          <option value="3">3 - Medio</option>
          <option value="2">2 - Scarso</option>
          <option value="1">1 - Pessimo</option>
        </select>

        <br>

        <label for="testo">Commento:</label>
        <textarea name="testo" id="testo" placeholder="Scrivi qui la tua esperienza..." required></textarea>

        <button type="submit" class="btn btn-secondary">Invia Recensione</button>
      </form>
    </div>
    <% } else { %>
    <div class="login-prompt" style="background: #eef; padding: 15px; border-radius: 5px;">
      <p>Vuoi lasciare una recensione? <a href="login.jsp" style="font-weight: bold; text-decoration: underline;">Accedi al sito</a>.</p>
    </div>
    <% } %>
  </div>
  <% } else { %>
  <div class="error-message">
    <h2>Prodotto non trovato</h2>
    <p>Ci dispiace, il prodotto che stai cercando non è disponibile.</p>
    <a href="products.jsp" class="btn btn-secondary">Torna al catalogo</a>
  </div>
  <% } %>
</main>

<%@ include file="common/footer.jspf" %>
</body>
</html>