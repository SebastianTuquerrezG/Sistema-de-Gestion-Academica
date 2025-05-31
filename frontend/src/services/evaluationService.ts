// services/evaluationService.ts

const baseUrl = "http://localhost:8080";
const token = "Bearer eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJuOWlwWXQxU0RNNzhMeU5lZ3BvekpTSVdQNDZyQURUdG1CLUNpYngyS1FJIn0.eyJleHAiOjE3NDg3MjQzNzksImlhdCI6MTc0ODcxMzU3OSwianRpIjoib25ydHJvOmRhNDcxZWMyLWE1ZDEtNDAwMS1hN2Q3LWIwYzIyOTVlMGI2NyIsImlzcyI6Imh0dHA6Ly9rZXljbG9hay5sb2NhbDo4MTgxL3JlYWxtcy9iYWNrZW5kX3Byb3llY3RvMV9zZ2EiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiODkwMGExNDItZjFhMC00OTg3LThjOWEtNDgwZTRkZGFjMjU2IiwidHlwIjoiQmVhcmVyIiwiYXpwIjoiYmFja2VuZF9zZ2FfY2xpZW50Iiwic2lkIjoiYjAxZDAyOTgtOGY2Yy00NzdkLTkyYTMtMzJjZDI5YzYxZTM1IiwiYWNyIjoiMSIsImFsbG93ZWQtb3JpZ2lucyI6WyJodHRwOi8vbG9jYWxob3N0OjgwODAiLCJodHRwczovL29hdXRoLnBzdG1uLmlvIl0sInJlYWxtX2FjY2VzcyI6eyJyb2xlcyI6WyJDT09SRElOQVRPUl9ST0xFIiwib2ZmbGluZV9hY2Nlc3MiLCJTVFVERU5UX1JPTEUiLCJkZWZhdWx0LXJvbGVzLWJhY2tlbmRfcHJveWVjdG8xX3NnYSIsIkFETUlOX1JPTEUiLCJ1bWFfYXV0aG9yaXphdGlvbiIsIlRFQUNIRVJfUk9MRSJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjp0cnVlLCJuYW1lIjoiT3NjYXIgRmVybmFuZG8gSG95b3MgQ2FydmFqYWwiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhZG1pbl91c2VyIiwiZ2l2ZW5fbmFtZSI6Ik9zY2FyIEZlcm5hbmRvIiwiZmFtaWx5X25hbWUiOiJIb3lvcyBDYXJ2YWphbCIsImVtYWlsIjoib3NmZWhveW9zQHVuaWNhdWNhLmVkdS5jbyJ9.PcoHdR87I-Z8uyr-sNisDSUVuVgcR6YTGzTDWeGkr-Jo_96qfBVutYicebkoztCYPaTazSxeZnjqjW8n-3RxT0Ndsz7nMl2yE-emcv83bk37JmZ8SM_-pQIC0mcOU-b6_EgZvIq4rgTV_Td6ir5589sTFAYubOcyi36XMeVZNc2KMZwQFO47WqajlHbBRCDK-LxLqyJD5FB_yXam41MB-Xp25MpH2ZrQErMCXVU5HDm2FWLhdnTxqGe-rTM2ibWAZo4s2oLN7XeDtF6uQpXaqn3Fx4ySDV8EyoJzscinNqwc4FNQHRsWnjWkZVqnI4yVW3467xoGk8yqt4S6sRMVjg";

const headers = {
  "Content-Type": "application/json",
  "Authorization": token
};

// Obtener todas las materias (subjects)
export async function getAllSubjects(): Promise<any[]> {
  try {
    const response = await fetch(`${baseUrl}/subject`, { headers });
    if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
    return await response.json();
  } catch (error) {
    console.error("Error al obtener materias:", error);
    return [];
  }
}

// Obtener rúbricas por materia (subject)
export async function getRubricsBySubjectId(idMateria: number): Promise<any[]> {
  try {
    console.log("ID materia:", idMateria);
    
    const response = await fetch(`${baseUrl}/rubrics/subject/${idMateria}`, { headers });
    console.log("Response completa:", response);
    
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
    const response = await fetch(`${baseUrl}/students`, { headers });
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
      { headers }
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
    const response = await fetch(`${baseUrl}/enroll`, { headers });
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

// Obtener todos los enrolls
export async function getAllEnrolls(): Promise<any[]> {
  try {
    const response = await fetch(`${baseUrl}/enroll`, { headers });
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
    headers,
    body: JSON.stringify(evaluation),
  });
  if (!response.ok) throw new Error("Error al guardar evaluación");
  return await response.json();
}

// Enviar cada registro de calificación
export async function submitCalificationRegister(register: any): Promise<any> {
  const response = await fetch(`${baseUrl}/calification-register`, {
    method: "POST",
    headers,
    body: JSON.stringify(register),
  });
  if (!response.ok) throw new Error("Error al guardar calificación");
  return await response.json();
}

export async function updateCriterios(
  register: any,
  criterioId: number
): Promise<any> {
  const response = await fetch(`${baseUrl}/criterios?id=${criterioId}`, {
    method: "PUT",
    headers,
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
    const response = await fetch(`${baseUrl}/evaluations/enroll/${enrollId}`, { headers });
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
      { headers }
    );
    if (!response.ok) throw new Error("No hay calificaciones");
    return await response.json();
  } catch (error) {
    console.error("Error al buscar calificaciones:", error);
    return [];
  }
}

export async function getAllCourses(): Promise<any[]> {
  const response = await fetch(`${baseUrl}/course`, { headers });
  if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
  return await response.json();
}

export async function getRANameById(raId: number): Promise<string> {
  try {
    const response = await fetch(`${baseUrl}/ra/${raId}`, { headers });
    if (!response.ok) throw new Error("Error al obtener el nombre del RA");
    const data = await response.json();
    return data?.name || "No definido";
  } catch {
    return "No definido";
  }
}
