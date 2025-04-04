// services/evaluationService.ts

const evaluationBaseUrl = 'http://localhost:8080';
const rubricBaseUrl = 'http://localhost:5001/api';

// ✅ Obtener todas las rúbricas completas desde el microservicio de rúbricas
export async function getAllRubrics(): Promise<any[]> {
  try {
    const response = await fetch(`${rubricBaseUrl}/rubricas`);
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Error al obtener rúbricas:", error);
    return [];
  }
}

// ✅ Obtener todos los estudiantes desde el microservicio de evaluaciones
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

// ✅ Obtener todos los períodos únicos desde los enrolls
export async function getAllSemesters(): Promise<string[]> {
  try {
    const response = await fetch(`${evaluationBaseUrl}/enroll`);
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    const data: { semester: string }[] = await response.json();
    const uniqueSemesters = Array.from(new Set(data.map(enroll => enroll.semester)));
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
