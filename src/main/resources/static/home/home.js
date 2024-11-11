
async function fetchPosts() {
    const token = localStorage.getItem('token'); 
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
        return data;
    } catch (error) {
        console.error('erro ao buscar post ', error);
    }
}
