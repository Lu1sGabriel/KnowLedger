import httpService from '../../../../services/public/httpService.js';
import toastService from '../../../../services/public/toastService.js';

async function registerUser() {

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;
    const confirmedPassword = document.getElementById("confirmedPassword").value;

    const registerData = {
        name: name,
        email: email,
        password: password,
        confirmedPassword: confirmedPassword
    };

    try {
        const url = '/users/register';
        const response = await httpService.post(url, registerData);
        toastService.success('Cadastrado com sucesso!');
    } catch (error) {
        toastService.error(error.message);
    }
}

document.getElementById("registerButton").addEventListener("click", registerUser);
