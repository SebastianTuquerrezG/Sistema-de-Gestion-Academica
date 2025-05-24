export interface Rubrica {
  idRubrica: number;
  nombreRubrica: string;
  materia: string;
  objetivoEstudio: string;
  courseId: number;
}

export interface Materia {
  id: number;
  name: string;
}

export interface EstadisticasFilters {
  materia: string;
  rubrica: string;
  periodo: string;
} 