import { CriterionInterface } from './CriterionInterface';

export interface RubricInterface {
  idRubrica: string;
  nombreRubrica: string;
  materia: number;
  notaRubrica: number;
  objetivoEstudio: string;
  criterios: CriterionInterface[];
  estado: string;
  raId:number;
}