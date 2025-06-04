import api from "@/services/api";

export const getSubject = async (idStudent: number, period: string): Promise<any> => {
  try {
    const { data } = await api.get(`/api/RubricEvaluation/${idStudent}/${period}`);

    if (!data || (Array.isArray(data) && data.length === 0)) {
      throw new Error("No se encontraron materias para el estudiante en el periodo seleccionado");
    }

    return data;
  } catch (error) {
    console.error("Error al obtener materias:", error);
    throw new Error("Error al obtener las materias");
  }
};

export const getPeriod = async (idStudent: string | undefined): Promise<any> => {
  if (!idStudent) throw new Error("ID de estudiante no definido");

  try {
    const { data } = await api.get(`/api/RubricEvaluation/${idStudent}`);
    return data;
  } catch (error) {
    console.error("Error al obtener los períodos:", error);
    throw new Error("Error al obtener los períodos");
  }
};

export const parseJwt = (token: string): any => {
  try {
    const base64Payload = token.split(".")[1];
    const decodedPayload = atob(base64Payload);
    return JSON.parse(decodedPayload);
  } catch {
    throw new Error("Token inválido");
  }
};

export const getLoggedUserFullName = (): string => {
  const tokenRaw = localStorage.getItem("auth-token");

  if (!tokenRaw) {
    throw new Error("Token no encontrado en localStorage");
  }

  try {
    const payload = parseJwt(tokenRaw);
    return payload.name;
  } catch (error) {
    console.error("Error al decodificar el token:", error);
    throw new Error("Token inválido");
  }
};

export const getIdStudent = async (): Promise<any> => {
  const fullName = getLoggedUserFullName();

  try {
    const { data } = await api.get(`/api/RubricEvaluation/byName/${fullName}`);
    return data;
  } catch (error) {
    console.error("Error al obtener el estudiante:", error);
    throw new Error("Error al obtener el estudiante");
  }
};
