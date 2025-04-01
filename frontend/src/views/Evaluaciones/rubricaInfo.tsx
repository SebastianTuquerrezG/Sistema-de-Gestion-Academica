import React from "react";
import Dropdown from "../../components/utils/dropdown";
import StaticField from "../../components/utils/staticField";
import "./EvaluacionesCSS/rubricaInfo.css";

interface RubricaInfoProps {
  estudiantes: string[];
  periodos: string[];
  materia: string;
  rubricaNombre: string;
  onSelectEstudiante: (nombre: string) => void;
}

const RubricaInfo: React.FC<RubricaInfoProps> = ({
  estudiantes,
  periodos,
  materia,
  rubricaNombre,
  onSelectEstudiante,
}) => {
  return (
    <div className="rubrica-info">
      <div className="rubrica-info-container">
        {/* Columna izquierda */}
        <div className="info-block">
          <Dropdown label="Estudiante" options={estudiantes} onSelect={onSelectEstudiante} />
          <StaticField label="Rúbrica" value={rubricaNombre} />
        </div>

        {/* Columna derecha */}
        <div className="info-block">
          <StaticField label="Materia" value={materia} />
          <Dropdown label="Período" options={periodos} />
        </div>
      </div>
    </div>
  );
};

export default RubricaInfo;
