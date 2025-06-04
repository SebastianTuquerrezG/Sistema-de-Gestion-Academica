import React from "react";

interface PeriodoItemProps {
    nombre: string;
    onClick?: () => void;
}

const CursoItem: React.FC<PeriodoItemProps> = ({ nombre, onClick }) => (
    <button className="curso-item" onClick={onClick}>
        <span className="curso-nombre">{nombre}</span>
    </button>
);

export default CursoItem;