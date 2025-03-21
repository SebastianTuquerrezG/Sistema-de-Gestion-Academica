import React, { useState } from "react";
import Dropdown from "../../components/utils/dropdown";
import StaticField from "../../components/utils/staticField";
import "./EvaluacionesCSS/rubricaInfo.css"; // Importamos el CSS

const InfoRubrica: React.FC = () => {
  const estudiantes = [
    { id: 1, name: "Estudiante Elegible" },
    { id: 2, name: "Jonathan Felipe Hurtado" },
    { id: 3, name: "Harold Andrés Molano" },
    { id: 4, name: "Álvaro Javier Arroyo" },
    { id: 5, name: "Felipe Armand Pino" }
  ];

  const periodos = [
    { id: 1, name: "Período Elegible" },
    { id: 2, name: "2024-I" },
    { id: 3, name: "2024-II" },
    { id: 4, name: "2025-I" }
  ];

  const [selectedEstudiante, setSelectedEstudiante] = useState(estudiantes[0].name);
  const [selectedPeriodo, setSelectedPeriodo] = useState(periodos[0].name);

  return (
    
    <div className="rubrica-info">
      <div className="rubrica-info-container">
        
        {/* Columna izquierda */}
        <div className="info-block">
          <Dropdown 
            label="Estudiante" 
            options={estudiantes} 
            selected={selectedEstudiante} 
            onSelect={(id: number) => setSelectedEstudiante(estudiantes.find(e => e.id === id)?.name || "")} 
          />
          <StaticField label="Rúbrica" value="Evaluación de Proyecto de Software" />
        </div>

        {/* Columna derecha */}
        <div className="info-block">
          <StaticField label="Materia" value="Ingeniería de Software III" />
          <Dropdown 
            label="Período" 
            options={periodos} 
            selected={selectedPeriodo} 
            onSelect={(id: number) => setSelectedPeriodo(periodos.find(p => p.id === id)?.name || "")} 
          />
        </div>
        
      </div>
    </div>
  );
};

export default InfoRubrica;
