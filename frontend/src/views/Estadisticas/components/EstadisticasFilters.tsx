import React from "react";
import Dropdown from "../../../components/utils/dropdown";
import StaticField from "../../../components/utils/staticField";
import "./EstadisticasFilters.css";

interface EstadisticasFiltersProps {
  materias: string[];
  rubricas: string[];
  periodos: string[];
  materiaSeleccionada: string;
  rubricaSeleccionada: string;
  periodoSeleccionado: string;
  resultadoAprendizaje: string;
  onSelectMateria: (materia: string) => void;
  onSelectRubrica: (rubrica: string) => void;
  onSelectPeriodo: (periodo: string) => void;
}

const EstadisticasFilters: React.FC<EstadisticasFiltersProps> = ({
  materias,
  rubricas,
  periodos,
  materiaSeleccionada,
  rubricaSeleccionada,
  periodoSeleccionado,
  resultadoAprendizaje,
  onSelectMateria,
  onSelectRubrica,
  onSelectPeriodo,
}) => {
  return (
    <div className="estadisticas-filters">
      <div className="filters-container">
        <div className="filter-block">
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

        <div className="filter-block">
          {rubricaSeleccionada && (
            <Dropdown
              label="Período"
              options={periodos}
              onSelect={onSelectPeriodo}
            />
          )}
        </div>
      </div>
    </div>
  );
};

export default EstadisticasFilters; 