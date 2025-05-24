import { CriterionInterface } from './CriterionInterface';

export interface RubricInterface {
  idRubrica: string;
  nombreRubrica: string;
  materia: number;
  nombreMateria?: string;
  notaRubrica: number;
  objetivoEstudio: string;
  criterios: CriterionInterface[];
  estado: string;
  raId:number;
}