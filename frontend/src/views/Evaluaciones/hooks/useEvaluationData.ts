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
    getAllSubjects().then((data) => {
      const ordenadas = data.slice().sort((a, b) => a.name.localeCompare(b.name, 'es', { sensitivity: 'base' }));
      setMaterias(ordenadas);
    });
  }, []);

  // Obtener rúbricas por materia
  useEffect(() => {
    const materia = materias.find((m) => m.name === selectedMateria);
    if (materia) {
      getRubricsBySubjectId(materia.idMateria)
        .then((data) => {
          const ordenadas = data.slice().sort((a, b) => a.nombreRubrica.localeCompare(b.nombreRubrica, 'es', { sensitivity: 'base' }));
          setRubricas(ordenadas);
        });
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
    getAllSemesters().then((data) => {
      const ordenados = data.slice().sort((a, b) => {
        const [añoA, semA] = a.split('-').map(Number);
        const [añoB, semB] = b.split('-').map(Number);
        if (añoA !== añoB) return añoB - añoA;
        return semB - semA;
      });
      setPeriodos(ordenados);
    });
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
        return;
      }

      getAllCourses().then((cursos) => {
        const curso = cursos.find((c: any) => c.subject === materia.idMateria);
        if (!curso) {
          return;
        }

        getStudentsByCourseAndPeriod(curso.id, selectedPeriodo).then((data) => {
          const nombres = data.map((e: any) =>
            `${e.name ?? ""} ${e.lastName ?? ""}`.trim()
          );
          const nombresOrdenados = nombres.slice().sort((a, b) => a.localeCompare(b, 'es', { sensitivity: 'base' }));
          setEstudiantes(nombresOrdenados);
        });
      });
    } else {
      return;
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