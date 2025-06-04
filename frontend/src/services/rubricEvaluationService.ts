import api from "@/services/api";

export type PerformanceLevelResponseViewDTO = {
    description: string;
    range: string;
};

export type CriteriaResponseViewDTO = {
    descriptionCriteria: string;
    levels: PerformanceLevelResponseViewDTO[];
    percentage: number;
};

export type CalificationResponseViewDTO = {
    calification: number;
    message: string;
    level: string;
};

export type EvaluationResponseViewDTO = {
    description: string;
    criterias: CriteriaResponseViewDTO[];
    califications: CalificationResponseViewDTO[];
    totalScore: number;
};

export const getRubricEvaluation = async (
  idStudent: string | undefined,
  idSubject: string | undefined,
  period: string | undefined,
  idRubric: string | undefined
): Promise<EvaluationResponseViewDTO> => {
  try {
    const { data } = await api.get(
      `/api/RubricEvaluation/${idStudent}/${idSubject}/${period}/${idRubric}`
    );
    return data;
  } catch (error: any) {
    if (error.response?.status === 404) {
      throw new Error(
        "Por favor comuníquese con el docente o repórtelo a soportetecnico@unicauca.edu.co"
      );
    }

    throw new Error(
      error?.message || "Error inesperado al conectar con el servidor."
    );
  }
};