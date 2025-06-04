import api from "@/services/api";

const API_BASE = "/api/RubricEvaluation";

export const getRubrics = async (
  idStudent: number,
  idSubject: number,
  period: string
): Promise<any> => {
  try {
    const { data } = await api.get(`${API_BASE}/${idStudent}/${idSubject}/${period}`);
    return data;
  } catch (error) {
    console.error("Error al obtener las rúbricas:", error);
    throw new Error("Error al obtener las rúbricas");
  }
};

export const getSubjectHeader = async (
  idSubject: number,
  period: string,
  idRubric: number
): Promise<any> => {
  try {
    const { data } = await api.get(`${API_BASE}/bySubject/${idSubject}/${period}/${idRubric}`);
    return data;
  } catch (error) {
    console.error("Error al obtener la cabecera:", error);
    throw new Error("Error al obtener la cabecera");
  }
};
