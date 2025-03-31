import React from "react";
import Dropdown from "../../components/utils/dropdown";
import StaticField from "../../components/utils/staticField";
import "./EvaluacionesCSS/rubricaInfo.css"; // Importamos el CSS

const InfoRubrica: React.FC = () => {
  return (
    <div className="rubrica-info">
      <div className="rubrica-info-container">
        
        {/* Columna izquierda */}
        <div className="info-block">
          <Dropdown 
            label="Estudiante" 
            options={[
              "Jonathan Felipe Hurtado", 
              "Harold Andrés Molano", 
              "Álvaro Javier Arroyo", 
              "Felipe Armand Pino"
            ]} 
          />
          <StaticField label="Rúbrica" value="Evaluación de Proyecto de Software" />
        </div>

        {/* Columna derecha */}
        <div className="info-block">
          <StaticField label="Materia" value="Ingeniería de Software III" />
          <Dropdown 
            label="Período" 
            options={[
              "Período Elegible", 
              "2024-I", 
              "2024-II", 
              "2025-I"
            ]} 
          />
        </div>
        
      </div>
    </div>
  );
};

export default InfoRubrica;
