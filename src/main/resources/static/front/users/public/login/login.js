import httpService from '../../../../services/public/httpService.js';
import toastService from '../../../../services/public/toastService.js';

async function loginUser() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    const loginData = { email, password };

    try {
        const url = 'http://localhost:8080/users/login';
        const response = await httpService.post(url, loginData);

        localStorage.setItem('authToken', response.token);
        console.log('Token salvo no localStorage:', response.token);

        window.location.href = "../../../home/home.html";


        toastService.success('Login efetuado com sucesso!');
    } catch (error) {
        toastService.error(error.message);

    }
}

document.getElementById("loginButton").addEventListener("click", loginUser);
