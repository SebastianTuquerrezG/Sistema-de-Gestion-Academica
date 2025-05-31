export async function authAction(data: { username: string; password: string }) {
    try {
        const response = await fetch("http://localhost:9090/auth/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        });

        const result = await response.json();

        if (response.ok && result.access_token) {
            localStorage.setItem('username', result.username);
            localStorage.setItem('fullname', result.fullName);
            localStorage.setItem('email', result.email);
            localStorage.setItem('roles', JSON.stringify(result.roles));
            localStorage.setItem('auth-token', result.access_token);
            localStorage.setItem('refresh-token', result.refresh_token);
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
                error: result.error || "Credenciales inválidas",
                field: 'root'
            };
        }
    } catch (error) {
        return {
            success: false,
            error: "Error de red o servidor",
            field: 'root'
        };
    }
}

export async function logoutAction() {
    try {
        const access_token = localStorage.getItem('auth-token');
        const refresh_token = localStorage.getItem('refresh-token');

        if (access_token && refresh_token) {
            await fetch("http://localhost:9090/auth/logout", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ refreshToken: refresh_token }),

            });
        }
        localStorage.removeItem('username');
        localStorage.removeItem('fullname');
        localStorage.removeItem('email');
        localStorage.removeItem('roles');
        localStorage.removeItem('auth-token');
        localStorage.removeItem('refresh-token');

        return { success: true };
    } catch (error) {
        return {
            success: false,
            error: "Error al cerrar sesión",
        };
    }
}