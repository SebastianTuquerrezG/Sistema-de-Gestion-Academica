
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

function getAuthHeaders(): Record<string, string> {
    const access_token = localStorage.getItem('auth-token');
    if (!access_token) {
        throw new Error("No se encontró el token de autenticación");
    }
    const headers: Record<string, string> = {
        "Content-Type": "application/json",
    };
    if (access_token) {
        headers["Authorization"] = `Bearer ${access_token}`;
    }
    return headers;
}

export const getRubricEvaluation = async (idStudent: string | undefined, idSubject: string | undefined, period: string | undefined, idRubric: string | undefined) => {
    try {
        const response = await fetch(`http://localhost:8080/api/RubricEvaluation/${idStudent}/${idSubject}/${period}/${idRubric}`, {
            headers: getAuthHeaders(),
        });

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
