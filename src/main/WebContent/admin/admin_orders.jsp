<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, it.unisatcg.model.Ordine" %>
<%@ include file="../common/header.jspf" %>

<%
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
%>

<main>
    <div class="container">

        <h1 class="page-title">Gestione Ordini</h1>

        <%
            if (ordini == null || ordini.isEmpty()) {
        %>
        <p style="text-align:center;">Nessun ordine presente.</p>
        <%
        } else {
        %>

        <table class="management-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Cliente</th>
                <th>Data</th>
                <th>Totale</th>
                <th>Stato</th>
                <th>Azione</th>
            </tr>
            </thead>

            <tbody>
            <%
                for (Ordine o : ordini) {
            %>
            <tr>
                <td><%= o.getId() %></td>
                <td><%= o.getClienteId() %></td>
                <td><%= o.getDataOrdine() %></td>
                <td><%= o.getTotale() %> €</td>

                <td>
                    <strong><%= o.getStatus() %></strong>
                </td>

                <td>
                    <form method="post" action="<%= request.getContextPath() %>/admin/ordini">
                        <input type="hidden" name="id" value="<%= o.getId() %>" />

                        <select name="status">
                            <option value="In elaborazione"
                                    <%= "In elaborazione".equals(o.getStatus()) ? "selected" : "" %>>
                                In elaborazione
                            </option>

                            <option value="Spedito"
                                    <%= "Spedito".equals(o.getStatus()) ? "selected" : "" %>>
                                Spedito
                            </option>

                            <option value="Consegnato"
                                    <%= "Consegnato".equals(o.getStatus()) ? "selected" : "" %>>
                                Consegnato
                            </option>
                        </select>

                        <button type="submit" class="btn btn-primary">
                            Aggiorna
                        </button>
                    </form>
                </td>
            </tr>
            <%
                }
            %>
            </tbody>
        </table>

        <%
            }
        %>

    </div>
</main>

<%@ include file="../common/footer.jspf" %>
