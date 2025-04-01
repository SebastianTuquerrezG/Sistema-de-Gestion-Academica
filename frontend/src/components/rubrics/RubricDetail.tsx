"use client"

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

interface Nivel {
    idNivel: number;
    nivelDescripcion: string;
    rangoNota: string;
}

interface Criterio {
    idCriterio: string;
    crfDescripcion: string;
    niveles: Nivel[];
    crfPorcentaje: number;
}

interface Rubric {
    idRubrica: string;
    nombreRubrica: string;
    materia: string;
    notaRubrica: number;
    objetivoEstudio: string;
    criterios: Criterio[];
    estado: string;
}

export default function RubricDetail() {
    const { id } = useParams<{ id: string }>();
    const [rubric, setRubric] = useState<Rubric | null>(null);

    useEffect(() => {
        fetch("/rubricas.json")
            .then(res => res.json())
            .then(data => {
                const foundRubric = data.find((r: Rubric) => r.idRubrica === id);
                setRubric(foundRubric || null);
            })
            .catch(error => console.error(error));
    }, [id]);

    if (!rubric) {
        return <p className="text-center text-red-500">Rúbrica no encontrada.</p>;
    }

    return (
        <div className="overflow-x-auto p-4">
            <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>
                Visualizar Rúbrica
            </h2>
            <div className="flex items-start gap-4 relative">
                <div className="w-full md:w-1/2 lg:w-1/3">
                    <p className="font-semibold">Identificador:</p>
                    <p className="border p-2 rounded">{rubric.idRubrica}</p>
                </div>
                <div className="w-full md:w-1/2 lg:w-1/3">
                    <p className="font-semibold">Nombre:</p>
                    <p className="border p-2 rounded">{rubric.nombreRubrica}</p>
                </div>
            </div>
            <div className="flex items-start gap-4 relative">
                <div className="w-full md:w-1/2 lg:w-1/3">
                    <p className="font-semibold">Materia:</p>
                    <p className="border p-2 rounded">{rubric.materia}</p>
                </div>
            </div>
            <table className="min-w-full bg-white border border-gray-300">
                <thead>
                <tr>
                    <th className="border border-gray-300 bg-gray-200 text-white px-4 py-2 text-left"></th>
                    <th colSpan={rubric.criterios[0]?.niveles.length} className="border border-gray-300 bg-gray-200 text-black px-4 py-2 text-center">DESCRIPTORES</th>
                    <th className="border border-gray-300 bg-gray-200 text-white px-4 py-2 text-center"></th>
                </tr>
                <tr>
                    <th className="border border-gray-300 bg-[#000066] text-white px-4 py-2 text-left">CRITERIOS</th>
                    {rubric.criterios[0]?.niveles.map((nivel, index) => (
                        <th key={index} className="border border-gray-300 bg-[#000066] text-white px-4 py-2 text-center">
                            {`Nivel ${nivel.idNivel}`}<br />{nivel.rangoNota}
                        </th>
                    ))}
                    <th className="border border-gray-300 bg-[#000066] text-white px-4 py-2 text-center">Porcentaje</th>
                </tr>
                </thead>
                <tbody>
                {rubric.criterios.map((criterio, index) => (
                    <tr key={index}>
                        <td className="border border-gray-300 px-4 py-2">{criterio.crfDescripcion}</td>
                        {criterio.niveles.map((nivel, i) => (
                            <td key={i} className="border border-gray-300 px-4 py-2">{nivel.nivelDescripcion}</td>
                        ))}
                        <td className="border border-gray-300 px-4 py-2 text-center">{criterio.crfPorcentaje}%</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}