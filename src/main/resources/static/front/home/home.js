document.addEventListener('DOMContentLoaded', async function() {
    async function fetchPosts() {
        const token = localStorage.getItem('authToken'); 
        console.log(token);
        try {
            const response = await fetch('http://localhost:8080/post-types/getAll', {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
            });

            if (!response.ok) {
                throw new Error(`Erro: ${response.status} - ${response.statusText}`);
            }

            const data = await response.json();
            console.log(data); 

            renderPosts(data);
        } catch (error) {
            console.error('Erro ao buscar posts: ', error);
        }
    }

    function renderPosts(posts) {
        const postsContainer = document.querySelector('.card-custom')
        if (!postsContainer) {
            console.error('Elemento card não encontrado');
            return;
        }

        postsContainer.innerHTML = ''; // Limpa o container antes de inserir novos posts

        posts.forEach(post => {
            const postElement = document.createElement('div');
            postElement.classList.add('card', 'mb-3', 'p-3');

            postElement.innerHTML = `
                <p>${post.description}</p>
            `;

            postsContainer.appendChild(postElement);
        });
    }

    fetchPosts();
});
