import React from "react";
import Dropdown from "../../components/utils/dropdown";
import StaticField from "../../components/utils/staticField";
import "./EvaluacionesCSS/rubricaInfo.css";

interface RubricaInfoProps {
  materias: string[];
  rubricas: string[];
  periodos: string[];
  estudiantes: string[];

  onSelectMateria: (materia: string) => void;
  onSelectRubrica: (rubrica: string) => void;
  onSelectPeriodo: (periodo: string) => void;
  onSelectEstudiante: (estudiante: string) => void;

  materiaSeleccionada: string;
  rubricaSeleccionada: string;
  periodoSeleccionado: string;
  estudianteSeleccionado: string;
  resultadoAprendizaje: string;
}

const RubricaInfo: React.FC<RubricaInfoProps> = ({
  materias,
  rubricas,
  periodos,
  estudiantes,
  onSelectMateria,
  onSelectRubrica,
  onSelectPeriodo,
  onSelectEstudiante,
  materiaSeleccionada,
  rubricaSeleccionada,
  periodoSeleccionado,
  estudianteSeleccionado,
  resultadoAprendizaje
}) => {
  return (
    <div className="rubrica-info">
      <div className="rubrica-info-container">
        <div className="info-block">
          <Dropdown
            label="Materia"
            options={materias}
            onSelect={onSelectMateria}
          />

          {materiaSeleccionada && (
            <Dropdown
              label="Rúbrica"
              options={rubricas}
              onSelect={onSelectRubrica}
            />
          )}

          {rubricaSeleccionada && (
            <StaticField
              label="Resultado de Aprendizaje"
              value={resultadoAprendizaje}
            />
          )}
        </div>

        <div className="info-block">
          {rubricaSeleccionada && (
            <Dropdown
              label="Período"
              options={periodos}
              onSelect={onSelectPeriodo}
            />
          )}

          {periodoSeleccionado && (
            <Dropdown
              label="Estudiante"
              options={estudiantes}
              onSelect={onSelectEstudiante}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default RubricaInfo;
