import httpService from "../../services/public/httpService.js";

let selectedDepartmentId = null;
let selectedPostTypeId = null;

async function insertPost() {
    const textPost = document.querySelector('#text-session').value;
    const title = document.querySelector('.input-duvida').value;

    const post = {
        title,
        content: textPost,
        departmentId: selectedDepartmentId,
        postTypeId: selectedPostTypeId,
    };

    console.log(post);
}

// Função para carregar e exibir os botões de departamento
async function getDepartmentTags() {
    const apiUrl = 'http://localhost:8080/departments/getAll';
    try {
        const response = await httpService.get(apiUrl);
        const dynamicButtonsContainer = document.querySelector('#department');

        response.forEach(department => {
            const button = document.createElement('input');
            button.type = 'button';
            button.value = department.name;
            button.id = department.id;
            button.classList.add('department-button');
            dynamicButtonsContainer.appendChild(button);

            // Adiciona o evento de clique para selecionar o departamento
            button.addEventListener('click', () => selectDepartment(button));
        });
    } catch (error) {
        console.error("Erro ao carregar tags de departamentos:", error.message);
    }
}

// Função para selecionar um botão de departamento e alterar o background-color
function selectDepartment(button) {

    document.querySelectorAll('#department .department-button').forEach(btn => {
        btn.style.backgroundColor = '';
        btn.style.color = '';
    });


    button.style.backgroundColor = 'blue';
    button.style.color = 'white';
    selectedDepartmentId = button.id;
    console.log("ID do departamento selecionado:", selectedDepartmentId);
}


async function getPostType() {
    const apiUrl = 'http://localhost:8080/post-types/getAll';
    try {
        const response = await httpService.get(apiUrl);
        const dynamicButtonsContainer = document.querySelector('#postType');

        response.forEach(postType => {
            const button = document.createElement('input');
            button.type = 'button';
            button.value = postType.name;
            button.id = postType.id;
            button.classList.add('post-type-button');
            dynamicButtonsContainer.appendChild(button);

            // Adiciona o evento de clique para selecionar o tipo de postagem
            button.addEventListener('click', () => selectPostType(button));
        });
    } catch (error) {
        console.error("Erro ao carregar tipos de postagem:", error.message);
    }
}


function selectPostType(button) {

    document.querySelectorAll('#postType .post-type-button').forEach(btn => {
        btn.style.backgroundColor = '';
        btn.style.color = '';
    });


    button.style.backgroundColor = 'blue';
    button.style.color = 'white';
    selectedPostTypeId = button.id;
    console.log("ID do tipo de postagem selecionado:", selectedPostTypeId);
}


document.addEventListener('DOMContentLoaded', () => {
    getDepartmentTags();
    getPostType();
    document.getElementById('post-button').addEventListener('click', insertPost);
});
