import React from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import ActionButtons from "../../components/utils/actionButtons";
import { useEvaluationData } from "./hooks/useEvaluationData";
import { Criterio } from "./types";
import "../../assets/css/evaluaciones.css";

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
  } = useEvaluationData();

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
            texto: n.nivelDescripcion, // Usamos nivelDescripcion como texto
          };
        }),
      }));
  };

  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons />
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
        resultadoAprendizaje={selectedRubrica?.objetivoEstudio || ""}
        onSelectMateria={setSelectedMateria}
        onSelectRubrica={handleSelectRubrica}
        onSelectPeriodo={setSelectedPeriodo}
        onSelectEstudiante={setSelectedEstudiante}
      />

      {selectedRubrica && enrollId && (
        <EvaluationTable
          estudiante={selectedEstudiante}
          criterios={transformCriterios(selectedRubrica.criterios)}
          rubricaId={selectedRubrica.idRubrica}
          enrollId={enrollId}
        />
      )}
    </>
  );
};

export default Evaluaciones;
