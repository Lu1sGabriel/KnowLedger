document.getElementById("loginButton").addEventListener("click", function () {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const loginData = {
        email: email,
        password: password
    };

    fetch("/users/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(loginData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao realizar o login.');
            }
            return response.json();
        })
        .then(data => {
            console.log("Login bem-sucedido, token recebido:", data.token);
            // window.location.href = "/index";
        })
        .catch(error => {
            alert("Erro ao realizar o login, verifique suas credenciais.");
        });
});
