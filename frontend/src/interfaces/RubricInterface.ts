import { CriterionInterface } from './CriterionInterface';

export interface RubricInterface {
  rubricId: string;
  rubricName: string;
  subject: string;
  rubricGrade: number;
  studyObjective: string;
  criteria: CriterionInterface[];
  status: string;
}