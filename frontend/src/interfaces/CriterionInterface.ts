import { LevelInterface } from './LevelInterface';

export interface CriterionInterface {
    idCriterio: number | null;
    crfDescripcion: string;
    crfPorcentaje: number;
    crfNota: number;
    crfComentario: string;
    niveles: LevelInterface[];
}