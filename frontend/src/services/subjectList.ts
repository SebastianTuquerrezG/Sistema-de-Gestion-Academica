const API_BASE = "http://localhost:8080/api/RubricEvaluation";

// export const login = async (username:string, password:string)=> {
//     const response = await fetch("http://localhost:9090/auth/login", {
//         method: "POST",
//         headers: {
//             "Content-Type": "application/json"
//         },
//         body: JSON.stringify({ username, password })
//     });
//
//     const data = await response.json();
//
//     if (!response.ok) {
//         throw new Error("Credenciales incorrectas");
//     }
//
//     localStorage.setItem("access_token", data.access_token);
//
//     return data.access_token;
// };
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
