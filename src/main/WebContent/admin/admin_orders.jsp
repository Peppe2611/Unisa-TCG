<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, it.unisatcg.model.Ordine" %>
<%@ include file="../common/header.jspf" %>
<%
    // Recupero della lista ordini passata dalla Servlet
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
%>

<main class="container">
    <h1 class="page-title">Gestione Ordini</h1>
    <% if (ordini == null || ordini.isEmpty()) { %>
    <p style="text-align:center;">Nessun ordine presente nel database.</p>
    <% } else { %>
    <table class="management-table">
        <thead>
        <tr>
            <th>ID</th><th>Cliente</th><th>Data</th><th>Totale</th><th>Stato</th><th>Azione</th>
        </tr>
        </thead>
        <tbody>
        <% for (Ordine o : ordini) { %>
        <tr>
            <td>
                <%-- Link al dettaglio ordine --%>
                <a href="order-detail?id=<%= o.getId() %>" style="font-weight:bold;">#<%= o.getId() %></a>
            </td>
            <td><%= o.getClienteId() %></td>
            <td><%= o.getDataOrdine() %></td>
            <td><%= String.format("%.2f", o.getTotale()) %> €</td>
            <td><strong><%= o.getStatus() %></strong></td>
            <td>
                <form method="post" action="${pageContext.request.contextPath}/admin/manage-orders">
                    <input type="hidden" name="id" value="<%= o.getId() %>" />
                    <select name="status">
                        <option value="In lavorazione" <%= "In lavorazione".equals(o.getStatus()) ? "selected" : "" %>>In lavorazione</option>
                        <option value="Spedito" <%= "Spedito".equals(o.getStatus()) ? "selected" : "" %>>Spedito</option>
                        <option value="Consegnato" <%= "Consegnato".equals(o.getStatus()) ? "selected" : "" %>>Consegnato</option>
                    </select>
                    <button type="submit" class="btn btn-primary">Aggiorna</button>
                </form>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
</main>
<%@ include file="../common/footer.jspf" %>