// services/evaluationService.ts

const baseUrl = "http://localhost:8080";

function getAuthHeaders(): Record<string, string> {
  const access_token = localStorage.getItem('auth-token');
  const headers: Record<string, string> = {
    "Content-Type": "application/json",
  };
  if (access_token) {
    headers["Authorization"] = `Bearer ${access_token}`;
  }
  return headers;
}

// Obtener todas las materias (subjects)
export async function getAllSubjects(): Promise<any[]> {
  try {
    const response = await fetch(`${baseUrl}/api/rubricas/materias`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    const data = await response.json();
    console.log("Respuesta de getAllSubjects:", data);
    return data;
  } catch (error) {
    console.error("Error al obtener materias:", error);
    return [];
  }
}

// Obtener rúbricas por materia (subject)
export async function getRubricsBySubjectId(idMateria: number): Promise<any[]> {
  try {
    const response = await fetch(`${baseUrl}/api/rubricas/materias/${idMateria}`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);

    const data = await response.json();
    console.log("Contenido retornado (string) en el servicio:", JSON.stringify(data, null, 2));

    return data;
  } catch (error) {
    console.error("Error al obtener rúbricas por materia:", error);
    return [];
  }
}

// Obtener todos los estudiantes
export async function getAllStudents(): Promise<any[]> {
  try {
    const response = await fetch(`${baseUrl}/students`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Error al obtener estudiantes:", error);
    return [];
  }
}

// Obtener estudiantes por curso y período
export async function getStudentsByCourseAndPeriod(
  courseId: number,
  period: string
): Promise<any[]> {
  try {
    const response = await fetch(
      `${baseUrl}/students/courseAndPeriod/${courseId}/${period}`,
      { headers: getAuthHeaders() }
    );
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error(
      `Error al obtener estudiantes del curso ${courseId} y periodo ${period}:`,
      error
    );
    return [];
  }
}

// Obtener todos los períodos únicos desde los enrolls
export async function getAllSemesters(): Promise<string[]> {
  try {
    const response = await fetch(`${baseUrl}/enroll`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    const data: { semester: string }[] = await response.json();

    const uniqueSemesters = Array.from(
      new Set(data.map((enroll) => enroll.semester))
    );
    return uniqueSemesters;
  } catch (error) {
    console.error("Error al obtener períodos:", error);
    return [];
  }
}

// Obtener todos los enrolls
export async function getAllEnrolls(): Promise<any[]> {
  try {
    const response = await fetch(`${baseUrl}/enroll`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Error al obtener enrolls:", error);
    return [];
  }
}

// Enviar evaluación principal
export async function submitEvaluation(evaluation: any): Promise<any> {
  const response = await fetch(`${baseUrl}/evaluations`, {
    method: "POST",
    headers: getAuthHeaders(),
    body: JSON.stringify(evaluation),
  });
  if (!response.ok) throw new Error("Error al guardar evaluación");
  return await response.json();
}

// Enviar cada registro de calificación
export async function submitCalificationRegister(register: any): Promise<any> {
  console.log("Registro de calificación enviado:", register);
  const response = await fetch(`${baseUrl}/calification-register`, {
    method: "POST",
    headers: getAuthHeaders(),
    body: JSON.stringify(register),
  });
  if (!response.ok) throw new Error("Error al guardar calificación");
  return await response.json();
}

export async function updateCriterios(
  register: any,
  criterioId: number
): Promise<any> {
  const response = await fetch(`${baseUrl}/criteria/${criterioId}`, {
    method: "PUT",
    headers: getAuthHeaders(),
    body: JSON.stringify(register),
  });

  if (!response.ok) {
    throw new Error("Error al actualizar criterio");
  }

  return await response.json();
}

// Obtener el enrollId cruzando nombre del estudiante y período
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
      (e: any) => e.student === matchedStudent.id && e.semester === semester
    );

    return matchedEnroll?.id ?? null;
  } catch (error) {
    console.error("Error buscando el enroll:", error);
    return null;
  }
}

export async function getEvaluationByEnroll(
  enrollId: number
): Promise<any | null> {
  try {
    const response = await fetch(`${baseUrl}/evaluations/enroll/${enrollId}`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) throw new Error("No hay evaluación previa");
    const data = await response.json();
    return data.length > 0 ? data[0] : null;
  } catch (error) {
    console.error("Error al buscar evaluación:", error);
    return null;
  }
}

export async function getCalificationsByEvaluationId(
  evaluationId: number
): Promise<any[]> {
  try {
    const response = await fetch(
      `${baseUrl}/calification-register/evaluation/${evaluationId}`,
      { headers: getAuthHeaders() }
    );
    if (!response.ok) throw new Error("No hay calificaciones");
    return await response.json();
  } catch (error) {
    console.error("Error al buscar calificaciones:", error);
    return [];
  }
}

export async function getAllCourses(): Promise<any[]> {
  const response = await fetch(`${baseUrl}/course`, {
    headers: getAuthHeaders(),
  });
  if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
  return await response.json();
}

export async function getRANameById(raId: number): Promise<string> {
  try {
    const response = await fetch(`${baseUrl}/ra/${raId}`, { headers: getAuthHeaders() });
    if (!response.ok) throw new Error("Error al obtener el nombre del RA");
    const data = await response.json();
    return data?.name || "No definido";
  } catch {
    return "No definido";
  }
}

export async function getEvaluationByEnrollAndRubric(
  enrollId: number,
  rubricaId: number
): Promise<any | null> {
  try {
    const response = await fetch(`${baseUrl}/evaluations/${enrollId}/${rubricaId}`, {
      headers: getAuthHeaders(),
    });
    if (!response.ok) return null;
    const data = await response.json();
    console.log("Respuesta de getEvaluationByEnrollAndRubric:", data);
    return data && data.id ? data : null;
  } catch (error) {
    console.error("Error al buscar evaluación por enroll y rúbrica:", error);
    return null;
  }
}