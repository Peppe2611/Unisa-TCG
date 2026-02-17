<%@ page import="it.unisatcg.model.Prodotto" %>
<%@ page import="it.unisatcg.model.Recensione" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
  List<Recensione> recensioni = (List<Recensione>) request.getAttribute("recensioni");
  NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
  Object utenteLoggato = session.getAttribute("utente");
  String nomeVenditore = (String) request.getAttribute("nomeVenditore");
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
    .review-text { color: #555; font-style: italic; }
    .review-form { margin-top: 30px; padding: 20px; border: 1px solid #eee; border-radius: 8px; background: #fff; }
    .review-form label { display: block; margin-top: 10px; font-weight: bold; }
    .review-form textarea { width: 100%; height: 80px; margin-top: 5px; padding: 10px; border: 1px solid #ccc; border-radius: 4px; resize: vertical; }
    .review-form select { width: 100%; padding: 8px; margin-top: 5px; }
  </style>
</head>
<body>
<%@ include file="common/header.jspf" %>

<main class="container">
  <div class="product-detail-layout" style="display: flex; gap: 40px; margin-top: 40px;">
    <div class="product-image" style="flex: 1;">
      <img src="ImageServlet?id=<%= prodotto.getId() %>" alt="<%= prodotto.getNome() %>" style="width: 100%; max-width: 500px; border-radius: 10px; box-shadow: 0 4px 10px rgba(0,0,0,0.1);">
    </div>

    <div class="product-info" style="flex: 1;">
      <h1 style="margin-top: 0; font-size: 2.5em; color: var(--dark-color);"><%= prodotto.getNome() %></h1>

      <% if (nomeVenditore != null) { %>
      <p style="color: #666; font-style: italic; margin-bottom: 15px;">
        Venduto da: <strong><%= nomeVenditore %></strong>
      </p>
      <% } %>

      <p class="price" style="font-size: 2em; color: var(--primary-color); font-weight: bold; margin-bottom: 20px;">
        <%= currencyFormatter.format(prodotto.getPrezzo()) %>
      </p>

      <div class="description" style="margin-bottom: 30px; line-height: 1.6; color: #444;">
        <h3>Descrizione</h3>
        <p><%= prodotto.getDescrizione() %></p>
      </div>

      <div class="specifiche" style="margin-bottom: 30px; padding: 15px; background: #f1f1f1; border-radius: 5px;">
        <strong>Specifiche:</strong> <%= (prodotto.getSpecifiche() != null) ? prodotto.getSpecifiche() : "Nessuna specifica disponibile" %>
      </div>

      <div class="stock-status" style="margin-bottom: 20px;">
        <% if (prodotto.getQuantita() > 0) { %>
        <span style="color: green; font-weight: bold;">● In magazzino (<%= prodotto.getQuantita() %> unità disponibili)</span>
        <% } else { %>
        <span style="color: red; font-weight: bold;">● Esaurito</span>
        <% } %>
      </div>

      <form action="gestione-carrello" method="post" style="display: flex; gap: 10px;">
        <input type="hidden" name="action" value="add">
        <input type="hidden" name="id" value="<%= prodotto.getId() %>">
        <input type="number" name="quantita" value="1" min="1" max="<%= prodotto.getQuantita() %>" style="width: 60px; padding: 8px;" <%= (prodotto.getQuantita() == 0) ? "disabled" : "" %>>
        <button type="submit" class="btn btn-primary" style="padding: 10px 30px; font-size: 1.1em;" <%= (prodotto.getQuantita() == 0) ? "disabled" : "" %>>
          Aggiungi al Carrello
        </button>
      </form>
    </div>
  </div>

  <div class="reviews-section" style="margin-top: 60px; max-width: 800px;">
    <h3>Recensioni Clienti</h3>

    <div class="reviews-list">
      <% if (recensioni != null && !recensioni.isEmpty()) {
        for (Recensione r : recensioni) { %>
      <div class="single-review">
        <div class="review-header">
          <span><%= r.getNomeUtente() %></span>
          <span style="color: #f1c40f;">
                        <% for(int i=0; i<r.getVoto(); i++) { %>★<% } %>
            <% for(int i=r.getVoto(); i<5; i++) { %>☆<% } %>
                    </span>
        </div>
        <div class="review-text">"<%= r.getTesto() %>"</div>
      </div>
      <% }
      } else { %>
      <p>Nessuna recensione per questo prodotto. Sii il primo a scriverne una!</p>
      <% } %>
    </div>

    <% if (utenteLoggato != null) { %>
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
</main>

<%@ include file="common/footer.jspf" %>
</body>
</html>