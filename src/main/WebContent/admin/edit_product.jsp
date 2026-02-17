<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unisatcg.model.Prodotto" %>
<%@ page import="it.unisatcg.model.Categoria" %>
<%@ page import="java.util.List" %>
<%
    Prodotto prodotto = (Prodotto) request.getAttribute("prodotto");
    // Recuperiamo la lista inviata dalla Servlet
    List<Categoria> categorie = (List<Categoria>) request.getAttribute("categorie");

    if (prodotto == null) {
        response.sendRedirect("gestione-prodotti");
        return;
    }
%>
<%@ include file="../common/header.jspf" %>

<main class="container">
    <div class="edit-container" style="max-width: 600px; margin: 40px auto; padding: 25px; border: 1px solid #ddd; border-radius: 8px; background-color: #fff;">
        <h2 style="text-align: center; margin-bottom: 20px;">Modifica: <%= prodotto.getNome() %></h2>

        <form action="${pageContext.request.contextPath}/admin/edit-product" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="<%= prodotto.getId() %>">

            <div class="form-group">
                <label>Nome Prodotto:</label>
                <input type="text" name="nome" value="<%= prodotto.getNome() %>" required style="width: 100%; padding: 8px;">
            </div>

            <div class="form-group">
                <label>Descrizione:</label>
                <textarea name="descrizione" rows="4" required style="width: 100%; padding: 8px;"><%= prodotto.getDescrizione() %></textarea>
            </div>

            <div class="form-row" style="display: flex; gap: 15px;">
                <div class="form-group" style="flex: 1;">
                    <label>Prezzo (€):</label>
                    <input type="number" step="0.01" name="prezzo" value="<%= prodotto.getPrezzo() %>" required style="width: 100%; padding: 8px;">
                </div>
                <div class="form-group" style="flex: 1;">
                    <label>Quantità:</label>
                    <input type="number" name="quantita" value="<%= prodotto.getQuantita() %>" required style="width: 100%; padding: 8px;">
                </div>
            </div>

            <%-- MENU A TENDINA PER LE CATEGORIE --%>
            <div class="form-group">
                <label>Categoria:</label>
                <select name="categoria" required style="width: 100%; padding: 8px; border-radius: 4px; border: 1px solid #ccc;">
                    <%
                        if (categorie != null) {
                            for (Categoria c : categorie) {
                                // Controlla se questa è la categoria attuale del prodotto
                                String selected = (c.getId() == prodotto.getCategoriaId()) ? "selected" : "";
                    %>
                    <option value="<%= c.getId() %>" <%= selected %>> <%= c.getNome() %> </option>
                    <%
                        }
                    } else {
                    %>
                    <option value="<%= prodotto.getCategoriaId() %>">Categoria ID: <%= prodotto.getCategoriaId() %></option>
                    <% } %>
                </select>
            </div>

            <div class="form-group" style="background: #f9f9f9; padding: 15px; border-radius: 4px; margin-top: 15px;">
                <label style="display:block; margin-bottom: 5px;">Foto Attuale:</label>
                <% if(prodotto.getFoto() != null && prodotto.getFoto().length > 0) { %>
                <img src="${pageContext.request.contextPath}/ImageServlet?id=<%= prodotto.getId() %>" alt="Foto attuale" style="max-width: 100px; border: 1px solid #ddd;">
                <% } else { %>
                <span style="color: #888;">Nessuna foto</span>
                <% } %>

                <label style="display:block; margin-top: 10px;">Carica Nuova (Opzionale):</label>
                <input type="file" name="foto" accept="image/*">
            </div>

            <div class="form-group">
                <label>Specifiche:</label>
                <input type="text" name="specifiche" value="<%= prodotto.getSpecifiche() != null ? prodotto.getSpecifiche() : "" %>" style="width: 100%; padding: 8px;">
            </div>

            <button type="submit" class="btn btn-success" style="width: 100%; padding: 10px; margin-top: 20px; background-color: #28a745; color: white; border: none; cursor: pointer;">Salva Modifiche</button>
            <div style="text-align: center; margin-top: 15px;">
                <a href="${pageContext.request.contextPath}/admin/gestione-prodotti" style="color: #666;">Annulla</a>
            </div>
        </form>
    </div>
</main>

<%@ include file="../common/footer.jspf" %>