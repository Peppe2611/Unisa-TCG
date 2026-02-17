<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/header.jspf" %>

<main class="container">
    <div class="dashboard-container">
        <h1 class="page-title">Privacy Policy - UnisaTCG</h1>
        <p>Informativa sulla protezione dei dati personali</p>

        <div class="checkout-box" style="margin-top: 30px; text-align: left;">
            <h3 style="color: #333; border-bottom: 2px solid #eee; padding-bottom: 10px;">Informativa Dati</h3>

            <div class="success-message" style="margin: 20px 0; padding: 15px; background-color: #d4edda; border-radius: 8px;">
                I dati non vengono ceduti a terzi e sono usati esclusivamente per le finalità del progetto didattico.
            </div>

            <div class="form-group" style="margin-bottom: 20px;">
                <label style="font-weight: bold; display: block; margin-bottom: 5px;">Cookie</label>
                <p>Utilizziamo esclusivamente cookie tecnici per gestire la tua sessione di acquisto fittizia. </p>
            </div>

            <div style="background-color: #fff3cd; border-left: 5px solid #ffc107; padding: 15px; margin-top: 20px;">
                <p class="error-text" style="margin-bottom: 0; color: #856404;">
                    <strong>Nota:</strong> Questo sistema è una simulazione e non rappresenta un ambiente di produzione sicuro.
                </p>
            </div>
        </div>

        <div class="dashboard-actions" style="margin-top: 30px;">
            <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-primary">
                Torna alla Home
            </a>
        </div>
    </div>
</main>

<%@ include file="common/footer.jspf" %>
