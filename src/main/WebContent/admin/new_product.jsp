<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisatcg.model.Categoria" %>
<!DOCTYPE html>
<html>
<head>
    <title>Nuovo Prodotto - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/style.css">
</head>
<body>
<%@ include file="../common/header.jspf" %>

<main class="container">
    <div class="auth-form-container">
        <div class="auth-form-box" style="max-width: 800px;"> <%-- Box più largo per comodità --%>
            <h1 class="page-title">Aggiungi Prodotto</h1>

            <form action="${pageContext.request.contextPath}/New_Product" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="nome">Nome Prodotto</label>
                    <input type="text" id="nome" name="nome" required>
                </div>

                <div class="form-group">
                    <label for="descrizione">Descrizione</label>
                    <textarea id="descrizione" name="descrizione" rows="3" required></textarea>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="prezzo">Prezzo (€)</label>
                        <input type="number" step="0.01" id="prezzo" name="prezzo" required>
                    </div>
                    <div class="form-group">
                        <label for="quantita">Quantità</label>
                        <input type="number" id="quantita" name="quantita" required>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="categoriaId">Categoria</label>
                        <select id="categoriaId" name="categoriaId" required>
                            <option value="" disabled selected>Scegli...</option>
                            <%
                                List<Categoria> categorie = (List<Categoria>) request.getAttribute("listaCategorie");
                                if (categorie != null) {
                                    for (Categoria c : categorie) {
                            %>
                            <option value="<%= c.getId() %>"><%= c.getNome() %></option>
                            <% } } %>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="disponibile">Visibilità</label>
                        <select id="disponibile" name="disponibile">
                            <option value="true">Immediata</option>
                            <option value="false">Bozza (Nascosto)</option>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <label for="specifiche">Specifiche Tecniche</label>
                    <input type="text" id="specifiche" name="specifiche">
                </div>

                <div class="form-group">
                    <label for="foto">Foto Prodotto (BLOB)</label>
                    <input type="file" id="foto" name="foto" accept="image/*" required>
                </div>

                <button type="submit" class="btn btn-success" style="width: 100%;">Crea Prodotto</button>
            </form>
        </div>
    </div>
</main>

<%@ include file="../common/footer.jspf" %>
</body>
</html>