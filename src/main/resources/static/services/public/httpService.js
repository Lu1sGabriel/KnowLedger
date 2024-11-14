const httpService = {
    get: async (url, includeAuth = true) => {
        return await httpService.request(url, 'GET', undefined, includeAuth);
    },
    post: async (url, data, includeAuth = true) => {
        return await httpService.request(url, 'POST', data, includeAuth);
    },
    put: async (url, data, includeAuth = true) => {
        return await httpService.request(url, 'PUT', data, includeAuth);
    },
    delete: async (url, data, includeAuth = true) => {
        return await httpService.request(url, 'DELETE', data, includeAuth);
    },
    request: async (url, method, data, includeAuth = true) => {
        const headers = {
            'Content-Type': 'application/json',
            ...(includeAuth && localStorage.getItem('authToken')
                ? { 'Authorization': `Bearer ${localStorage.getItem('authToken')}` }
                : {})
        };

        const response = await fetch(url, {
            method,
            headers,
            body: data ? JSON.stringify(data) : undefined
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.error || 'Erro na requisição');
        }

        return await response.json();
    }
};

export default httpService;
