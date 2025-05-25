export interface LoginResponse {
    success: boolean;
    data?: {
        token: string;
        user: {
            id: string;
            username: string;
            name: string;
        };
    };
    error?: string;
    field?: 'username' | 'password' | 'root';
}

export interface ErrorResponse {
    detail?: string;
    message?: string;
    error?: string;
}