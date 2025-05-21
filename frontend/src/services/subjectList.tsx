const API_BASE = "http://localhost:8080/api/RubricEvaluation";

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
