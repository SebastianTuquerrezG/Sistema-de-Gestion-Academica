export interface Rubrica {
  id: number;
  idRubrica: number;
  name: string;
  materia: string;
  notaRubrica: number;
  objetivoEstudio: string;
  courseId: number;
  criterios: any[];
  ra_id?: number;
  nombreRubrica: string;
  raId: number;
}

export interface Materia {
  id: string;
  name: string;
  idMateria?: string | number;
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