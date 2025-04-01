// Mock implementation for authentication actions

export async function authAction(data: { email: string; password: string }) {
    return new Promise((resolve) => {
        setTimeout(() => {
            if (data.email === "test@example.com" && data.password === "password") {
                const mockToken = "mock-auth-token";
                localStorage.setItem('auth-token', mockToken);
                console.log("Mock token saved in localStorage");
                resolve({
                    success: true,
                    data: {
                        token: mockToken,
                        user: { email: data.email, name: "Test User" }
                    }
                });
            } else {
                resolve({
                    success: false,
                    error: "Invalid credentials",
                    field: 'root'
                });
            }
        }, 1000); // Simulate API delay
    });
}

export async function logoutAction() {
    return new Promise((resolve) => {
        setTimeout(() => {
            localStorage.removeItem('auth-token');
            console.log("Mock token removed from localStorage");
            resolve({ success: true });
        }, 500); // Simulate API delay
    });
}
