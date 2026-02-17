<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisatcg.model.Ordine" %>
<%
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
%>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>I Miei Ordini - UnisaTCG</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>
<%@ include file="/common/header.jspf" %>

<main class="container" style="padding: 40px 0;">
    <h1 class="page-title">Storico Ordini</h1>

    <% if (ordini == null || ordini.isEmpty()) { %>
    <div style="text-align: center; margin-top: 50px;">
        <h3>Non hai ancora effettuato ordini.</h3>
        <a href="${pageContext.request.contextPath}/catalogo.jsp" class="btn btn-primary" style="margin-top: 20px;">Vai al Catalogo</a>
    </div>
    <% } else { %>

    <table class="management-table" style="width: 100%; border-collapse: collapse; margin-top: 20px;">
        <thead>
        <tr style="background-color: #f8f9fa; text-align: left; color: #333;">
            <th style="padding: 15px; border-bottom: 2px solid #dee2e6;">Ordine #</th>
            <th style="padding: 15px; border-bottom: 2px solid #dee2e6;">Data</th>
            <th style="padding: 15px; border-bottom: 2px solid #dee2e6;">Totale</th>
            <th style="padding: 15px; border-bottom: 2px solid #dee2e6;">Stato</th>
        </tr>
        </thead>
        <tbody>
        <% for (Ordine o : ordini) { %>
        <tr style="border-bottom: 1px solid #dee2e6;">
            <td style="padding: 15px;">#<%= o.getId() %></td>
            <td style="padding: 15px;"><%= o.getDataOrdine() %></td>
            <td style="padding: 15px; font-weight: bold;"><%= String.format("%.2f", o.getTotale()) %> €</td>
            <td style="padding: 15px;">
                            <span style="padding: 5px 10px; border-radius: 15px; background-color: #eee;">
                                <%= o.getStatus() %>
                            </span>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/profile.jsp">&larr; Torna al Profilo</a>
    </div>
    <% } %>
</main>

<%@ include file="/common/footer.jspf" %>
</body>
</html>