
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

export const getRubricEvaluation = async (idStudent: string | undefined, idSubject: string | undefined, period: string | undefined, idRubric: string | undefined) => {
    try {
        console.log("Datos recibidos:",{ idStudent, idSubject, period, idRubric });
        const response = await fetch(`http://localhost:8080/api/RubricEvaluation/${idStudent}/${idSubject}/${period}/${idRubric}`);

        if (response.status === 404) {
            throw new Error("Por favor comuníquese con el docente o repórtelo a soportetecnico@unicauca.edu.co");
        }

        if (!response.ok) {
            throw new Error("Hubo un problema al obtener la evaluación. Contacte al soporte.");
        }

        const data = await response.json();
        return data;
    } catch (error: any) {
        // Solo reemplaza si no hay mensaje útil
        throw new Error(error?.message || "Error inesperado al conectar con el servidor.");
    }
};
/*http://localhost:2020/RubricEvaluation/{idStudent}/{idSubject}/{semester}/{idRubric}
*/
