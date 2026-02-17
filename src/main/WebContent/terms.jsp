<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/header.jspf" %>

<main class="container">
    <div class="dashboard-container">
        <h1 class="page-title">Termini di Servizio - UnisaTCG</h1>
        <p>Accordo per l'Utente e simulazione didattica</p>

        <div class="checkout-box" style="margin-top: 30px; text-align: left;">
            <h3 style="color: #333; border-bottom: 2px solid #eee; padding-bottom: 10px;">Dettagli dell'Accordo</h3>

            <div class="form-group" style="margin-top: 20px;">
                <label style="font-weight: bold; color: #007bff;">Progetto Universitario</label>
                <p>Questo sito è una simulazione didattica. Non vengono effettuate transazioni reali né spedizioni di prodotti fisici.</p>
            </div>

            <hr style="border: 0; border-top: 1px solid #eee; margin: 20px 0;">

            <div class="form-group">
                <label style="font-weight: bold; color: #dc3545;">Responsabilità e Sicurezza</label>
                <p>I dati inseriti sono a solo scopo di test. Si prega caldamente di <strong>non utilizzare password reali</strong> o dati sensibili.</p>
            </div>

            <div style="background-color: #f8f9fa; padding: 15px; border-radius: 5px; margin-top: 20px; text-align: center;">
                <p class="price" style="font-size: 1.25rem; font-weight: bold; margin: 0;">Costo del servizio: 0.00€</p>
            </div>
        </div>

        <div class="dashboard-actions" style="display: flex; gap: 20px; justify-content: center; margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                Torna alla Home
            </a>
            <a href="${pageContext.request.contextPath}/login.jsp" class="btn btn-success">
                Vai al Login
            </a>
        </div>
    </div>
</main>

<%@ include file="common/footer.jspf" %>