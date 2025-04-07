import React, { useEffect, useState } from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import ActionButtons from "../../components/utils/actionButtons";
import {
  getAllRubrics,
  getAllStudents,
  getAllSemesters,
  getEnrollIdFromStudentAndPeriod,
} from "../../services/evaluationService";

import "../../assets/css/evaluaciones.css";

interface Rubrica {
  idRubrica: number;
  nombreRubrica: string;
  materia: string;
  notaRubrica: number;
  objetivoEstudio: string;
  criterios: {
    crfDescripcion: string;
    crfPorcentaje: number;
    niveles: {
      nivelDescripcion: string;
      rangoNota: string;
    }[];
  }[];
}

interface Estudiante {
  id: number;
  name: string;
  lastName: string;
  identification: string;
}

const Evaluaciones: React.FC = () => {
  const [rubricas, setRubricas] = useState<Rubrica[]>([]);
  const [materias, setMaterias] = useState<string[]>([]);
  const [selectedMateria, setSelectedMateria] = useState<string>("");

  const [filteredRubricas, setFilteredRubricas] = useState<Rubrica[]>([]);
  const [selectedRubrica, setSelectedRubrica] = useState<Rubrica | null>(null);

  const [periodos, setPeriodos] = useState<string[]>([]);
  const [selectedPeriodo, setSelectedPeriodo] = useState<string>("");

  const [estudiantes, setEstudiantes] = useState<string[]>([]);
  const [selectedEstudiante, setSelectedEstudiante] = useState<string>("");

  const [enrollId, setEnrollId] = useState<number | null>(null);

  // Cargar rúbricas y materias al iniciar
  useEffect(() => {
    getAllRubrics().then((rubricasData) => {
      setRubricas(rubricasData);
      const materiasUnicas = Array.from(
        new Set(rubricasData.map((r: Rubrica) => r.materia))
      );
      setMaterias(materiasUnicas);
    });
  }, []);

  // Filtrar rúbricas por materia
  useEffect(() => {
    const filtradas = rubricas.filter((r) => r.materia === selectedMateria);
    setFilteredRubricas(filtradas);
    setSelectedRubrica(null);
    setSelectedPeriodo("");
    setSelectedEstudiante("");
    setEstudiantes([]);
    setEnrollId(null);
  }, [selectedMateria]);

  // Cargar períodos una vez
  useEffect(() => {
    getAllSemesters().then(setPeriodos);
  }, []);

  // Cargar estudiantes por período
  useEffect(() => {
    if (selectedPeriodo) {
      getAllStudents().then((data: Estudiante[]) => {
        const nombres = data.map(
          (e) => `${e.name ?? ""} ${e.lastName ?? ""}`.trim()
        );
        setEstudiantes(nombres);
      });
    }
  }, [selectedPeriodo]);

  // Buscar enrollId si hay estudiante y período
  useEffect(() => {
    if (selectedEstudiante && selectedPeriodo) {
      getEnrollIdFromStudentAndPeriod(selectedEstudiante, selectedPeriodo).then(
        (id) => {
          setEnrollId(id);
        }
      );
    }
  }, [selectedEstudiante, selectedPeriodo]);

  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons />
      </div>

      <RubricaInfo
        materias={materias}
        rubricas={filteredRubricas.map((r) => r.nombreRubrica)}
        periodos={periodos}
        estudiantes={estudiantes}
        materiaSeleccionada={selectedMateria}
        rubricaSeleccionada={selectedRubrica?.nombreRubrica || ""}
        periodoSeleccionado={selectedPeriodo}
        estudianteSeleccionado={selectedEstudiante}
        resultadoAprendizaje={selectedRubrica?.objetivoEstudio || ""}
        onSelectMateria={setSelectedMateria}
        onSelectRubrica={(nombre) => {
          const rubrica =
            filteredRubricas.find((r) => r.nombreRubrica === nombre) || null;
          setSelectedRubrica(rubrica);
          setSelectedPeriodo("");
          setSelectedEstudiante("");
          setEnrollId(null);
        }}
        onSelectPeriodo={(periodo) => {
          setSelectedPeriodo(periodo);
          if (!periodo) {
            setSelectedEstudiante("");
            setEstudiantes([]);
          }
        }}
        
        onSelectEstudiante={setSelectedEstudiante}
      />

      {selectedRubrica && (
        <EvaluationTable
          estudiante={selectedEstudiante}
          criterios={selectedRubrica.criterios.map((c) => ({
            criterio: c.crfDescripcion,
            porcentaje: c.crfPorcentaje * 100,
            descriptores: c.niveles.map((n) => {
              const [inferior, superior] = n.rangoNota.split("-").map(Number);
              return {
                nivel: n.nivelDescripcion,
                texto: n.nivelDescripcion,
                inferior,
                superior,
              };
            }),
          }))}
          rubricaId={selectedRubrica.idRubrica}
          enrollId={enrollId || 1}
        />
      )}
    </>
  );
};

export default Evaluaciones;
