import Evaluaciones from "../views/Evaluaciones/Evaluation";

const baseUrl = 'http://localhost:8080';

export async function getAllSubjects(): Promise<string[]> {
    try {
      const response = await fetch(`${baseUrl}/subject`);
      if (!response.ok) throw new Error(`HTTP error: ${response.status}`);
      const data = await response.json();
      return data.map((subject: any) => subject.name); // Extraer el nombre
    } catch (error) {
      console.error("Error al obtener materias:", error);
      return [];
    }
  }
