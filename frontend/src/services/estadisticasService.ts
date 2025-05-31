import axios from 'axios';

const baseUrl = "http://localhost:8080";

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

function getAuthHeaders(): Record<string, string> {
  const access_token = localStorage.getItem('auth-token');
  const headers: Record<string, string> = {
    "Content-Type": "application/json",
  };
  if (access_token) {
    headers["Authorization"] = `Bearer ${access_token}`;
  }
  return headers;
}

export const getStatsByRubric = async (filter: FilterStatsDTO): Promise<CourseStatsDTO> => {
  try {
    const response = await axios.post(`${baseUrl}/stats/by-rubric`, filter, { headers: getAuthHeaders() });
    return response.data;
  } catch (error) {
    console.error('Error al obtener estadísticas por rúbrica:', error);
    throw new Error('Error al obtener las estadísticas por rúbrica');
  }
};

export const getHistogramByCriteria = async (filter: FilterStatsDTO): Promise<HistogramByCriteriaDTO[]> => {
  try {
    const response = await axios.post(`${baseUrl}/stats/by-criteria`, filter, { headers: getAuthHeaders() });
    return response.data;
  } catch (error) {
    console.error('Error al obtener histograma por criterios:', error);
    throw new Error('Error al obtener el histograma por criterios');
  }
};

export const getCriteriaAverages = async (filter: FilterStatsDTO): Promise<CriteriaAverageDTO[]> => {
  try {
    const response = await axios.post(`${baseUrl}/stats/criteria-stats`, filter, { headers: getAuthHeaders() });
    return response.data;
  } catch (error) {
    console.error('Error al obtener promedios por criterio:', error);
    throw new Error('Error al obtener los promedios por criterio');
  }
};

export const getEstadisticasPorMateria = async (materiaId: string): Promise<CourseStatsDTO> => {
  try {
    const response = await axios.get(`${baseUrl}/estadisticas/materia/${materiaId}`, { headers: getAuthHeaders() });
    return response.data;
  } catch (error) {
    console.error('Error al obtener estadísticas por materia:', error);
    throw new Error('Error al obtener las estadísticas de la materia');
  }
};

export const getEstadisticasPorRubrica = async (rubricaId: string): Promise<CourseStatsDTO> => {
  try {
    const response = await axios.get(`${baseUrl}/estadisticas/rubrica/${rubricaId}`, { headers: getAuthHeaders() });
    return response.data;
  } catch (error) {
    console.error('Error al obtener estadísticas por rúbrica:', error);
    throw new Error('Error al obtener las estadísticas de la rúbrica');
  }
};

export const getEstadisticasPorPeriodo = async (periodoId: string): Promise<CourseStatsDTO> => {
  try {
    const response = await axios.get(`${baseUrl}/estadisticas/periodo/${periodoId}`, { headers: getAuthHeaders() });
    return response.data;
  } catch (error) {
    console.error('Error al obtener estadísticas por período:', error);
    throw new Error('Error al obtener las estadísticas del período');
  }
};
