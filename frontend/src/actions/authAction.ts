import axios from "axios";
import { clearAuthTokens } from "@/lib/auth";

const API_URL = import.meta.env.VITE_API_AUTH_URL ?? "http://localhost:9090";

export async function authAction(data: { username: string; password: string }) {
    try {
        const response = await axios.post(`${API_URL}/auth/login`, data, {
            headers: { "Content-Type": "application/json" },
        });

        const result = response.data;

        if (response.status === 200 && result.access_token) {
            localStorage.setItem('username', result.username);
            localStorage.setItem('fullname', result.fullName);
            localStorage.setItem('email', result.email);
            localStorage.setItem('roles', JSON.stringify(result.roles));
            localStorage.setItem('auth-token', result.access_token);
            localStorage.setItem('refresh-token', result.refresh_token);
            localStorage.setItem('expires_in', result.expires_in.toString());
            const expiresAt = Date.now() + result.expires_in * 1000;
            localStorage.setItem('expires_at', expiresAt.toString());

            return {
                success: true,
                data: {
                    username: result.username,
                    fullname: result.fullName,
                    email: result.email,
                    roles: result.roles,
                    access_token: result.access_token,
                    refresh_token: result.refresh_token,
                    expires_in: result.expires_in
                }
            };
        } else {
            return {
                success: false,
                error: result.error ?? "Credenciales inválidas",
                field: 'root'
            };
        }
    } catch (error: any) {
        return {
            success: false,
            error: error.response?.data?.error ?? "Error de red o servidor",
            field: 'root'
        };
    }
}

export async function logoutAction() {
    try {
        const access_token = localStorage.getItem('auth-token');
        const refresh_token = localStorage.getItem('refresh-token');

        if (access_token && refresh_token) {
            await axios.post(`${API_URL}/auth/logout`,
                { refreshToken: refresh_token },
                { headers: { "Content-Type": "application/json" } }
            );
        }
        clearAuthTokens();
        return { success: true };
    } catch (error) {
        return {
            success: false,
            error: "Error al cerrar sesión",
        };
    }
}
