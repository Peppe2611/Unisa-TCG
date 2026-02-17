<%@ page import="it.unisatcg.model.Carrello" %>
<%@ page import="it.unisatcg.model.ArticoloCarrello" %>
<%@ page import="it.unisatcg.model.Utente" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Checkout - UnisaTCG</title>
</head>
<body>
<%@ include file="common/header.jspf" %>
<%
    Utente utente = (Utente) session.getAttribute("utente");
    Carrello carrello = (Carrello) session.getAttribute("carrello");
    if (utente == null || carrello == null || carrello.getArticoli().isEmpty()) {
        response.sendRedirect("cart.jsp");
        return;
    }
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
%>
<main class="container">
    <h1 class="page-title">Checkout</h1>
    <form id="checkoutForm" action="checkout" method="post" class="checkout-grid">
        <div class="checkout-details">
            <div class="checkout-box">
                <h3>Indirizzo di Spedizione</h3>
                <p>
                    <strong><%= utente.getNome() %></strong><br>
                    <%= utente.getIndirizzo() %><br>
                    <%= utente.getCap() != null ? utente.getCap() : "" %>
                </p>
                <a href="profile.jsp" style="font-size: 0.9em;">Modifica indirizzo nel profilo</a>
            </div>

            <div class="checkout-box">
                <h3>Metodo di Pagamento</h3>
                <div class="form-group">
                    <label>Titolare Carta</label>
                    <input type="text" id="card-holder" name="cardHolder" class="form-control" required>
                    <span id="cardHolderError" class="error-text"></span>
                </div>
                <div class="form-group">
                    <label for="card-number">Numero Carta</label>
                    <input type="text" id="card-number" name="cardNumber" class="form-control"
                           placeholder="0000 0000 0000 0000" maxlength="16" required>
                    <span id="cardNumberError" class="error-text"></span>
                </div>
                <div class="form-row" style="display: flex; gap: 20px;">
                    <div class="form-group" style="flex: 1;">
                        <label for="expiry-date">Scadenza (MM/AA)</label>
                        <input type="text" id="expiry-date" name="expiryDate" class="form-control"
                               placeholder="MM/AA" maxlength="5" required>
                        <span id="expiryError" class="error-text"></span>
                    </div>
                    <div class="form-group" style="flex: 1;">
                        <label for="cvv">CVV</label>
                        <input type="text" id="cvv" name="cvv" class="form-control"
                               placeholder="123" maxlength="3" required>
                        <span id="cvvError" class="error-text"></span>
                    </div>
                </div>
            </div>
        </div>

        <div class="order-summary-box">
            <h3>Riepilogo Ordine</h3>
            <% for (ArticoloCarrello articolo : carrello.getArticoli()) { %>
            <div style="display: flex; justify-content: space-between; margin-bottom: 10px;">
                <span><%= articolo.getQuantita() %>x <%= articolo.getProdotto().getNome() %></span>
                <span><%= currencyFormatter.format(articolo.getPrezzoTotale()) %></span>
            </div>
            <% } %>
            <hr>
            <div style="display: flex; justify-content: space-between; font-size: 1.3em;">
                <strong>Totale</strong>
                <strong><%= currencyFormatter.format(carrello.getTotaleComplessivo()) %></strong>
            </div>
            <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 20px;">Conferma Acquisto</button>
        </div>
    </form>
</main>
<%@ include file="common/footer.jspf" %>
<script src="${pageContext.request.contextPath}/scripts/CheckoutValidator.js"></script>
</body>
</html>