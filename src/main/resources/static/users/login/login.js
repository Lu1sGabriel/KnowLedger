import httpService from '../../services/public/httpService.js';

async function loginUser() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const loginData = {
        email: email,
        password: password,
    };

    try {
        // fazer IF caso ja tenha o token, só redirecionar direto

        const url = '/users/login';
        const response = await httpService.post(url, loginData);

        if (response.token) {
            localStorage.setItem('authToken', response.token);
            console.log('Token salvo no localStorage:', response.token);


            window.location.href = "http://localhost:8080/home/home.html";
        } else {
            console.error('Token não encontrado na resposta');
        }

    } catch (error) {
        console.error(error.message);
    }
}

// Define o evento diretamente no botão
document.getElementById("loginButton").addEventListener("click", loginUser);
