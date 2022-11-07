const execute = async (url: string, method: 'GET' | 'POST' | 'PUT' | 'DELETE', data?) => {
    return await fetch(`http://localhost:5173/api/${url}`, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    });
};

export default execute;