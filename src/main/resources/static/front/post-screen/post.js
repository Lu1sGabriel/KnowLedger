import httpService from "../../services/public/httpService.js";

let selectedDepartmentId = null;
let selectedPostTypeId = null;
const apiUrl = 'http://localhost:8080/api/posts/register';

// Função para decodificar o token JWT e extrair o user_id
function parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
        atob(base64)
            .split('')
            .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
            .join('')
    );

    return JSON.parse(jsonPayload);
}

// Função para inserir postagem
async function insertPost() {
    const textPost = document.querySelector('#text-session').value;
    const title = document.querySelector('.input-duvida').value;
    const token = localStorage.getItem('authToken');
    const payload = parseJwt(token);

    const postData = {
        title,
        content: textPost,
        departmentId: selectedDepartmentId,
        postTypeId: selectedPostTypeId,
        userId: payload.user_id
    };
    console.log('post->', postData);

    try {
        const response = await httpService.post(apiUrl, postData);
        console.log("Postagem inserida com sucesso:", response);

        // Chama a função para limpar o formulário após o sucesso
        resetForm();

    } catch (error) {
        console.error("Erro ao inserir postagem:", error.message);
    }
}

// Função para limpar o formulário
function resetForm() {
    // Limpa os campos de texto
    document.querySelector('#text-session').value = '';
    document.querySelector('.input-duvida').value = '';

    // Desmarca os botões de departamento e tipo de postagem
    document.querySelectorAll('#department .department-button').forEach(btn => {
        btn.style.backgroundColor = '';
        btn.style.color = '';
    });
    document.querySelectorAll('#postType .post-type-button').forEach(btn => {
        btn.style.backgroundColor = '';
        btn.style.color = '';
    });

    // Reseta os IDs selecionados
    selectedDepartmentId = null;
    selectedPostTypeId = null;
}

// Funções de carregamento e seleção para os botões de departamento e tipo de postagem
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

            button.addEventListener('click', () => selectDepartment(button));
        });
    } catch (error) {
        console.error("Erro ao carregar tags de departamentos:", error.message);
    }
}

function selectDepartment(button) {
    document.querySelectorAll('#department .department-button').forEach(btn => {
        btn.style.backgroundColor = '';
        btn.style.color = '';
    });
    button.style.backgroundColor = 'blue';
    button.style.color = 'white';
    selectedDepartmentId = button.id;
}

async function getPostTypeTags() {
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
}

document.addEventListener('DOMContentLoaded', () => {
    getDepartmentTags();
    getPostTypeTags();
    document.getElementById('post-button').addEventListener('click', insertPost);
});
