import api from "@/services/api";

export async function getAllSubjects(): Promise<any[]> {
  try {
    const { data } = await api.get("/api/rubricas/materias");
    return data;
  } catch (error) {
    console.error("Error al obtener materias:", error);
    return [];
  }
}

export async function getRubricsBySubjectId(idMateria: number): Promise<any[]> {
  try {
    const { data } = await api.get(`/api/rubricas/materias/${idMateria}`);
    return data;
  } catch (error) {
    console.error("Error al obtener rúbricas por materia:", error);
    return [];
  }
}

export async function getAllStudents(): Promise<any[]> {
  try {
    const { data } = await api.get("/students");
    return data;
  } catch (error) {
    console.error("Error al obtener estudiantes:", error);
    return [];
  }
}

export async function getStudentsByCourseAndPeriod(courseId: number, period: string): Promise<any[]> {
  try {
    const { data } = await api.get(`/students/courseAndPeriod/${courseId}/${period}`);
    return data;
  } catch (error) {
    console.error(`Error al obtener estudiantes del curso ${courseId} y periodo ${period}:`, error);
    return [];
  }
}

export async function getAllSemesters(): Promise<string[]> {
  try {
    const { data } = await api.get("/enroll");
    const uniqueSemesters = Array.from(new Set(data.map((enroll: any) => enroll.semester)));
    return uniqueSemesters;
  } catch (error) {
    console.error("Error al obtener períodos:", error);
    return [];
  }
}

export async function getAllEnrolls(): Promise<any[]> {
  try {
    const { data } = await api.get("/enroll");
    return data;
  } catch (error) {
    console.error("Error al obtener enrolls:", error);
    return [];
  }
}

export async function submitEvaluation(evaluation: any): Promise<any> {
  try {
    const { data } = await api.post("/evaluations", evaluation);
    return data;
  } catch (error) {
    console.error("Error al guardar evaluación:", error);
    throw new Error("Error al guardar evaluación");
  }
}

export async function submitCalificationRegister(register: any): Promise<any> {
  try {
    const { data } = await api.post("/calification-register", register);
    return data;
  } catch (error) {
    console.error("Error al guardar calificación:", error);
    throw new Error("Error al guardar calificación");
  }
}

export async function updateCriterios(register: any, criterioId: number): Promise<any> {
  try {
    const { data } = await api.put(`/criteria/${criterioId}`, register);
    return data;
  } catch (error) {
    console.error("Error al actualizar criterio:", error);
    throw new Error("Error al actualizar criterio");
  }
}

export async function getEnrollIdFromStudentAndPeriod(studentName: string, semester: string): Promise<number | null> {
  try {
    const [enrolls, students] = await Promise.all([
      getAllEnrolls(),
      getAllStudents(),
    ]);

    const matchedStudent = students.find(
      (s: any) =>
        `${s.name ?? ""} ${s.lastName ?? ""}`.trim().toLowerCase() ===
        studentName.toLowerCase()
    );

    if (!matchedStudent) {
      console.warn("Estudiante no encontrado:", studentName);
      return null;
    }

    const matchedEnroll = enrolls.find(
      (e: any) => e.student === matchedStudent.id && e.semester === semester
    );

    return matchedEnroll?.id ?? null;
  } catch (error) {
    console.error("Error buscando el enroll:", error);
    return null;
  }
}

export async function getEvaluationByEnroll(enrollId: number): Promise<any | null> {
  try {
    const { data } = await api.get(`/evaluations/enroll/${enrollId}`);
    return data.length > 0 ? data[0] : null;
  } catch (error) {
    console.error("Error al buscar evaluación:", error);
    return null;
  }
}

export async function getCalificationsByEvaluationId(evaluationId: number): Promise<any[]> {
  try {
    const { data } = await api.get(`/calification-register/evaluation/${evaluationId}`);
    return data;
  } catch (error) {
    console.error("Error al buscar calificaciones:", error);
    return [];
  }
}

export async function getAllCourses(): Promise<any[]> {
  try {
    const { data } = await api.get("/course");
    return data;
  } catch (error) {
    console.error("Error al obtener cursos:", error);
    return [];
  }
}

export async function getRANameById(raId: number): Promise<string> {
  try {
    const { data } = await api.get(`/ra/${raId}`);
    return data?.name || "No definido";
  } catch {
    return "No definido";
  }
}

export async function getEvaluationByEnrollAndRubric(enrollId: number, rubricaId: number): Promise<any | null> {
  try {
    const { data } = await api.get(`/evaluations/${enrollId}/${rubricaId}`);
    return data && data.id ? data : null;
  } catch (error) {
    console.error("Error al buscar evaluación por enroll y rúbrica:", error);
    return null;
  }
}
