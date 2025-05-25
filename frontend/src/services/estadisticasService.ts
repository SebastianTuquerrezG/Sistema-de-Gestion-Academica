import axios from 'axios';

const baseUrl = "http://localhost:8080";
const token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJuOWlwWXQxU0RNNzhMeU5lZ3BvekpTSVdQNDZyQURUdG1CLUNpYngyS1FJIn0.eyJleHAiOjE3NDgxNDY4NDUsImlhdCI6MTc0ODEzNjA0NSwianRpIjoib25ydHJvOjk5MDM1YzkxLTUwOGYtNGVlNy1iYTIwLTBkYzUyYzBhYWYyNyIsImlzcyI6Imh0dHA6Ly9rZXljbG9hay5sb2NhbDo4MTgxL3JlYWxtcy9iYWNrZW5kX3Byb3llY3RvMV9zZ2EiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiODkwMGExNDItZjFhMC00OTg3LThjOWEtNDgwZTRkZGFjMjU2IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFja2VuZF9zZ2FfY2xpZW50Iiwic2lkIjoiYWVlNmIwMTktMTUwYi00NTBhLTgzMGUtOGU3MTNhNWFhNDI5IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJodHRwczovL29hdXRoLnBzdG1uLmlvIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJDT09SRElOQVRPUl9ST0xFIiwib2ZmbGluZV9hY2Nlc3MiLCJTVFVERU5UX1JPTEUiLCJkZWZhdWx0LXJvbGVzLWJhY2tlbmRfcHJveWVjdG8xX3NnYSIsIkFETUlOX1JPTEUiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIlRFQUNIRVJfUk9MRSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiSm9hbiBUdXF1ZXJyZXogR29tZXoiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbl91c2VyIiwiZ2l2ZW5fbmFtZSI6IkpvYW4iLCJmYW1pbHlfbmFtZSI6IlR1cXVlcnJleiBHb21leiIsImVtYWlsIjoiam9hbmdvbWV6c2ViYXNAZ21haWwuY29tIn0.sRmhUM4VTN1VRc-i2nP0d02YNMWXOEGAeGNPH31ORI1RHWVTytYg_RBwwItecXcoLYV8egcIdfwYUkEW7i4ztp89lSIGl7x6AhXvBoY_e1HjQB-AVong5ejXYNLoNx0JKL5NsX1xW6Xn7-ATT50-1MMsT2yjJH0O-Y_NfUEm3im0jG8DSSbEheHdvV5fNDyAs7FnzXtszvjyTqvI_CnmqiQjFkBG0fjNB6HH_fv8HBC4N3-JAmoYC--HuBu4HU08tls75XVKrkmI-zXLes5Eg8-yj38Tmj784j2lCsf09BHsbytJCcN8d-tese6c8Ml2Q6_2Ec6NUzuGqEoa1FTiUw";

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