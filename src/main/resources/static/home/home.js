import httpService from "../services/public/httpService";

document.addEventListener("DOMContentLoaded", () => {
    // Verifica se o token está no localStorage
    const token = localStorage.getItem('authToken');
    if (!token) {
        // Redireciona para login.html se o token não existir
        window.location.href = "login.html";
    } else {
        // Usa o httpService para fazer uma requisição GET
        console.log('chegou na home.js')
        httpService.get('http://localhost:8080/departments/getAll')
            .then(data => {
                // Exibe os dados no elemento content
                document.getElementById('content').innerText = JSON.stringify(data);
            })
            .catch(error => {
                console.error(error);
                window.location.href = "users/login.html"; // Redireciona se o acesso for negado
            });
    }
});
