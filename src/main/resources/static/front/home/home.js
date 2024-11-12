import httpService from "../../services/public/httpService.js";


document.addEventListener("DOMContentLoaded", async function () {

    const apiUrl = "/api/posts";

    try {
        const posts = await httpService.get(apiUrl);
        console.log(posts);
    } catch (error) {
        console.error("Erro ao carregar os posts:", error);
    }

}   );