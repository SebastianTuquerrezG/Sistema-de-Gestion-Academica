import axios from 'axios';

const baseUrl = "http://localhost:8080";
const token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJuOWlwWXQxU0RNNzhMeU5lZ3BvekpTSVdQNDZyQURUdG1CLUNpYngyS1FJIn0.eyJleHAiOjE3NDgxNTQ1OTYsImlhdCI6MTc0ODE0Mzc5NiwianRpIjoib25ydHJvOjU0M2VmYjBjLWM5MmUtNGMyYy1iZWNmLTM0ZGQyNjI4YmYyZiIsImlzcyI6Imh0dHA6Ly9rZXljbG9hay5sb2NhbDo4MTgxL3JlYWxtcy9iYWNrZW5kX3Byb3llY3RvMV9zZ2EiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiODkwMGExNDItZjFhMC00OTg3LThjOWEtNDgwZTRkZGFjMjU2IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFja2VuZF9zZ2FfY2xpZW50Iiwic2lkIjoiZDQ3NGViMTQtZTQwNy00ODE4LWFmNjYtY2I1NzEzMTA4NTE5IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJodHRwczovL29hdXRoLnBzdG1uLmlvIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJDT09SRElOQVRPUl9ST0xFIiwib2ZmbGluZV9hY2Nlc3MiLCJTVFVERU5UX1JPTEUiLCJkZWZhdWx0LXJvbGVzLWJhY2tlbmRfcHJveWVjdG8xX3NnYSIsIkFETUlOX1JPTEUiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIlRFQUNIRVJfUk9MRSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiSm9hbiBUdXF1ZXJyZXogR29tZXoiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbl91c2VyIiwiZ2l2ZW5fbmFtZSI6IkpvYW4iLCJmYW1pbHlfbmFtZSI6IlR1cXVlcnJleiBHb21leiIsImVtYWlsIjoiam9hbmdvbWV6c2ViYXNAZ21haWwuY29tIn0.mAcaxtFNM4zIAHfYAtZHz6-RZDGBDwJeRRr2F352CnivJWw_TxQIAQtInHDALz0ZU2G-WGHr3GYoOhsxBWtITX9CwRj_6r1JVoFSPWLvSpLLHaHYb70D40umPG-4x74nVfZmnm6-eAxwr5nXmdlZSD1jLqhXcAUs96tKZGtu5xMKq17OcNMlWc9XRG1IBBLpEiiiSIbHCWlkRK2gpx_MRBvNe1Lmjp9JJOI_aNE6QVZN7hABRJV7vAfmXpdLDV33OqtQKpOD7E7zVhQSyLNOT-lyZtE1ddMD-CHSs9VPYRvzyhpzqYkPo3yHwC9lzNYqrYolipya-wmibmdD-oNCBA";

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