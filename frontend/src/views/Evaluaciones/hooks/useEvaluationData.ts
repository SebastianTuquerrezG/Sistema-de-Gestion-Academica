import { useState, useEffect } from 'react';
import { Rubrica, Estudiante } from '../types';
import {
  getAllSubjects,
  getRubricsBySubjectId,
  getStudentsByCourseAndPeriod,
  getAllCourses,
  getAllSemesters,
  getEnrollIdFromStudentAndPeriod,
  getRANameById
} from '../../../services/evaluationService';

export const useEvaluationData = () => {
  const [materias, setMaterias] = useState<{ idMateria: number; name: string }[]>([]);
  const [selectedMateria, setSelectedMateria] = useState<string>("");
  const [rubricas, setRubricas] = useState<Rubrica[]>([]);
  const [selectedRubrica, setSelectedRubrica] = useState<Rubrica | null>(null);
  const [periodos, setPeriodos] = useState<string[]>([]);
  const [selectedPeriodo, setSelectedPeriodo] = useState<string>("");
  const [estudiantes, setEstudiantes] = useState<string[]>([]);
  const [selectedEstudiante, setSelectedEstudiante] = useState<string>("");
  const [enrollId, setEnrollId] = useState<number | null>(null);
  const [raName, setRaName] = useState<string>("");

  // Cargar materias
  useEffect(() => {
    getAllSubjects().then(setMaterias);
  }, []);

  // Obtener rúbricas por materia
  useEffect(() => {
    const materia = materias.find((m) => m.name === selectedMateria);
    if (materia) {
      getRubricsBySubjectId(materia.idMateria)
        .then((data) => {
          setRubricas(data);
          console.log("Contenido retornado (string) en el hook:", JSON.stringify(data, null, 2));
        });
    } else {
      setRubricas([]);
      console.log("rubricas")
    }
    setSelectedRubrica(null);
    setSelectedPeriodo("");
    setSelectedEstudiante("");
    setEstudiantes([]);
    setEnrollId(null);
  }, [selectedMateria]);

  // Obtener períodos
  useEffect(() => {
    getAllSemesters().then(setPeriodos);
  }, []);

  // Obtener nombre del RA cuando cambia la rúbrica seleccionada
  useEffect(() => {
    if (selectedRubrica?.raId) {
      getRANameById(selectedRubrica.raId).then(setRaName);
    } else {
      setRaName("");
    }
  }, [selectedRubrica]);

  // Obtener estudiantes por curso y período
  useEffect(() => {
    if (selectedPeriodo && selectedMateria) {
      setSelectedEstudiante("");
      setEnrollId(null);
      const materia = materias.find((m) => m.name === selectedMateria);
      if (!materia) {
        console.log("No se encontró la materia:", selectedMateria, materias);
        return;
      }

      getAllCourses().then((cursos) => {
        const curso = cursos.find((c: any) => c.subject === materia.idMateria);
        if (!curso) {
          console.log("No se encontró el curso para la materia:", materia, cursos);
          return;
        }

        getStudentsByCourseAndPeriod(curso.id, selectedPeriodo).then((data) => {
          console.log('Retorno de getStudentsByCourseAndPeriod:', data);
          const nombres = data.map((e: any) =>
            `${e.name ?? ""} ${e.lastName ?? ""}`.trim()
          );
          setEstudiantes(nombres);
        });
      });
    } else {
      console.log("selectedPeriodo o selectedMateria no definidos", selectedPeriodo, selectedMateria);
    }
  }, [selectedPeriodo, selectedMateria]);

  // Obtener enrollId
  useEffect(() => {
    if (selectedEstudiante && selectedPeriodo) {
      getEnrollIdFromStudentAndPeriod(selectedEstudiante, selectedPeriodo).then(
        (id) => setEnrollId(id)
      );
    }
  }, [selectedEstudiante, selectedPeriodo]);

  const handleSelectRubrica = (nombre: string) => {
    const rubrica = rubricas.find((r) => r.nombreRubrica === nombre) || null;
    setSelectedRubrica(rubrica);
    setSelectedPeriodo("");
    setSelectedEstudiante("");
    setEnrollId(null);
    setEstudiantes([]);
  };

  return {
    materias,
    selectedMateria,
    setSelectedMateria,
    rubricas,
    selectedRubrica,
    periodos,
    selectedPeriodo,
    setSelectedPeriodo,
    estudiantes,
    selectedEstudiante,
    setSelectedEstudiante,
    enrollId,
    handleSelectRubrica,
    raName,
  };
}; 