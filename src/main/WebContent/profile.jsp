<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="it.unisatcg.model.Utente" %>
<%@ page import="it.unisatcg.model.Ordine" %>
<%@ page import="java.util.List" %>
<%@ page import="java.time.format.DateTimeFormatter" %>

<%
    Utente utente = (Utente) session.getAttribute("utente");
    if (utente == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Ordine> ordini = (List<Ordine>) request.getAttribute("ordini");
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
%>

<%@ include file="common/header.jspf" %>

<main class="container">
    <div class="dashboard-container">
        <h1 class="page-title">Area Personale</h1>
        <p>Gestisci i tuoi dati e visualizza i tuoi acquisti</p>

        <div style="display: flex; gap: 30px; flex-wrap: wrap; align-items: flex-start;">

            <%-- SEZIONE PROFILO --%>
            <div class="checkout-box" style="flex: 1; min-width: 350px;">
                <h3>👤 Il tuo profilo</h3>
                <form method="post" action="updateProfilo">
                    <input type="hidden" name="id" value="<%= utente.getId() %>" />

                    <div class="form-group" style="margin-bottom: 15px;">
                        <label style="font-weight: bold;">Nome:</label>
                        <input type="text" name="nome" value="<%= utente.getNome() %>" class="form-control" required style="width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #ddd;"/>
                    </div>

                    <div class="form-group" style="margin-bottom: 15px;">
                        <label style="font-weight: bold;">Email:</label>
                        <input type="email" name="email" value="<%= utente.getEmail() %>" class="form-control" required style="width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #ddd;"/>
                    </div>

                    <div class="form-group" style="margin-bottom: 15px;">
                        <label style="font-weight: bold;">Indirizzo:</label>
                        <input type="text" name="indirizzo" value="<%= utente.getIndirizzo() %>" class="form-control" style="width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #ddd;"/>
                    </div>

                    <button type="submit" class="btn btn-success" style="width: 100%; margin-top: 10px;">💾 Salva Modifiche</button>
                </form>
            </div>

            <%-- SEZIONE STORICO ORDINI --%>
            <div class="checkout-box" style="flex: 2; min-width: 500px;">
                <h3>📦 Storico Ordini</h3>

                <% if (ordini == null || ordini.isEmpty()) { %>
                <p style="text-align: center; color: #666; padding: 20px;">Non hai ancora effettuato ordini.</p>
                <% } else { %>
                <table style="width: 100%; border-collapse: collapse; margin-top: 10px;">
                    <thead>
                    <tr style="background-color: #f8f9fa; border-bottom: 2px solid #eee;">
                        <th style="padding: 12px; text-align: left;">ID Ordine</th>
                        <th style="padding: 12px; text-align: left;">Data</th>
                        <th style="padding: 12px; text-align: left;">Totale</th>
                        <th style="padding: 12px; text-align: center;">Stato</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for (Ordine o : ordini) { %>
                    <tr style="border-bottom: 1px solid #eee;">
                        <td style="padding: 12px;">#<%= o.getId() %></td>
                        <td style="padding: 12px;"><%= o.getDataOrdine().format(formatter) %></td>
                        <td style="padding: 12px; font-weight: bold;">€ <%= String.format("%.2f", o.getTotale()) %></td>
                        <td style="padding: 12px; text-align: center;">
                                        <span style="padding: 4px 10px; border-radius: 20px; font-size: 0.85rem; font-weight: bold;
                                            <%= o.getStatus().equalsIgnoreCase("Consegnato") ? "background: #d4edda; color: #155724;" : "background: #fff3cd; color: #856404;" %>">
                                            <%= o.getStatus().toUpperCase() %>
                                        </span>
                        </td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
                <% } %>
            </div>

        </div>
    </div>
</main>

<%@ include file="common/footer.jspf" %>