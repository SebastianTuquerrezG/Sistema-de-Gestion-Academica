// ProtectedRoute.tsx
import { JSX } from "react";
import { Navigate } from "react-router-dom";

export function ProtectedRoute({ children }: { children: JSX.Element }) {
    const token = localStorage.getItem("auth-token");
    if (!token) {
        return <Navigate to="/login" replace />;
    }
    return children;
}