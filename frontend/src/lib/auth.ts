export function isAuthenticated(): boolean {
    return !!localStorage.getItem("auth-token");
}

export function isTokenExpired(): boolean {
    const expiresAt = localStorage.getItem('expires_at');
    if (!expiresAt) return true;
    return Date.now() > Number(expiresAt);
}

export function clearAuthTokens() {
    localStorage.removeItem('auth-token');
    localStorage.removeItem('refresh-token');
    localStorage.removeItem('expires_in');
    localStorage.removeItem('expires_at');
    localStorage.removeItem('username');
    localStorage.removeItem('fullname');
    localStorage.removeItem('email');
    localStorage.removeItem('roles');
}