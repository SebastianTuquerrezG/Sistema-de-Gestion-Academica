import React from "react";

import PeriodoItem from "./PeriodoItem.tsx";

interface Periodo {
    nombre: string;
}

interface CursosListProps {
    periodos: Periodo[];
    onPeriodoClick?: (periodo: Periodo) => void;
}

const CursosList: React.FC<CursosListProps> = ({ periodos, onPeriodoClick }) => (
    <div>
        {periodos.map((periodo, idx) => (
            <PeriodoItem
                key={idx}
                nombre={periodo.nombre}
                onClick={() => onPeriodoClick && onPeriodoClick(periodo)}
            />
        ))}
    </div>
);

export default CursosList;