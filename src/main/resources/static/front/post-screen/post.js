import httpService from "../../services/public/httpService";


async function insertPost() {

    const textPost = document.querySelector('.text-session').value;
    // const typePost = document.querySelector('.type-session').value;
    const title = document.querySelector('.input-duvida').value;
    
    const post = {
        title: title,
        content: content,
        type: typePost
    };

    try {
        const response = await httpService.post('', post); // backend sem url pra post
        console.log(response);
    } catch (error) {
        console.error(error.message);
    }
}

async function getDepartmentTags() {

    try {
        console.log("Carregar tags de posts");
        const apiUrl = 'http://localhost:8080/departments/getAll';
        const response = await httpService.get(apiUrl);
        console.log(response);

    }catch (error) {
        console.error(error.message);
    }

}

document.addEventListener('DOMContentLoaded', getDepartmentTags);