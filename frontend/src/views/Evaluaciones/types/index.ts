export interface Descriptor {
  nivel: string;
  texto?: string;
  superior: number;
  inferior: number;
  nivelDescripcion: string;
}

export interface Criterio {
  idCriterio: number;
  criterio: string;
  porcentaje: number;
  descriptores: Descriptor[];
}

export interface Rubrica {
  id: number;
  name: string;
  materia: string;
  notaRubrica: number;
  objetivoEstudio: string;
  courseId: number;
  criterios: {
    idCriterio: number;
    crfDescripcion: string;
    crfPorcentaje: number;
    niveles: {
      nivel: string;
      nivelDescripcion: string;
      rangoNota: string;
    }[];
  }[];
  ra_id?: number;
}

export interface Estudiante {
  id: number;
  name: string;
  lastName: string;
  identification: string;
}

export interface NotificationType {
  type: "error" | "info";
  title: string;
  message: string;
}

export const COLORS_BASE = ["#2e2ebe", "#22229e", "#13137c", "#0d0d66", "#050545"]; 