import { useParams } from "react-router-dom";
import RubricList from "./rubricList.tsx";
import React from "react";

const RubricWrapper: React.FC = () => {
    const { idStudent, idSubject, period } = useParams();

    if (!idStudent || !idSubject || !period) return <p>Parámetros inválidos</p>;

    return (
        <RubricList
            idStudent={parseInt(idStudent)}
            idSubject={parseInt(idSubject)}
            period={period}
        />
    );
};

export default RubricWrapper;