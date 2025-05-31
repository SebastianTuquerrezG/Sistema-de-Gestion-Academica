//import { CriterionInterface } from './CriterionInterface';
import {CriterionInterfacePeticion} from "@/interfaces/CriterionInterfacePeticion.ts";

export interface RubricInterfacePeticion {
  idRubrica: number| null;
  nombreRubrica: string;
  idMateria: number;
  notaRubrica: number;
  objetivoEstudio: string;
  criterios: CriterionInterfacePeticion[];
  estado: string;
  raId:number;
}