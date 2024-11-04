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