document.getElementById("registerButton").addEventListener("click", function () {
    // Captura os valores dos inputs
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmedPassword = document.getElementById("confirmedPassword").value;

    const registerData = {
        name: name,
        email: email,
        password: password,
        confirmedPassword: confirmedPassword
    };

    fetch("/users/register", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(registerData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao registrar o usuário.');
            }
            return response.json();
        })
        .then(data => {
            console.log("Registro bem-sucedido, dados recebidos:", data);
            alert("Usuário registrado com sucesso!");

            setTimeout(() => {
                window.location.href = "/users/login/login.html";
            }, 3000);
        })
        .catch(error => {
            console.error("Erro:", error);
            alert("Erro ao registrar. Verifique as informações e tente novamente.");
        });
});
