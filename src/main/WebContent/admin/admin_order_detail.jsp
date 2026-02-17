<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*, it.unisatcg.model.Ordine" %>
<%@ include file="../common/header.jspf" %>
<main class="container">
    <% Ordine o = (Ordine) request.getAttribute("ordine"); %>
    <h1>Dettaglio Ordine #<%= o.getId() %></h1>

    <div class="checkout-box" style="margin-bottom:20px;">
        <p><strong>Totale:</strong> <%= o.getTotale() %> €</p>
        <p><strong>Stato Attuale:</strong> <%= o.getStatus() %></p>
        <form method="post" action="${pageContext.request.contextPath}/admin/manage-orders">
            <input type="hidden" name="id" value="<%= o.getId() %>" />
            <select name="status">
                <option value="In lavorazione" <%= "In lavorazione".equals(o.getStatus()) ? "selected" : "" %>>In lavorazione</option>
                <option value="Spedito" <%= "Spedito".equals(o.getStatus()) ? "selected" : "" %>>Spedito</option>
                <option value="Consegnato" <%= "Consegnato".equals(o.getStatus()) ? "selected" : "" %>>Consegnato</option>
            </select>
            <button type="submit" class="btn btn-primary">Aggiorna Stato</button>
        </form>
    </div>

    <table class="management-table">
        <thead><tr><th>Prodotto</th><th>Quantità</th><th>Prezzo Un.</th></tr></thead>
        <tbody>
        <% List<Map<String, Object>> articoli = (List<Map<String, Object>>) request.getAttribute("articoli");
            for (Map<String, Object> art : articoli) { %>
        <tr>
            <td><%= art.get("nome") %></td>
            <td><%= art.get("quantita") %></td>
            <td><%= String.format("%.2f", art.get("prezzo")) %> €</td>
        </tr>
        <% } %>
        </tbody>
    </table>
</main>
<%@ include file="../common/footer.jspf" %>