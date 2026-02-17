<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/header.jspf" %>

<%
    // Redirect se non loggato
    if (session.getAttribute("utente") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<main class="container" style="max-width: 600px; margin-top: 30px;">
    <h2>Metti in vendita una carta</h2>

    <%-- ENCTYPE è fondamentale per inviare immagini --%>
    <form action="vendi" method="post" enctype="multipart/form-data" class="form-style">

        <div class="form-group">
            <label>Nome Carta:</label>
            <input type="text" name="nome" required class="input-field">
        </div>

        <div class="form-group">
            <label>Descrizione e Condizioni:</label>
            <textarea name="descrizione" required class="input-field"></textarea>
        </div>

        <div class="form-group">
            <label>Specifiche (es. Edizione, Lingua):</label>
            <input type="text" name="specifiche" class="input-field">
        </div>

        <div class="form-group">
            <label>Categoria:</label>
            <select name="categoriaId" required class="input-field">
                <option value="1">Yu-Gi-Oh!</option>
                <option value="2">Pokemon</option>
                <option value="3">Magic</option>
                <option value="4">Sportive</option>
                <option value="5">Accessori</option>
            </select>
        </div>

        <div class="form-group">
            <label>Prezzo (€):</label>
            <input type="number" step="0.01" name="prezzo" required class="input-field">
        </div>

        <div class="form-group">
            <label>Quantità:</label>
            <input type="number" name="quantita" value="1" min="1" required class="input-field">
        </div>

        <div class="form-group">
            <label>Foto della Carta:</label>
            <input type="file" name="foto" accept="image/*" required class="input-field">
        </div>

        <button type="submit" class="btn btn-primary" style="width: 100%; margin-top: 15px;">Metti in Vendita</button>
    </form>
</main>

<style>
    .form-style .form-group { margin-bottom: 15px; }
    .input-field { width: 100%; padding: 8px; border: 1px solid #ccc; border-radius: 4px; }
</style>

<%@ include file="common/footer.jspf" %>