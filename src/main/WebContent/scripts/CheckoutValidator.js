document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('checkoutForm');
    const cardNumber = document.getElementById('card-number');
    const expiryDate = document.getElementById('expiry-date');
    const cvv = document.getElementById('cvv');

    function showError(elementId, message, inputElement) {
        const errorSpan = document.getElementById(elementId);
        errorSpan.innerText = message;
        inputElement.classList.add('invalid-field');
    }

    function clearError(elementId, inputElement) {
        const errorSpan = document.getElementById(elementId);
        errorSpan.innerText = "";
        inputElement.classList.remove('invalid-field');
    }

    function validateCard() {
        let val = cardNumber.value.replace(/\s+/g, '');
        if (val === "") return false;
        if (!/^\d{16}$/.test(val)) {
            showError('cardNumberError', "Il numero carta deve essere di 16 cifre.", cardNumber);
            return false;
        }
        clearError('cardNumberError', cardNumber);
        return true;
    }

    function validateExpiry() {
        const val = expiryDate.value;
        if (val === "") return false;
        const regex = /^(0[1-9]|1[0-2])\/([0-9]{2})$/;
        if (!regex.test(val)) {
            showError('expiryError', "Formato non valido. Usa MM/AA (es. 12/26).", expiryDate);
            return false;
        }
        const parts = val.split('/');
        const month = parseInt(parts[0], 10);
        const year = parseInt("20" + parts[1], 10);
        const now = new Date();
        const currentMonth = now.getMonth() + 1;
        const currentYear = now.getFullYear();

        if (year < currentYear || (year === currentYear && month < currentMonth)) {
            showError('expiryError', "La carta inserita è scaduta.", expiryDate);
            return false;
        }
        clearError('expiryError', expiryDate);
        return true;
    }

    function validateCVV() {
        const val = cvv.value;
        if (val === "") return false;
        if (!/^\d{3}$/.test(val)) {
            showError('cvvError', "Il CVV deve essere di 3 cifre.", cvv);
            return false;
        }
        clearError('cvvError', cvv);
        return true;
    }

    cardNumber.addEventListener('blur', validateCard);
    expiryDate.addEventListener('blur', validateExpiry);
    cvv.addEventListener('blur', validateCVV);

    cardNumber.addEventListener('focus', () => clearError('cardNumberError', cardNumber));
    expiryDate.addEventListener('focus', () => clearError('expiryError', expiryDate));
    cvv.addEventListener('focus', () => clearError('cvvError', cvv));

    form.addEventListener('submit', function(e) {
        const isCardValid = validateCard();
        const isExpiryValid = validateExpiry();
        const isCvvValid = validateCVV();

        if (!isCardValid || !isExpiryValid || !isCvvValid) {
            e.preventDefault();
            alert("Per favore, correggi i dati della carta evidenziati in rosso.");

            if (!isCardValid) cardNumber.focus();
            else if (!isExpiryValid) expiryDate.focus();
            else if (!isCvvValid) cvv.focus();
        }
    });
});