import React from "react";
import Dropdown from "../../components/utils/dropdown";
import StaticField from "../../components/utils/staticField";
import "./EvaluacionesCSS/rubricaInfo.css";

interface RubricaInfoProps {
  rubricas: string[];
  periodos: string[];
  estudiantes: string[];

  onSelectRubrica: (rubrica: string) => void;
  onSelectPeriodo: (periodo: string) => void;
  onSelectEstudiante: (estudiante: string) => void;

  rubricaSeleccionada: string;
  resultadoAprendizaje: string;
  materia: string;
  periodoSeleccionado: string;
  estudianteSeleccionado: string;
}

const RubricaInfo: React.FC<RubricaInfoProps> = ({
  rubricas,
  periodos,
  estudiantes,
  onSelectRubrica,
  onSelectPeriodo,
  onSelectEstudiante,
  rubricaSeleccionada,
  resultadoAprendizaje,
  materia,
  periodoSeleccionado,
  estudianteSeleccionado
}) => {
  return (
    <div className="rubrica-info">
      <div className="rubrica-info-container">
        <div className="info-block">
          <Dropdown
            label="Rúbrica"
            options={rubricas}
            onSelect={onSelectRubrica}
          />

          {rubricaSeleccionada && (
            <>
              <StaticField label="Materia" value={materia} />
              <StaticField
                label="Resultado de Aprendizaje"
                value={resultadoAprendizaje}
              />
            </>
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
