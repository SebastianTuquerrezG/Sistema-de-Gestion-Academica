import { CriterionInterface } from './CriterionInterface';

export interface RubricInterface {
  rubricaId: string;
  nombreRubrica: string;
  materia: string;
  notaRubrica: number;
  objetivoEstudio: string;
  criterios: CriterionInterface[];
  estado: string;
}