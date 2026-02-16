<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="it.unisatcg.model.Utente" %>

<%
    Utente utente = (Utente) session.getAttribute("utente");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Profilo Utente</title>
    <link rel="stylesheet" href="styles/style.css">

    <style>
        .profilo-container {
            width: 500px;
            margin: 40px auto;
            background: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0,0,0,0.1);
        }

        .profilo-container h2 {
            text-align: center;
        }

        .profilo-field {
            margin: 10px 0;
        }

        .profilo-field label {
            font-weight: bold;
        }

        input {
            width: 100%;
            padding: 6px;
        }

        button {
            margin-top: 15px;
            width: 100%;
            padding: 10px;
        }
    </style>
</head>

<body>

<%@ include file="common/header.jspf" %>

<div class="profilo-container">
    <h2>👤 Il tuo profilo</h2>

    <form method="post" action="updateProfilo">

        <input type="hidden" name="id" value="<%= utente.getId() %>" />

        <div class="profilo-field">
            <label for="nome">Nome:</label>
            <input type="text" id="nome" name="nome"
                   value="<%= utente.getNome() %>" required />
        </div>

        <div class="profilo-field">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email"
                   value="<%= utente.getEmail() %>" required />
        </div>

        <div class="profilo-field">
            <label for="telefono">Telefono:</label>
            <input type="text" id="telefono" name="telefono"
                   value="<%= utente.getTelefono() %>" required />
        </div>

        <div class="profilo-field">
            <label for="indirizzo">Indirizzo:</label>
            <input type="text" id="indirizzo" name="indirizzo"
                   value="<%= utente.getIndirizzo() %>" required />
        </div>

        <div class="profilo-field">
            <label for="cap">CAP:</label>
            <input type="text" id="cap" name="cap"
                   value="<%= utente.getCap() %>" required />
        </div>

        <button type="submit">💾 Salva modifiche</button>
    </form>
</div>

<%@ include file="common/footer.jspf" %>

</body>
</html>