document.addEventListener('DOMContentLoaded', async function() {
    async function fetchCategoriesOfPosts() {
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

            renderCategories(data);

        } catch (error) {
            console.error('Erro ao buscar posts: ', error);
        }
    }

    function renderCategories(categories) {

        categories.forEach(categories => {

            const categoryItem = document.createElement('li');
            categoryItem.innerHTML = `
                <i class="fa-brands fa-js"></i>
                <a href=${categories.name}.html class="text-decoration-none text-dark">${categories.name}</a>
            `;

            categoryList.appendChild(categoryItem);

        });
    }

    fetchCategoriesOfPosts();
});
