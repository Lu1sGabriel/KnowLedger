const httpService = {

    get: async (url) => {
        return await httpService.request(url, 'GET');
    },
    post: async (url, data) => {
        return await httpService.request(url, 'POST', data);
    },
    put: async (url, data) => {
        return await httpService.request(url, 'PUT', data);
    },
    delete: async (url, data) => {
        return await httpService.request(url, 'DELETE', data);
    },
    request: async (url, method, data) => {
        const token = localStorage.getItem('authToken');
        const headers = {
            'Content-Type': 'application/json',
            ...(token ? { 'Authorization': `Bearer ${token}` } : {})
        };

        const response = await fetch(url, {
            method,
            headers,
            body: data ? JSON.stringify(data) : undefined
        });

        if (!response.ok) {

            if (response.status === 401) {
                alert('Sessão expirada. Faça login novamente.');
                window.location.href = "/front/users/public/login/login.html";
                return;
            }

            const errorData = await response.json();
            throw new Error(errorData.error || 'Erro na requisição');
        }

        return await response.json();
    }
};

export default httpService;
