import axios from "axios";
import { clearAuthTokens, isTokenExpired, isAuthenticated } from "@/lib/auth";

const API_URL = import.meta.env.VITE_API_URL ?? "http://localhost:8080";

const api = axios.create({
    baseURL: API_URL,
    headers: {
        "Content-Type": "application/json",
    },
});

api.interceptors.request.use((config) => {
    if (!isAuthenticated() || isTokenExpired()) {
        clearAuthTokens();
        window.location.href = "/login";
    }
    config.headers = config.headers || {};
    config.headers["Authorization"] = `Bearer ${localStorage.getItem("auth-token")}`;
    return config;
});

export default api;