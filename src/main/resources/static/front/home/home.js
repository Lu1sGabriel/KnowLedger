import httpService from "../../services/public/httpService.js";


document.addEventListener("DOMContentLoaded", async function () {
    const authToken = localStorage.getItem("authToken");

    if (!authToken) {
        // Redireciona para a página de login se o authToken não existir
        window.location.href = "../users/public/login/login.html";
    }

    const apiUrl = "/api/posts";


    try {
        const posts = await httpService.get(apiUrl);
        console.log(posts);
    } catch (error) {
        console.error("Erro ao carregar os posts:", error);
    }

}   );