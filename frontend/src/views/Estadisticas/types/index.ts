export interface Rubrica {
  id: string;
  nombreRubrica: string;
  objetivoEstudio: string;
  name?: string;
  ra_id?: number;
}

export interface Materia {
  id: string;
  name: string;
}

export interface EstadisticasFilters {
  materia: string;
  rubrica: string;
  periodo: string;
  resultadoAprendizaje: string;
}

export interface CourseStatsDTO {
  average: number;
  maxScore: number;
  median: number;
  minScore: number;
  raName: string;
  standardDeviation: number;
  studentsCount: number;
} 