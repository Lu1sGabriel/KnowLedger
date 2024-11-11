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
        };

        const response = await fetch(url, {
        });

        if (!response.ok) {
            const errorData = await response.json();
        }

        return await response.json();
    }
};

export default httpService;