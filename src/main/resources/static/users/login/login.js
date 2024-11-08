import httpService from '../../services/public/httpService.js';
import toastService from '../../services/public/toastService.js';

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
        toastService.success('Login efetuado com sucesso!');
    } catch (error) {
        toastService.error(error.message);
    }
}

document.getElementById("loginButton").addEventListener("click", loginUser);
