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

<main class="container">
    <h1 class="page-title">Storico Ordini</h1>

    <% if (ordini == null || ordini.isEmpty()) { %>
    <div style="text-align: center; margin-top: 50px;">
        <h3>Nessun ordine trovato.</h3>
        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">Vai al Catalogo</a>
    </div>
    <% } else { %>

    <table class="management-table" style="width: 100%; border-collapse: collapse; margin-top: 20px; box-shadow: 0 0 20px rgba(0,0,0,0.1);">
        <thead>
        <tr style="background-color: #009879; color: #ffffff; text-align: left;">
            <th style="padding: 12px 15px;">ID Ordine</th>
            <th style="padding: 12px 15px;">Data</th>
            <th style="padding: 12px 15px;">Totale</th>
            <th style="padding: 12px 15px;">Stato</th>
            <th style="padding: 12px 15px;">Azioni</th>
        </tr>
        </thead>
        <tbody>
        <% for (Ordine o : ordini) { %>
        <tr style="border-bottom: 1px solid #dddddd;">


            <td style="padding: 12px 15px;">
                <a href="${pageContext.request.contextPath}/dettaglio-ordine?id=<%= o.getId() %>"
                   style="color: #007bff; font-weight: bold; text-decoration: underline; cursor: pointer;">
                    #<%= o.getId() %>
                </a>
            </td>

            <td style="padding: 12px 15px;"><%= o.getDataOrdine() %></td>
            <td style="padding: 12px 15px; font-weight: bold;"><%= String.format("%.2f", o.getTotale()) %> €</td>
            <td style="padding: 12px 15px;">
                            <span style="padding: 5px 10px; border-radius: 4px; font-size: 0.9em; background-color: #eee;">
                                <%= o.getStatus() %>
                            </span>
            </td>

            <%-- ANCHE IL TASTO AZIONI ORA PORTA AL DETTAGLIO --%>
            <td style="padding: 12px 15px;">
                <a href="${pageContext.request.contextPath}/dettaglio-ordine?id=<%= o.getId() %>"
                   class="btn btn-primary"
                   style="padding: 5px 10px; font-size: 0.8em; text-decoration: none; color: white; background-color: #007bff; border-radius: 3px;">
                    Vedi Dettagli
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

    <div style="margin-top: 20px;">
        <a href="${pageContext.request.contextPath}/profile.jsp" style="color: #666;">&larr; Torna al Profilo</a>
    </div>
    <% } %>
</main>

<%@ include file="/common/footer.jspf" %>
</body>
</html>