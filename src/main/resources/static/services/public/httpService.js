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
            try {
                const errorData = await response.json();
                throw new Error(errorData.message || 'Erro na requisição');
            } catch (e) {
                throw new Error('Erro desconhecido na requisição');
            }
        }

        return await response.json();
    }
};

export default httpService;
