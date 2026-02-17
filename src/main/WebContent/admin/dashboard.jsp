<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="it.unisatcg.model.Utente" %>
<%
    Utente utente = (Utente) session.getAttribute("utente");
    if(utente == null || !utente.isAdmin()){
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
    String nomeAdmin = utente.getNome();
%>
<%@ include file="../common/header.jspf" %>

<main class="container">
    <div class="dashboard-container">
        <h1 class="page-title">Benvenuto, <%= nomeAdmin %></h1>
        <p>Pannello di Amministrazione</p>

        <div class="dashboard-actions" style="display: flex; gap: 20px; justify-content: center; margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/admin/new-product" class="btn btn-success">
                Nuovo Prodotto
            </a>

            <a href="${pageContext.request.contextPath}/admin/gestione-utenti" class="btn btn-primary" style="background-color: #6f42c1;">
                Gestione Utenti
            </a>

            <a href="${pageContext.request.contextPath}/admin/gestione-recensioni" class="btn btn-primary" style="background-color: #d63384;">
                Moderazione Recensioni
            </a>

            <a href="${pageContext.request.contextPath}/admin/gestione-prodotti" class="btn btn-primary">
                Gestione Prodotti
            </a>

            <a href="${pageContext.request.contextPath}/admin/admin_orders.jsp" class="btn btn-primary">
                Gestione Ordini
            </a>
        </div>
    </div>
</main>

<%@ include file="../common/footer.jspf" %>