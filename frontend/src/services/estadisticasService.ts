import axios from 'axios';

const baseUrl = "http://localhost:8080";
const token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJuOWlwWXQxU0RNNzhMeU5lZ3BvekpTSVdQNDZyQURUdG1CLUNpYngyS1FJIn0.eyJleHAiOjE3NDgxMzMxNDYsImlhdCI6MTc0ODEyMjM0NiwianRpIjoib25ydHJvOjA1MWVlYjg2LTViMmYtNGU3NS04YTZiLTQyY2JmZWMwMjMyNSIsImlzcyI6Imh0dHA6Ly9rZXljbG9hay5sb2NhbDo4MTgxL3JlYWxtcy9iYWNrZW5kX3Byb3llY3RvMV9zZ2EiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiODkwMGExNDItZjFhMC00OTg3LThjOWEtNDgwZTRkZGFjMjU2IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFja2VuZF9zZ2FfY2xpZW50Iiwic2lkIjoiY2YxOTQ4YmEtZWU5ZC00NjE5LTk0NTItZjMzYmI1NGMyYWU1IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJodHRwczovL29hdXRoLnBzdG1uLmlvIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJDT09SRElOQVRPUl9ST0xFIiwib2ZmbGluZV9hY2Nlc3MiLCJTVFVERU5UX1JPTEUiLCJkZWZhdWx0LXJvbGVzLWJhY2tlbmRfcHJveWVjdG8xX3NnYSIsIkFETUlOX1JPTEUiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIlRFQUNIRVJfUk9MRSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiSm9hbiBUdXF1ZXJyZXogR29tZXoiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbl91c2VyIiwiZ2l2ZW5fbmFtZSI6IkpvYW4iLCJmYW1pbHlfbmFtZSI6IlR1cXVlcnJleiBHb21leiIsImVtYWlsIjoiam9hbmdvbWV6c2ViYXNAZ21haWwuY29tIn0.sEQ_ZFpQUp7BlrYsxWq1eXAb9kvYKjhrARX9TEcrwpjq6StuKpvhQZ3piCjCmEHg62LCS5ykJYB_a9HcNR22aVJ4bXHR31hr4zS38n5qp0J_aDeu5kyDm1ajTIXnshgk8B11BfPPGi7wwf26-weEs9sMRzenlCGlFBP70DER-M8W4DJ0Tp6kEKIgATa6az-g7ToQeJhwyMDDSJsqg6VIdAOGU_2D9q9BfbyUp4kMwBcur4gkNK8XTEwpZ-IChG1FeBzRn1c1S4r8tjDw1u0fwq-6EUTtNM-Eu_XB3cKvblZF9IBjR7uvKM_98hCcqQwJDV8NFMxgeTOze6uvGq2weQ";

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
  media: number;
  mediana: number;
  moda: number;
  desviacionEstandar: number;
  maximo: number;
  minimo: number;
  promedioGeneral: number[];
  criterios: {
    nombre: string;
    promedio: number;
  }[];
  histogramas: {
    criterio: string;
    descripcion: string;
    niveles: {
      nivel: string;
      cantidad: number;
    }[];
  }[];
}

export interface HistogramByCriteriaDTO {
  criterio: string;
  descripcion: string;
  niveles: {
    nivel: string;
    cantidad: number;
  }[];
}

export interface CriteriaAverageDTO {
  criterio: string;
  promedio: number;
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