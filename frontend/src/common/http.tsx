const env = "http://localhost:8099/igse";

const headers = {
    'Accept': 'application/json',
    'Content-Type': 'application/json'
};

export const get = async (path: string) => {
    try {
        return await fetch(`${env}${path}`, {
            method: "GET",
            headers: {...headers, Authorization: `${sessionStorage.getItem('token')}`}
        });
    } catch (error) {
        console.error(error);
    }
}

export const preloginPost = async (path: string, data: any) => {
    try {
        return await fetch(`${env}${path}`, {
            method: "POST",
            headers: headers,
            body: JSON.stringify(data)
        });
    } catch (error) {
        console.error(error);
    }
}

export const post = async (path: string, data: any) => {
    try {
        return await fetch(`${env}${path}`, {
            method: "POST",
            headers: {...headers, Authorization: `${sessionStorage.getItem('token')}`},
            body: JSON.stringify(data)
        });
    } catch (error) {
        console.error(error);
    }
}

