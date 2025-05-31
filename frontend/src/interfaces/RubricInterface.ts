//import { CriterionInterface } from './CriterionInterface';
import { CriterionInterfacePeticion } from "@/interfaces/CriterionInterfacePeticion.ts";

export interface RubricInterface {
  idRubrica: number;
  nombreRubrica: string;
  materia: number;
  nombreMateria?: string;
  notaRubrica: number;
  objetivoEstudio: string;
  criterios: CriterionInterfacePeticion[];
  estado: string;
  raId: number;
}