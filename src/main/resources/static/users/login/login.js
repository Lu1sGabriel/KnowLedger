import httpService from '../../services/public/httpService.js';

async function loginUser() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const loginData = {
        email: email,
        password: password,
    };

    try {
        const url = '/users/login';
        const response = await httpService.post(url, loginData);
        console.log(response);
    } catch (error) {
        console.error(error.message);
    }
}

// Define o evento diretamente no botão
document.getElementById("loginButton").addEventListener("click", loginUser);
