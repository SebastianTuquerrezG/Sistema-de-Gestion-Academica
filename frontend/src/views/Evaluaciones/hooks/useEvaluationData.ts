import { useState, useEffect } from 'react';
import { Rubrica, Estudiante } from '../types';
import {
  getAllSubjects,
  getRubricsBySubjectId,
  getStudentsByCourseAndPeriod,
  getAllCourses,
  getAllSemesters,
  getEnrollIdFromStudentAndPeriod,
} from '../../../services/evaluationService';

export const useEvaluationData = () => {
  const [materias, setMaterias] = useState<{ id: number; name: string }[]>([]);
  const [selectedMateria, setSelectedMateria] = useState<string>("");
  const [rubricas, setRubricas] = useState<Rubrica[]>([]);
  const [selectedRubrica, setSelectedRubrica] = useState<Rubrica | null>(null);
  const [periodos, setPeriodos] = useState<string[]>([]);
  const [selectedPeriodo, setSelectedPeriodo] = useState<string>("");
  const [estudiantes, setEstudiantes] = useState<string[]>([]);
  const [selectedEstudiante, setSelectedEstudiante] = useState<string>("");
  const [enrollId, setEnrollId] = useState<number | null>(null);

  // Cargar materias
  useEffect(() => {
    getAllSubjects().then(setMaterias);
  }, []);

  // Obtener rúbricas por materia
  useEffect(() => {
    const materia = materias.find((m) => m.name === selectedMateria);
    if (materia) {
      getRubricsBySubjectId(materia.id).then(setRubricas);
    } else {
      setRubricas([]);
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

  // Obtener estudiantes por curso y período
  useEffect(() => {
    if (selectedPeriodo && selectedMateria) {
      const materia = materias.find((m) => m.name === selectedMateria);
      if (!materia) return;

      getAllCourses().then((cursos) => {
        const curso = cursos.find((c: any) => c.subject === materia.id);
        if (!curso) return;

        getStudentsByCourseAndPeriod(curso.id, selectedPeriodo).then((data) => {
          const nombres = data.map((e: any) =>
            `${e.name ?? ""} ${e.lastName ?? ""}`.trim()
          );
          setEstudiantes(nombres);
        });
      });
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
  };
}; 