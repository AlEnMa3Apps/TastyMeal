

export interface LoginData {
    username: string,
    password: string,
};

export interface CreateData {

    username: string,
    password: string,
    email: string,
    firstname: string,
    lastName: string,
}

export interface loginResponseData {
    token: string,
    role: string
}

