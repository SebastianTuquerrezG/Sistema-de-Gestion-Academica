const API_BASE = "http://localhost:8080/api/RubricEvaluation";

export const getRubrics = async (idStudent :number, idSubject: number, period :string) => {
    const response = await fetch(`${API_BASE}/${idStudent}/${idSubject}/${period}`);
    if (!response.ok) throw new Error("Error al obtener las rúbricas");
    return await response.json();
};

export const getSubjectHeader = async (idSubject : number, period: string, idRubric: number) => {
    const response = await fetch(`${API_BASE}/bySubject/${idSubject}/${period}/${idRubric}`);
    if (!response.ok) throw new Error("Error al obtener la cabecera");
    return await response.json();
};