import React from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import ActionButtons from "../../components/utils/actionButtons";
import { useEvaluationData } from "./hooks/useEvaluationData";
import { Criterio } from "./types";
import "../../assets/css/evaluaciones.css";
import { useNavigate } from "react-router-dom";

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
            texto: n.nivelDescripcion, // Usamos nivelDescripcion como texto
          };
        }),
      }));
  };

  // Agregar logs para depuración
  if (selectedRubrica && enrollId) {
    console.log("Contenido de la evaluación seleccionada:", selectedRubrica);
    console.log("EnrollId seleccionado:", enrollId);
  }

  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons
          onEstadisticas={() => {
            navigate("/estadisticas", {
              state: {
                materia: selectedMateria,
                rubrica: selectedRubrica?.name || "",
                periodo: selectedPeriodo,
              },
            });
          }}
        />
      </div>

      <RubricaInfo
        materias={materias.map((m) => m.name)}
        rubricas={rubricas.map((r) => r.name)}
        periodos={periodos}
        estudiantes={estudiantes}
        materiaSeleccionada={selectedMateria}
        rubricaSeleccionada={selectedRubrica?.name || ""}
        periodoSeleccionado={selectedPeriodo}
        estudianteSeleccionado={selectedEstudiante}
        resultadoAprendizaje={raName}
        onSelectMateria={setSelectedMateria}
        onSelectRubrica={handleSelectRubrica}
        onSelectPeriodo={setSelectedPeriodo}
        onSelectEstudiante={setSelectedEstudiante}
      />

      {selectedRubrica && enrollId && (
        <>
          <EvaluationTable
            estudiante={selectedEstudiante}
            criterios={transformCriterios(selectedRubrica.criterios || [])}
            rubricaId={selectedRubrica.id}
            enrollId={enrollId}
          />
        </>
      )}
    </>
  );
};

export default Evaluaciones;
