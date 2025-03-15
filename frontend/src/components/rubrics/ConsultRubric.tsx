import { Button } from "@/components/ui/button";
import { useState } from "react"
import { Search, Plus, Filter, Pencil, Trash2 } from "lucide-react"
import { Input } from "@/components/ui/input"

"use client"
interface Rubric {
    id: string
    nombre: string
    materia: string
}

const initialRubrics: Rubric[] = [
    { id: "IS101", nombre: "Evaluación de Proyecto de Software", materia: "Ingenieria de Software III" },
    { id: "IS102", nombre: "Evaluación de Prácticas de Programación", materia: "Estructura de Datos" },
    { id: "IS103", nombre: "Evaluación de Presentación de Arquitectura de Software",
        materia: "Ingenieria de Software I",},
    { id: "IS104", nombre: "Evaluación de Documentación Técnica", materia: "Programacion Orientada a objetos" },
    { id: "IS105", nombre: "Evaluación de Testeo de Software", materia: "Calidad de Software" },
]

export default function ConsultarRubrica() {
    const [searchTerm, setSearchTerm] = useState("")
    const [rubrics] = useState<Rubric[]>(initialRubrics)

    const filteredRubrics = rubrics.filter(
        (rubric) =>
            rubric.nombre.toLowerCase().includes(searchTerm.toLowerCase()) ||
            rubric.id.toLowerCase().includes(searchTerm.toLowerCase()) ||
            rubric.materia.toLowerCase().includes(searchTerm.toLowerCase()),
    )
    return (
        <div>
            <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>Mis Rubricas</h2>
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
                        <Button className="outline">
                            <Plus className="h-4 w-4 mr-2" />
                            Añadir Rubrica
                        </Button>
                        <Button className="outline">
                            <Filter className="h-4 w-4 mr-2" />
                            Filtrar
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
                            <tr key={rubric.id} className={index % 2 === 0 ? "bg-gray-50" : "bg-white"}>
                                <td className="px-6 py-4">{rubric.id}</td>
                                <td className="px-6 py-4">{rubric.nombre}</td>
                                <td className="px-6 py-4">{rubric.materia}</td>
                                <td className="px-6 py-4">
                                    <div className="flex justify-center gap-2">
                                        <Button variant="ghost" size="icon" className="h-8 w-8">
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
    )
}

