// services/evaluationService.ts

const evaluationBaseUrl = 'http://localhost:8080';

// ✅ Obtener todas las materias (subjects)
export async function getAllSubjects(): Promise<any[]> {
  try {
    const response = await fetch(`${evaluationBaseUrl}/subject`);
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Error al obtener materias:", error);
    return [];
  }
}

// ✅ Obtener rúbricas por materia (subject)
export async function getRubricsBySubjectId(subjectId: number): Promise<any[]> {
  try {
    const response = await fetch(`${evaluationBaseUrl}/rubrics/subject/${subjectId}`);
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Error al obtener rúbricas por materia:", error);
    return [];
  }
}

// ✅ Obtener todos los estudiantes
export async function getAllStudents(): Promise<any[]> {
  try {
    const response = await fetch(`${evaluationBaseUrl}/students`);
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Error al obtener estudiantes:", error);
    return [];
  }
}

// ✅ Obtener estudiantes por curso y período
export async function getStudentsByCourseAndPeriod(courseId: number, period: string): Promise<any[]> {
  try {
    const response = await fetch(`${evaluationBaseUrl}/students/courseAndPeriod/${courseId}/${period}`);
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error(`Error al obtener estudiantes del curso ${courseId} y periodo ${period}:`, error);
    return [];
  }
}


// ✅ Obtener todos los períodos únicos desde los enrolls
export async function getAllSemesters(): Promise<string[]> {
  try {
    const response = await fetch(`${evaluationBaseUrl}/enroll`);
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    const data: { semester: string }[] = await response.json();

    const uniqueSemesters = Array.from(
      new Set(data.map((enroll) => enroll.semester))
    );
    console.log("🎓 Semestres únicos:", uniqueSemesters);
    return uniqueSemesters;
  } catch (error) {
    console.error("Error al obtener períodos:", error);
    return [];
  }
}

// ✅ Obtener todos los enrolls
export async function getAllEnrolls(): Promise<any[]> {
  try {
    const response = await fetch(`${evaluationBaseUrl}/enroll`);
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Error al obtener enrolls:", error);
    return [];
  }
}

// ✅ Enviar evaluación principal
export async function submitEvaluation(evaluation: any): Promise<any> {
  const response = await fetch(`${evaluationBaseUrl}/evaluations`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(evaluation),
  });
  if (!response.ok) throw new Error('Error al guardar evaluación');
  return await response.json();
}

// ✅ Enviar cada registro de calificación
export async function submitCalificationRegister(register: any): Promise<any> {
  const response = await fetch(`${evaluationBaseUrl}/calification-register`, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(register),
  });
  if (!response.ok) throw new Error('Error al guardar calificación');
  return await response.json();
}

// ✅ Obtener el enrollId cruzando nombre del estudiante y período
export async function getEnrollIdFromStudentAndPeriod(
  studentName: string,
  semester: string
): Promise<number | null> {
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
      (e: any) =>
        e.student === matchedStudent.id && e.semester === semester
    );

    return matchedEnroll?.id ?? null;
  } catch (error) {
    console.error("Error buscando el enroll:", error);
    return null;
  }
}

export async function getEvaluationByEnrollAndRubric(enrollId: number, rubricId: number): Promise<any> {
  try {
    const response = await fetch(`http://localhost:8080/evaluations/enroll/${enrollId}`);
    if (!response.ok) throw new Error("No hay evaluación previa");
    const data = await response.json();
    return data.find((e: any) => e.rubric === rubricId) || null;
  } catch (error) {
    console.error("Error al buscar evaluación:", error);
    return null;
  }
}

export async function getCalificationsByEvaluationId(evaluationId: number): Promise<any[]> {
  try {
    const response = await fetch(`http://localhost:8080/calification-register/evaluation/${evaluationId}`);
    if (!response.ok) throw new Error("No hay calificaciones");
    return await response.json();
  } catch (error) {
    console.error("Error al buscar calificaciones:", error);
    return [];
  }
}


