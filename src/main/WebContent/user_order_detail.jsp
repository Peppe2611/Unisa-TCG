<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisatcg.model.Ordine, it.unisatcg.model.DettaglioOrdine" %>
<%
    Ordine ordine = (Ordine) request.getAttribute("ordine");
    List<DettaglioOrdine> dettagli = (List<DettaglioOrdine>) request.getAttribute("dettagli");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Dettaglio Ordine #<%= ordine.getId() %></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<%@ include file="/common/header.jspf" %>

<main class="container">
    <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
        <h1 class="page-title">Ordine #<%= ordine.getId() %></h1>
        <a href="${pageContext.request.contextPath}/storico-ordini" class="btn btn-secondary">Torna alla lista</a>
    </div>

    <div class="checkout-box" style="margin-bottom: 30px;">
        <h3>Riepilogo</h3>
        <p><strong>Data:</strong> <%= ordine.getDataOrdine() %></p>
        <p><strong>Stato:</strong> <%= ordine.getStatus() %></p>
        <p><strong>Totale Ordine:</strong> <%= String.format("%.2f", ordine.getTotale()) %> €</p>

        <%-- Se il primo dettaglio ha l'indirizzo, lo mostriamo qui --%>
        <% if (!dettagli.isEmpty() && dettagli.get(0).getIndirizzo() != null) { %>
        <p><strong>Spedito a:</strong> <%= dettagli.get(0).getIndirizzo() %> - <%= dettagli.get(0).getCap() %></p>
        <% } %>
    </div>

    <h3>Prodotti Acquistati</h3>
    <table class="management-table" style="width: 100%; border-collapse: collapse; margin-top: 10px; box-shadow: 0 0 20px rgba(0,0,0,0.1);">
        <thead>
        <tr style="background-color: #009879; color: white;">
            <th style="padding: 12px 15px; text-align: left;">Prodotto</th>
            <th style="padding: 12px 15px; text-align: center;">Quantità</th>
            <th style="padding: 12px 15px; text-align: right;">Prezzo Unitario</th>
            <th style="padding: 12px 15px; text-align: right;">Subtotale</th>
        </tr>
        </thead>
        <tbody>
        <% for (DettaglioOrdine d : dettagli) { %>
        <tr style="border-bottom: 1px solid #ddd;">
            <td style="padding: 12px 15px;">
                <%-- Ora usa la variabile che abbiamo aggiunto --%>
                <%= d.getNomeProdotto() %>
            </td>
            <td style="padding: 12px 15px; text-align: center;">
                <%= d.getQuantita() %>
            </td>
            <td style="padding: 12px 15px; text-align: right;">
                <%-- CORRETTO: usa getPrezzoUnitario() --%>
                <%= String.format("%.2f", d.getPrezzoUnitario()) %> €
            </td>
            <td style="padding: 12px 15px; text-align: right; font-weight: bold;">
                <%= String.format("%.2f", d.getPrezzoUnitario() * d.getQuantita()) %> €
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</main>

<%@ include file="/common/footer.jspf" %>
</body>
</html>