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

export const getSubject = async (idStudent :number, period :string) => {
    const response = await fetch(`${API_BASE}/${idStudent}/${period}`);
    if (!response.ok) throw new Error("Error al obtener las rúbricas");
    return await response.json();
};

export const getPeriod = async (idStudent : number) => {
    const response = await fetch(`${API_BASE}/${idStudent}`);
    if (!response.ok) throw new Error("Error al obtener la cabecera");
    return await response.json();
};
