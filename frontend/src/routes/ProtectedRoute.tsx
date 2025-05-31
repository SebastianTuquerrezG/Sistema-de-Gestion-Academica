// ProtectedRoute.tsx
import { JSX } from "react";
import { Navigate } from "react-router-dom";

interface ProtectedRouteProps {
    allowedRoles: string[];
    children: JSX.Element;
}

export function ProtectedRoute({ allowedRoles, children }: ProtectedRouteProps) {
    const token = localStorage.getItem("auth-token");

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    const userRole = localStorage.getItem("roles");
    let roles: string[] = [];
    if (userRole) {
        try {
            roles = JSON.parse(userRole);
        } catch {
            roles = [];
        }
    }

    if (!roles || !roles.some(role => allowedRoles.includes(role))) {
        return <Navigate to="/acceso-denegado" replace />;
    }
    return children;
}