import { LevelInterface } from './LevelInterface';

export interface CriterionInterfacePeticion {
    crfDescripcion: string;
    crfPorcentaje: number;
    crfNota: number;
    crfComentario: string;
    niveles: LevelInterface[] ;
    idRubrica: number | null;
}