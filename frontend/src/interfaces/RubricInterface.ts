import { CriterionInterface } from './CriterionInterface';

export interface RubricInterface {
  idRubrica: string;
  nombreRubrica: string;
  materia: string;
  notaRubrica: number;
  objetivoEstudio: string;
  criterios: CriterionInterface[];
  estado: string;
}