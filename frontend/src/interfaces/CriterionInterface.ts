import { LevelInterface } from './LevelInterface';

export interface CriterionInterface {
    idCriterio: number;
    crfDescripcion: string;
    crfPorcentaje: number;
    crfNota: number;
    crfComentario: string;
    niveles: LevelInterface[];
}