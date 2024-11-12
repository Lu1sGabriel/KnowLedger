import httpService from "../../services/public/httpService.js";

let selectedDepartmentId = null;
let selectedPostTypeId = null;

async function insertPost() {
    const textPost = document.querySelector('#text-session').value;
    const title = document.querySelector('.input-duvida').value;

    const post = {
        title: title,
        content: textPost,
        departmentId: selectedDepartmentId,
        postTypeId: selectedPostTypeId,
    };

    try {
        const response = await httpService.post('', post);
        console.log(response);
    } catch (error) {
        console.error(error.message);
    }
}

async function getDepartmentTags() {
    const apiUrl = 'http://localhost:8080/departments/getAll';
    try {
        const response = await httpService.get(apiUrl);
        console.log(response);

        const dynamicButtonsContainer = document.querySelector('#department');

        response.forEach(department => {
            const button = document.createElement('input');
            button.type = 'button';
            button.value = department.name;
            button.id = department.id;
            dynamicButtonsContainer.appendChild(button);
        });

        dynamicButtonsContainer.addEventListener('click', (event) => {
            if (event.target.tagName === 'INPUT' && event.target.type === 'button') {
                selectedDepartmentId = event.target.id; // Atualiza o ID do departamento

                // Remove a classe de seleção de todos os botões
                dynamicButtonsContainer.querySelectorAll('input').forEach(btn => {
                    btn.classList.remove('button-selected');
                });

                // Adiciona a classe de seleção ao botão clicado
                event.target.classList.add('button-selected');

                console.log("ID do departamento selecionado:", selectedDepartmentId);
            }
        });

    } catch (error) {
        console.error("Erro ao carregar tags de departamentos:", error.message);
    }
}

async function getPostType() {
    const apiUrl = 'http://localhost:8080/post-types/getAll';
    try {
        const response = await httpService.get(apiUrl);
        console.log(response);

        const dynamicButtonsContainer = document.querySelector('#postType');

        response.forEach(postType => {
            const button = document.createElement('input');
            button.type = 'button';
            button.value = postType.name;
            button.id = postType.id;
            dynamicButtonsContainer.appendChild(button);
        });

        dynamicButtonsContainer.addEventListener('click', (event) => {
            if (event.target.tagName === 'INPUT' && event.target.type === 'button') {
                selectedPostTypeId = event.target.id; // Atualiza o ID do tipo de postagem

                // Remove a classe de seleção de todos os botões
                dynamicButtonsContainer.querySelectorAll('input').forEach(btn => {
                    btn.classList.remove('button-selected');
                });

                // Adiciona a classe de seleção ao botão clicado
                event.target.classList.add('button-selected');

                console.log("ID do tipo de postagem selecionado:", selectedPostTypeId);
            }
        });

    } catch (error) {
        console.error("Erro ao carregar tipos de postagem:", error.message);
    }
}

async function callFunctions() {
    await getDepartmentTags();
    await getPostType();
}

document.addEventListener('DOMContentLoaded', callFunctions);
