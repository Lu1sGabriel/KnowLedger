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
        console.log(token);
        const headers = {
            'Content-Type': 'application/json',
            ...(token && { 'Authorization': `Bearer ${token}` })
        };

        const response = await fetch(url, {
            method: method,
            headers: headers,
            body: data ? JSON.stringify(data) : null
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || 'Erro na requisição');
        }

        return await response.json();
    }
};

export default httpService;