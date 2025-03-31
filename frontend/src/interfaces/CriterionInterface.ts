import { LevelInterface } from './LevelInterface';

export interface CriterionInterface {
    criterionId: number;
    criterionDescription: string;
    criterionPercentage: number;
    criterionGrade: number;
    criterionComment: string;
    levels: LevelInterface[];
}