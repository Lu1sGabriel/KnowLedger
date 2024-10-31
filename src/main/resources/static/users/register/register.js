import httpService from '../../services/public/httpService.js';

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
        console.log(response);
    } catch (error) {
        console.error(error.message);
    }
}

// Define o evento diretamente no botão
document.getElementById("registerButton").addEventListener("click", registerUser);
