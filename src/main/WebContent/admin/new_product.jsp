<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, it.unisatcg.model.Categoria" %>
<%@ include file="../common/header.jspf" %>

<main class="container">
    <div class="auth-form-container">
        <div class="auth-form-box" style="max-width: 800px; margin: 40px auto;">
            <h1 class="page-title">Aggiungi Prodotto</h1>

            <form action="${pageContext.request.contextPath}/admin/new-product" method="post" enctype="multipart/form-data">

                <div class="form-group">
                    <label for="nome">Nome Prodotto</label>
                    <input type="text" id="nome" name="nome" required class="form-control">
                </div>

                <div class="form-group">
                    <label for="descrizione">Descrizione</label>
                    <textarea id="descrizione" name="descrizione" rows="3" required class="form-control"></textarea>
                </div>

                <div class="form-row" style="display: flex; gap: 20px;">
                    <div class="form-group" style="flex: 1;">
                        <label for="prezzo">Prezzo (€)</label>
                        <input type="number" step="0.01" id="prezzo" name="prezzo" required class="form-control">
                    </div>
                    <div class="form-group" style="flex: 1;">
                        <label for="quantita">Quantità</label>
                        <input type="number" id="quantita" name="quantita" required class="form-control">
                    </div>
                </div>

                <div class="form-group">
                    <label for="categoria">Categoria ID</label>
                    <%-- Se hai la lista categorie nella request usala, altrimenti input manuale --%>
                    <input type="number" id="categoria" name="categoriaId" required class="form-control">
                </div>

                <div class="form-group">
                    <label for="foto">Foto Prodotto</label>
                    <input type="file" id="foto" name="foto" accept="image/*" required class="form-control">
                </div>

                <button type="submit" class="btn btn-success" style="width: 100%; margin-top: 20px;">Crea Prodotto</button>
            </form>
        </div>
    </div>
</main>

<%@ include file="../common/footer.jspf" %>