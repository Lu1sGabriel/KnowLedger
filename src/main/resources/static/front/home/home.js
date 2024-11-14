document.addEventListener('DOMContentLoaded', async function () {
    let currentPage = 0;
    const pageSize = 20;

    async function fetchCategoriesOfPosts() {
        const token = localStorage.getItem('authToken');
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
            renderCategories(data);

        } catch (error) {
            console.error('Erro ao buscar posts: ', error);
        }
    }

    async function fetchPosts(page = 0) {
        const token = localStorage.getItem('authToken');
        try {
            const response = await fetch(`http://localhost:8080/api/posts?page=${page}&size=${pageSize}`, {
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
            renderPosts(data.content);
            renderPagination(data.totalPages, page);

        } catch (error) {
            console.error('Erro ao buscar posts: ', error);
        }
    }

    function renderCategories(categories) {
        const categoryList = document.getElementById('categoryList');
        categories.forEach(category => {
            const categoryItem = document.createElement('li');
            categoryItem.innerHTML = `
                <i class="fa-brands fa-js"></i>
                <a href="${category.name}.html" class="text-decoration-none text-dark">${category.name}</a>
            `;
            categoryList.appendChild(categoryItem);
        });
    }

    function renderPosts(posts) {
        const postsContainer = document.getElementById('postsContainer');
        postsContainer.innerHTML = ''; // Limpa o conteúdo anterior dos posts

        posts.forEach(post => {
            const postCard = document.createElement('div');
            postCard.className = 'card-custom';
            postCard.innerHTML = `
                <div>
                    <h6 class="post-title">${post.title}</h6>
                    <button class="btn btn-link like-btn">
                        <i class="fa-solid fa-heart"></i>
                    </button>
                </div>
                <div class="tags mb-3">
                    <span class="tag-badge">${post.postTypeId}</span>
                </div>
                <div class="author-info">
                    <div>
                        <i class="fa-solid fa-user px-1"></i>
                        <strong class="author-name">${post.user.name}</strong>
                        <span class="text-muted px-2">${new Date(post.createdAt).toLocaleDateString()}</span>
                    </div>
                    <div class="post-interactions">
                        <span class="likes"><i class="fa-solid fa-thumbs-up px-1"></i>10,920</span>
                        <span class="comments"><i class="fa-solid fa-comments px-1"></i>${post.comments.length}</span>
                    </div>
                </div>
            `;
            postsContainer.appendChild(postCard);
        });
    }

    function renderPagination(totalPages, currentPage) {
        const paginationContainer = document.getElementById('paginationContainer');
        paginationContainer.innerHTML = ''; // Limpa a paginação anterior

        for (let i = 0; i < totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.className = 'btn btn-link';
            pageButton.innerText = i + 1;
            pageButton.addEventListener('click', () => fetchPosts(i));
            if (i === currentPage) {
                pageButton.classList.add('active');
            }
            paginationContainer.appendChild(pageButton);
        }
    }

    fetchCategoriesOfPosts();
    fetchPosts(currentPage);
});
