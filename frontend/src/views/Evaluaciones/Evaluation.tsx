import React, { useEffect, useState } from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import ActionButtons from "../../components/utils/actionButtons";
import {
  getAllRubrics,
  getAllStudents,
  getAllSemesters,
} from "../../services/evaluationService";

import "../../assets/css/evaluaciones.css";

// Interfaces
// Interfaces
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
      rangoNota: string; // ej: "1-2"
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
  const [selectedRubrica, setSelectedRubrica] = useState<Rubrica | null>(null);

  const [periodos, setPeriodos] = useState<string[]>([]);
  const [selectedPeriodo, setSelectedPeriodo] = useState<string>("");

  const [estudiantes, setEstudiantes] = useState<string[]>([]);
  const [selectedEstudiante, setSelectedEstudiante] = useState<string>("");

  // Cargar rúbricas
  useEffect(() => {
    getAllRubrics().then(setRubricas);
  }, []);

  // Cargar periodos desde enrolls
  useEffect(() => {
    getAllSemesters().then(setPeriodos);
  }, []);

  // Cargar estudiantes cuando se seleccione un período
  useEffect(() => {
    if (selectedPeriodo) {
      getAllStudents().then((data: Estudiante[]) => {
        const nombres = data.map(e => `${e.name ?? ""} ${e.lastName ?? ""}`.trim());
        setEstudiantes(nombres);
      });
    }
  }, [selectedPeriodo]);
  

  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons />
      </div>

      <RubricaInfo
        rubricas={rubricas.map((r) => r.nombreRubrica)}
        periodos={periodos}
        estudiantes={estudiantes}
        rubricaSeleccionada={selectedRubrica?.nombreRubrica || ""}
        periodoSeleccionado={selectedPeriodo}
        estudianteSeleccionado={selectedEstudiante}
        onSelectRubrica={(nombre) => {
          const rubrica = rubricas.find((r) => r.nombreRubrica === nombre) || null;
          setSelectedRubrica(rubrica);
          setSelectedPeriodo("");
          setEstudiantes([]);
        }}
        onSelectPeriodo={setSelectedPeriodo}
        onSelectEstudiante={setSelectedEstudiante}
        resultadoAprendizaje={selectedRubrica?.objetivoEstudio || ""}
        materia={selectedRubrica?.materia || ""}
      />

{selectedEstudiante && selectedRubrica && (
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
    enrollId={1} // simulado
  />
)}
    </>
  );
};

export default Evaluaciones;
