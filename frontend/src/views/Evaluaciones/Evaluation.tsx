import React from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import ActionButtons from "../../components/utils/actionButtons";
import { useEvaluationData } from "./hooks/useEvaluationData";
import { Criterio } from "./types";
import "../../assets/css/evaluaciones.css";
import { useNavigate } from "react-router-dom";
import { CircularProgress, Alert, Box, Typography } from '@mui/material';

const Evaluaciones: React.FC = () => {
  const {
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
  } = useEvaluationData();

  const navigate = useNavigate();

  const transformCriterios = (criterios: any[]): Criterio[] => {
    return criterios
      .slice()
      .sort((a, b) => a.idCriterio - b.idCriterio)
      .map((c) => ({
        idCriterio: c.idCriterio,
        criterio: c.crfDescripcion,
        porcentaje: c.crfPorcentaje * 100,
        descriptores: c.niveles.map((n: any, i: number) => {
          const [inferior, superior] = n.rangoNota.split("-").map(Number);
          return {
            nivel: `Nivel ${i + 1}`,
            nivelDescripcion: n.nivelDescripcion,
            inferior,
            superior,
            texto: n.nivelDescripcion,
            rangoNota: n.rangoNota,
          };
        }),
      }));
  };

  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons
          onEstadisticas={() => {
            navigate("/estadisticas", {
              state: {
                materia: selectedMateria,
                rubrica: selectedRubrica?.nombreRubrica || "",
                periodo: selectedPeriodo,
              },
            });
          }}
        />
      </div>

      <RubricaInfo
        materias={materias.map((m) => m.name)}
        rubricas={rubricas.map((r) => r.nombreRubrica)}
        periodos={periodos}
        estudiantes={estudiantes}
        materiaSeleccionada={selectedMateria}
        rubricaSeleccionada={selectedRubrica?.nombreRubrica || ""}
        periodoSeleccionado={selectedPeriodo}
        estudianteSeleccionado={selectedEstudiante}
        resultadoAprendizaje={raName}
        onSelectMateria={setSelectedMateria}
        onSelectRubrica={handleSelectRubrica}
        onSelectPeriodo={setSelectedPeriodo}
        onSelectEstudiante={setSelectedEstudiante}
      />

      {selectedRubrica && enrollId && selectedPeriodo && selectedEstudiante && (
        <EvaluationTable
          estudiante={selectedEstudiante}
          criterios={transformCriterios(selectedRubrica.criterios || [])}
          rubricaId={selectedRubrica.idRubrica}
          enrollId={enrollId}
        />
      )}
    </>
  );
};

export default Evaluaciones;
