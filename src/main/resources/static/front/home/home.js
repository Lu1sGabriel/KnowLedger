

async function fetchPosts() {
    const token = localStorage.getItem('authToken'); 
    console.log(token)
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
            return;
        }

        const data = await response.json();
        renderPosts(data);
        console.log(data); 
    } catch (error) {
        console.error('erro ao buscar post ', error);
    }
}


function renderPosts(posts) {
    const postsContainer = document.getElementById('.section-news');
    postsContainer.innerHTML = '';  

    if (posts.length === 0) {
        postsContainer.innerHTML = '<p>Nenhum post encontrado.</p>';
        return;
    }

    newsSection.innerHTML = '';

    posts.forEach(post => {
        const postElement = document.createElement('div');
        postElement.classList.add('post');
        postElement.innerHTML = `
            <h2>${post.title}</h2>
            <p>${post.content}</p>
        `;
        postsContainer.appendChild(postElement);
    });
}

document.addEventListener('DOMContentLoaded', fetchPosts);