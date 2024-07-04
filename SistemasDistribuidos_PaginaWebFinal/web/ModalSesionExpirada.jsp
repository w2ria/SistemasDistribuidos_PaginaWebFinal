<div id="myModal" class="modal">
    <div class="modal-content">
        <p>Tu sesión expirará en <span id="countdown"></span> segundos.</p>
    </div>
</div>


<style>
    .modal {
        display: none;
        position: fixed;
        z-index: 1;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        overflow: auto;
        background-color: rgb(0,0,0);
        background-color: rgba(0,0,0,0.4);
        padding-top: 60px;
    }
    .modal-content {
        background-color: #fefefe;
        margin: 5% auto;
        padding: 20px;
        border: 1px solid #888;
        width: 80%;
        text-align: center;
    }

    span{
        font-size: 3.5vh;
        margin: 0.5vh;
    }
</style>

<script>
    var totalInactiveTime = 7; // 10 segundos totales
    var warningTime = 5; // 5 segundos antes del logout, osea es la cosa blanca "Modal"
    var time, warningTimer;

    window.onload = function () {
        resetTimer();
        document.addEventListener('mousemove', resetTimer, false);
        document.addEventListener('keypress', resetTimer, false);
    }

    function startLogoutWarning() {
        var countdown = warningTime;
        var modal = document.getElementById("myModal");
        var countdownElement = document.getElementById("countdown");
        modal.style.display = "block";
        countdownElement.innerText = countdown;

        warningTimer = setInterval(function () {
            countdown--;
            countdownElement.innerText = countdown;
            if (countdown < 0) {
                clearInterval(warningTimer);
                logout();
            }
        }, 1000);
    }

    function logout() {
        alert("Tu sesión ha expirado.");
        window.location.href = 'CerrarSesion'; /*Es el controlador que hice para que se cierre */
    }


    function resetTimer() {
        clearTimeout(time);
        clearInterval(warningTimer); // Detener el contador de advertencia si está en curso
        var modal = document.getElementById("myModal");
        modal.style.display = "none"; // Ocultar el modal si se muestra
        time = setTimeout(startLogoutWarning, (totalInactiveTime - warningTime) * 1000);
    }
</script>
