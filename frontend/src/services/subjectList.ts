const API_BASE = "http://localhost:8080/api/RubricEvaluation";

function getAuthHeaders(): Record<string, string> {
    const access_token = localStorage.getItem('auth-token');
    if (!access_token) {
        throw new Error("No se encontró el token de autenticación");
    }
    const headers: Record<string, string> = {
        "Content-Type": "application/json",
    };
    if (access_token) {
        headers["Authorization"] = `Bearer ${access_token}`;
    }
    return headers;
}

export const getSubject = async (idStudent :number, period :string) => {
    const response = await fetch(`${API_BASE}/${idStudent}/${period}`,{
        headers: getAuthHeaders(),
        }
    );
    if (!response.ok) throw new Error("Error al obtener las rúbricas");
    return await response.json();
};

export const getPeriod = async (idStudent : string|undefined) => {
    const response = await fetch(`${API_BASE}/${idStudent}`,{
        headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error("Error al obtener la cabecera");
    return await response.json();
};

export const parseJwt =(token: string): any =>{
    try {
        const base64Payload = token.split('.')[1]; // El segundo segmento es el payload
        const decodedPayload = atob(base64Payload); // Decodifica Base64
        return JSON.parse(decodedPayload); // Convierte a objeto

    } catch (e) {
        throw new Error("Token inválido");
    }

};

export const getLoggedUserFullName = (): string => {
    const tokenRaw = localStorage.getItem("auth-token");

    if (!tokenRaw) {
        throw new Error("Token no encontrado en localStorage");
    }

    try {
        const base64Payload = tokenRaw.split(".")[1];
        const payload = JSON.parse(atob(base64Payload));
        return payload.name; // ← Este es el campo correcto según tu token
    } catch (error) {
        console.error("Error al decodificar el token:", error);
        throw new Error("Token inválido");
    }
};



export const getIdStudent = async () => {
    const fullName = getLoggedUserFullName();

    const response = await fetch(`${API_BASE}/byName/${fullName}`,{
        headers: getAuthHeaders(),
    });

    if (!response.ok) {
        throw new Error("Error al obtener el estudiante");
    }

    return await response.json();
};







