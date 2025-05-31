import axios from 'axios';

const baseUrl = "http://localhost:8080";
const token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJuOWlwWXQxU0RNNzhMeU5lZ3BvekpTSVdQNDZyQURUdG1CLUNpYngyS1FJIn0.eyJleHAiOjE3NDg3MjQzNzksImlhdCI6MTc0ODcxMzU3OSwianRpIjoib25ydHJvOmRhNDcxZWMyLWE1ZDEtNDAwMS1hN2Q3LWIwYzIyOTVlMGI2NyIsImlzcyI6Imh0dHA6Ly9rZXljbG9hay5sb2NhbDo4MTgxL3JlYWxtcy9iYWNrZW5kX3Byb3llY3RvMV9zZ2EiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiODkwMGExNDItZjFhMC00OTg3LThjOWEtNDgwZTRkZGFjMjU2IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFja2VuZF9zZ2FfY2xpZW50Iiwic2lkIjoiYjAxZDAyOTgtOGY2Yy00NzdkLTkyYTMtMzJjZDI5YzYxZTM1IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJodHRwczovL29hdXRoLnBzdG1uLmlvIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJDT09SRElOQVRPUl9ST0xFIiwib2ZmbGluZV9hY2Nlc3MiLCJTVFVERU5UX1JPTEUiLCJkZWZhdWx0LXJvbGVzLWJhY2tlbmRfcHJveWVjdG8xX3NnYSIsIkFETUlOX1JPTEUiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIlRFQUNIRVJfUk9MRSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiT3NjYXIgRmVybmFuZG8gSG95b3MgQ2FydmFqYWwiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbl91c2VyIiwiZ2l2ZW5fbmFtZSI6Ik9zY2FyIEZlcm5hbmRvIiwiZmFtaWx5X25hbWUiOiJIb3lvcyBDYXJ2YWphbCIsImVtYWlsIjoib3NmZWhveW9zQHVuaWNhdWNhLmVkdS5jbyJ9.PcoHdR87I-Z8uyr-sNisDSUVuVgcR6YTGzTDWeGkr-Jo_96qfBVutYicebkoztCYPaTazSxeZnjqjW8n-3RxT0Ndsz7nMl2yE-emcv83bk37JmZ8SM_-pQIC0mcOU-b6_EgZvIq4rgTV_Td6ir5589sTFAYubOcyi36XMeVZNc2KMZwQFO47WqajlHbBRCDK-LxLqyJD5FB_yXam41MB-Xp25MpH2ZrQErMCXVU5HDm2FWLhdnTxqGe-rTM2ibWAZo4s2oLN7XeDtF6uQpXaqn3Fx4ySDV8EyoJzscinNqwc4FNQHRsWnjWkZVqnI4yVW3467xoGk8yqt4S6sRMVjg";

const headers = {
  "Content-Type": "application/json",
  "Authorization": token
};

export interface FilterStatsDTO {
  subjectName: string;
  rubricName: string;
  period: string;
}

export interface CourseStatsDTO {
  raName: string;
  average: number;
  median: number;
  standardDeviation: number;
  minScore: number;
  maxScore: number;
  studentsCount: number;
}

export interface HistogramByCriteriaDTO {
  criteriaId: number;
  criteriaDescription: string;
  countNivel1: number;
  countNivel2: number;
  countNivel3: number;
}

export interface CriteriaAverageDTO {
  idCriterio: number;
  criterioDescripcion: string;
  promedioNota: number;
}

export const getStatsByRubric = async (filter: FilterStatsDTO): Promise<CourseStatsDTO> => {
  try {
    const response = await axios.post(`${baseUrl}/stats/by-rubric`, filter, { headers });
    return response.data;
  } catch (error) {
    console.error('Error al obtener estadísticas por rúbrica:', error);
    throw new Error('Error al obtener las estadísticas por rúbrica');
  }
};

export const getHistogramByCriteria = async (filter: FilterStatsDTO): Promise<HistogramByCriteriaDTO[]> => {
  try {
    const response = await axios.post(`${baseUrl}/stats/by-criteria`, filter, { headers });
    return response.data;
  } catch (error) {
    console.error('Error al obtener histograma por criterios:', error);
    throw new Error('Error al obtener el histograma por criterios');
  }
};

export const getCriteriaAverages = async (filter: FilterStatsDTO): Promise<CriteriaAverageDTO[]> => {
  try {
    const response = await axios.post(`${baseUrl}/stats/criteria-stats`, filter, { headers });
    return response.data;
  } catch (error) {
    console.error('Error al obtener promedios por criterio:', error);
    throw new Error('Error al obtener los promedios por criterio');
  }
};

export const getEstadisticasPorMateria = async (materiaId: string): Promise<CourseStatsDTO> => {
  try {
    const response = await axios.get(`${baseUrl}/estadisticas/materia/${materiaId}`);
    return response.data;
  } catch (error) {
    console.error('Error al obtener estadísticas por materia:', error);
    throw new Error('Error al obtener las estadísticas de la materia');
  }
};

export const getEstadisticasPorRubrica = async (rubricaId: string): Promise<CourseStatsDTO> => {
  try {
    const response = await axios.get(`${baseUrl}/estadisticas/rubrica/${rubricaId}`);
    return response.data;
  } catch (error) {
    console.error('Error al obtener estadísticas por rúbrica:', error);
    throw new Error('Error al obtener las estadísticas de la rúbrica');
  }
};

export const getEstadisticasPorPeriodo = async (periodoId: string): Promise<CourseStatsDTO> => {
  try {
    const response = await axios.get(`${baseUrl}/estadisticas/periodo/${periodoId}`);
    return response.data;
  } catch (error) {
    console.error('Error al obtener estadísticas por período:', error);
    throw new Error('Error al obtener las estadísticas del período');
  }
}; 