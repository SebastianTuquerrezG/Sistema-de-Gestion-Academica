import { useState, useEffect } from 'react';
import { Rubrica, Materia } from '../types';
import {
  getAllSubjects,
  getRubricsBySubjectId,
  getAllSemesters,
  getRANameById
} from '../../../services/evaluationService';

export const useEstadisticasData = () => {
  const [materias, setMaterias] = useState<Materia[]>([]);
  const [selectedMateria, setSelectedMateria] = useState<string>("");
  const [rubricas, setRubricas] = useState<Rubrica[]>([]);
  const [selectedRubrica, setSelectedRubrica] = useState<Rubrica | null>(null);
  const [periodos, setPeriodos] = useState<string[]>([]);
  const [selectedPeriodo, setSelectedPeriodo] = useState<string>("");
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
      getRubricsBySubjectId(Number(materia.idMateria ?? materia.id)).then((data) => {
        const ordenadas = data.slice().sort((a, b) => (a.nombreRubrica || a.name).localeCompare(b.nombreRubrica || b.name, 'es', { sensitivity: 'base' }));
        setRubricas(ordenadas);
      });
    } else {
      setRubricas([]);
    }
    setSelectedRubrica(null);
    setSelectedPeriodo("");
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

  const handleSelectRubrica = (nombre: string) => {
    // Buscar por ambas propiedades para compatibilidad
    const rubrica = rubricas.find((r) => r.nombreRubrica === nombre || r.name === nombre) || null;
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
    raName,
  };
}; 