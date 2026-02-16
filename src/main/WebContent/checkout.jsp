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
    Carrello carrello = (Carrello) session.getAttribute("carrello");
    if (utente == null || carrello == null || carrello.getArticoli().isEmpty()) {
        response.sendRedirect("cart.jsp");
        return;
    }
    NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.ITALY);
%>
<main class="container">
    <h1 class="page-title">Checkout</h1>
    <form action="checkout" method="post" class="checkout-grid">
        <div class="checkout-details">
            <div class="checkout-box" style="background: #f8f9fa; padding: 20px; border-radius: 8px; margin-bottom: 20px;">
                <h3>Indirizzo di Spedizione</h3>
                <p>
                    <strong><%= utente.getNome() %></strong><br>
                    <%= utente.getIndirizzo() %><br>
                    <%= utente.getCap() != null ? utente.getCap() : "" %>
                </p>
                <a href="profilo.jsp" style="font-size: 0.9em;">Modifica indirizzo nel profilo</a>
            </div>

            <div class="checkout-box" style="background: white; border: 1px solid #ddd; padding: 20px; border-radius: 8px;">
                <h3>Metodo di Pagamento</h3>
                <div class="form-group">
                    <label>Titolare Carta</label>
                    <input type="text" name="cardHolder" class="form-control" required>
                </div>
                <div class="form-group">
                    <label>Numero Carta</label>
                    <input type="text" name="cardNumber" class="form-control" placeholder="0000 0000 0000 0000" required>
                </div>
                <div style="display: flex; gap: 10px;">
                    <div class="form-group" style="flex: 1;">
                        <label>Scadenza</label>
                        <input type="text" name="expiry" class="form-control" placeholder="MM/AA" required>
                    </div>
                    <div class="form-group" style="flex: 1;">
                        <label>CVV</label>
                        <input type="password" name="cvv" class="form-control" placeholder="123" required>
                    </div>
                </div>
            </div>
        </div>

        <div class="order-summary-box" style="border: 1px solid var(--primary-color); padding: 20px; border-radius: 8px;">
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
</body>
</html>