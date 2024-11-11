import httpService from "../../services/public/httpService.js";

async function insertPost() {
    const textPost = document.querySelector('.text-session').value;
    const title = document.querySelector('.input-duvida').value;

    const post = {
        title: title,
        content: content,
        type: typePost
    };

    try {
        const response = await httpService.post('', post);
        console.log(response);
    } catch (error) {
        console.error(error.message);
    }
}

async function getDepartmentTags() {
    try {
        const apiUrl = 'http://localhost:8080/departments/getAll';
        const response = await httpService.get(apiUrl);

        console.log(response);

        // Seleciona a div onde os botões dinâmicos serão inseridos
        const dynamicButtonsContainer = document.querySelector('.dynamic-buttons');

        response.forEach(department => {
            const button = document.createElement('input');
            button.type = 'button';
            button.value = department.name;
            dynamicButtonsContainer.appendChild(button); // Adiciona o botão à div
        });

    } catch (error) {
        console.error("Erro ao carregar tags de departamentos:", error.message);
    }
}

document.addEventListener('DOMContentLoaded', getDepartmentTags);
