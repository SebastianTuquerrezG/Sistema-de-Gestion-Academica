import { useState, useEffect } from 'react';
import { Rubrica, Materia } from '../types';
import {
  getAllSubjects,
  getRubricsBySubjectId,
  getAllSemesters,
} from '../../../services/evaluationService';

export const useEstadisticasData = () => {
  const [materias, setMaterias] = useState<Materia[]>([]);
  const [selectedMateria, setSelectedMateria] = useState<string>("");
  const [rubricas, setRubricas] = useState<Rubrica[]>([]);
  const [selectedRubrica, setSelectedRubrica] = useState<Rubrica | null>(null);
  const [periodos, setPeriodos] = useState<string[]>([]);
  const [selectedPeriodo, setSelectedPeriodo] = useState<string>("");

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
  }, [selectedMateria]);

  // Obtener períodos
  useEffect(() => {
    getAllSemesters().then(setPeriodos);
  }, []);

  const handleSelectRubrica = (nombre: string) => {
    const rubrica = rubricas.find((r) => r.nombreRubrica === nombre) || null;
    setSelectedRubrica(rubrica);
    setSelectedPeriodo("");
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
    handleSelectRubrica,
  };
}; 