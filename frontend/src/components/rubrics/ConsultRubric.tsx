"use client"

import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { Search, Plus, Pencil, Trash2 } from "lucide-react";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router-dom";
import { getAllRubrics } from "@/services/rubricService";
import { RubricInterface } from "@/interfaces/RubricInterface";


export default function ConsultarRubrica() {
    const [searchTerm, setSearchTerm] = useState("");
    const [rubrics, setRubrics] = useState<RubricInterface[]>([]);
    const navigate = useNavigate(); // Hook to navigate between pages
    const [selectedRubricId, setSelectedRubricId] = useState<string | null>(null);


    useEffect(() => {
        getAllRubrics()
            .then((data) => setRubrics(data))
            .catch((error) => console.error(error));
    }, []);

    const filteredRubrics = rubrics.filter(
        (rubric) =>
            rubric.nombreRubrica.toLowerCase().includes(searchTerm.toLowerCase()) ||
            rubric.rubricaId.toLowerCase().includes(searchTerm.toLowerCase()) ||
            rubric.materia.toLowerCase().includes(searchTerm.toLowerCase())
    );

    // Funcion para navegar a la pagina de editar
    const handleEdit = (id: string) => {
        navigate(`/rubricas/editar/${id}`);
    };
    // Function para navegar a la pagina de crear
    const handleAdd = () => {
        navigate(`/rubricas/crear`);
    };

    // Función para navegar a RubricDetail
    const handleDetail = (id: string) => {
        setSelectedRubricId(id);
        navigate(`/rubricas/detalle/${id}`);
    };


    return (
        <div>
            <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>
                Mis Rubricas
            </h2>
            <main className="max-w-7xl mx-auto px-4 py-8">
                <div className="mb-6 flex flex-col sm:flex-row gap-4 justify-between items-start sm:items-center">
                    <div className="relative flex-1 max-w-md">
                        <Input
                            type="text"
                            placeholder="Ingrese Rubrica a buscar..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="pl-10 w-full"
                        />
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                    </div>
                    <div className="flex gap-2">
                        <Button className="outline" onClick={() => handleAdd()}>
                            <Plus className="h-4 w-4 mr-2" />
                            Añadir Rubrica
                        </Button>
                    </div>
                </div>
                <div>
                    <table className="w-full">
                        <thead>
                            <tr className="title5 bg-[#000066] text-white ">
                                <th className="py-3 text-left ">Identificador</th>
                                <th className="px-6 py-3 text-left">Nombre Rubrica</th>
                                <th className="px-6 py-3 text-left">Materia</th>
                                <th className="px-6 py-3 text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredRubrics.map((rubric, index) => (
                                <tr key={rubric.rubricaId} className={index % 2 === 0 ? "bg-gray-50" : "bg-white"}>
                                    <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.rubricaId ? "text-blue-600" : ""}`}
                                        onClick={() => handleDetail(rubric.rubricaId)}>
                                        {rubric.rubricaId}
                                    </td>
                                    <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.rubricaId ? "text-blue-600" : ""}`}
                                        onClick={() => handleDetail(rubric.rubricaId)}>
                                        {rubric.nombreRubrica}
                                    </td>
                                    <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.rubricaId ? "text-blue-600" : ""}`}
                                        onClick={() => handleDetail(rubric.rubricaId)}>
                                        {rubric.materia}
                                    </td>
                                    <td className="px-6 py-4">
                                        <div className="flex justify-center gap-2">
                                            <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => handleEdit(rubric.rubricaId)}>
                                                <Pencil className="h-4 w-4" />
                                            </Button>
                                            <Button variant="ghost" size="icon" className="h-8 w-8 text-orange-500 hover:text-orange-600">
                                                <Trash2 className="h-4 w-4" />
                                            </Button>
                                        </div>
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    );
}