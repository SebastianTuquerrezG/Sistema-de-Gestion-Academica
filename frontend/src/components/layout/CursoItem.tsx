import React from "react";
import "./CursoItem.css";

interface CursoItemProps {
  nombre: string;
  docente: string;
    id: number;
  onClick?: () => void;
}

const CursoItem: React.FC<CursoItemProps> = ({ nombre, docente, onClick }) => (
  <button className="curso-item" onClick={onClick}>
    <span className="curso-nombre">{nombre}</span>
    <span className="curso-docente">{docente}</span>
  </button>
);

export default CursoItem;
