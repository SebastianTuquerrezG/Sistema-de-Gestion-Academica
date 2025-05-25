import React from "react";
import "./EstadisticasCards.css";

interface EstadisticasCardsProps {
  media: number;
  desviacionEstandar: number;
  maximo: number;
  minimo: number;
}

const EstadisticasCards: React.FC<EstadisticasCardsProps> = ({
  media,
  desviacionEstandar,
  maximo,
  minimo,
}) => {
  return (
    <div className="estadisticas-cards-grid">
      <div className="estadistica-card">
        <span className="estadistica-label">Media</span>
        <span className="estadistica-valor">{media}</span>
      </div>
      <div className="estadistica-card">
        <span className="estadistica-label">Desviación Estandar</span>
        <span className="estadistica-valor">{desviacionEstandar}</span>
      </div>
      <div className="estadistica-card card-max">
        <span className="estadistica-label">Máximo</span>
        <span className="estadistica-valor">{maximo}</span>
      </div>
      <div className="estadistica-card card-min">
        <span className="estadistica-label">Mínimo</span>
        <span className="estadistica-valor">{minimo}</span>
      </div>
    </div>
  );
};

export default EstadisticasCards; 