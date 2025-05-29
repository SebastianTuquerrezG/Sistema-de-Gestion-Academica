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

export const getRubrics = async (idStudent :number, idSubject: number, period :string) => {
    const response = await fetch(`${API_BASE}/${idStudent}/${idSubject}/${period}`,{
        headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error("Error al obtener las rúbricas");
    return await response.json();
};

export const getSubjectHeader = async (idSubject : number, period: string, idRubric: number) => {
    const response = await fetch(`${API_BASE}/bySubject/${idSubject}/${period}/${idRubric}`,{
        headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error("Error al obtener la cabecera");
    return await response.json();
};