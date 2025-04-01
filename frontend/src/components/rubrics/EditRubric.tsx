"use client"

import type React from "react"

import { useState } from "react"
import { Button } from "@/components/ui/button"
import { Input } from "@/components/ui/input"
import { Textarea } from "@/components/ui/textarea"
import { Plus, Trash2, CirclePlus } from "lucide-react"
import {Card,CardHeader,CardContent,CardDescription, CardTitle} from "@/components/ui/card.tsx";

export default function EditRubric() {
    // Datos precargados para el ejemplo
    const [rubricData, setRubricData] = useState({
        id: "IS102",
        name: "Evaluación de Proyecto Final",
        subject: "Ingeniería de Software",
        maxScore: "3",
        objective: "Evaluar las competencias adquiridas en el desarrollo del proyecto final",
    })

    // Criterios precargados
    const [criteria, setCriteria] = useState([
        {
            description: "Calidad del código",
            percentage: "40.00",
            comment: "Se evalúa la legibilidad, estructura y buenas prácticas",
            level1: "Código desorganizado con múltiples errores",
            level2: "Código funcional con estructura básica",
            level3: "Código bien estructurado siguiendo estándares",
        },
        {
            description: "Documentación",
            percentage: "30.00",
            comment: "Se evalúa la completitud y claridad de la documentación",
            level1: "Documentación escasa o inexistente",
            level2: "Documentación básica pero incompleta",
            level3: "Documentación completa y bien estructurada",
        },
    ])

    // Función para actualizar los datos del formulario
    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { name, value } = e.target
        setRubricData({
            ...rubricData,
            [name]: value,
        })
    }

    // Función para actualizar los criterios
    const handleCriteriaChange = (index: number, field: string, value: string) => {
        const updatedCriteria = [...criteria]
        updatedCriteria[index] = {
            ...updatedCriteria[index],
            [field]: value,
        }
        setCriteria(updatedCriteria)
    }

    // Función para añadir un nuevo criterio
    const addCriteria = () => {
        setCriteria([
            ...criteria,
            {
                description: "",
                percentage: "0.00",
                comment: "",
                level1: "",
                level2: "",
                level3: "",
            },
        ])
    }

    // Función para eliminar un criterio
    const removeCriteria = (index: number) => {
        const updatedCriteria = criteria.filter((_, i) => i !== index)
        setCriteria(updatedCriteria)
    }

    return (
        <Card className="w-full max-w-[1200px]">
            <CardHeader>
                <CardTitle>Editar Rúbrica de Evaluación</CardTitle>
                <CardDescription>Ingresa los datos de la nueva rúbrica en el formulario.</CardDescription>
            </CardHeader>
            <CardContent>
                <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
                <div>
                    <label htmlFor="id" className="block mb-2 font-medium">
                        ID Rúbrica
                    </label>
                    <Input id="id" name="id" placeholder="Ej: IS102" value={rubricData.id} onChange={handleInputChange} />
                </div>
                <div>
                    <label htmlFor="name" className="block mb-2 font-medium">
                        Nombre Rúbrica
                    </label>
                    <Input
                        id="name"
                        name="name"
                        placeholder="Nombre de la rúbrica"
                        value={rubricData.name}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label htmlFor="subject" className="block mb-2 font-medium">
                        Materia
                    </label>
                    <Input
                        id="subject"
                        name="subject"
                        placeholder="Nombre de la materia"
                        value={rubricData.subject}
                        onChange={handleInputChange}
                    />
                </div>
                <div>
                    <label htmlFor="maxScore" className="block mb-2 font-medium">
                        Nota Máxima Rúbrica
                    </label>
                    <Input
                        id="maxScore"
                        name="maxScore"
                        placeholder="Ej: 3"
                        value={rubricData.maxScore}
                        onChange={handleInputChange}
                    />
                </div>
            </div>
                <div className="mb-6">
                    <label htmlFor="objective" className="block mb-2 font-medium">
                        Objetivo de Estudio
                    </label>
                    <Input
                        id="objective"
                        name="objective"
                        placeholder="Objetivo de la evaluación"
                        value={rubricData.objective}
                        onChange={handleInputChange}
                    />
                </div>
                <div className="mb-6">

                    <CardTitle className="mt-6">Criterios de Evaluación</CardTitle>
                    <CardDescription className="mb-4">Ingresa los criterios de evaluación y sus niveles.</CardDescription>

                    <div className="overflow-x-auto">
                    <table className="w-full border-collapse">
                        <thead>
                        <tr className="bg-[#0a0a6b] text-white">
                            <th className="p-3 text-left">Criterio Evaluación</th>
                            <th className="p-3 text-left">Porcentaje</th>
                            <th className="p-3 text-left">Comentario</th>
                            <th className="p-3 text-left">
                                Nivel 1<div className="text-xs">0-1</div>
                            </th>
                            <th className="p-3 text-left">
                                Nivel 2<div className="text-xs">1-2</div>
                            </th>
                            <th className="p-3 text-left">
                                Nivel 3<div className="text-xs">2-3</div>
                            </th>
                            <th className="p-3 text-center">
                                <CirclePlus className="inline-block w-5 h-5" />
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                        {criteria.map((criterion, index) => (
                            <tr key={index} className="border-b">
                                <td className="p-2">
                                    <Textarea
                                        placeholder="Descripción del criterio"
                                        value={criterion.description}
                                        onChange={(e) => handleCriteriaChange(index, "description", e.target.value)}
                                        className="min-h-[80px] resize-none"
                                    />
                                </td>
                                <td className="p-2">
                                    <Input
                                        type="number"
                                        step="0.01"
                                        placeholder="0.00"
                                        value={criterion.percentage}
                                        onChange={(e) => handleCriteriaChange(index, "percentage", e.target.value)}
                                        className="w-24"
                                    />
                                </td>
                                <td className="p-2">
                                    <Textarea
                                        placeholder="Comentario para el criterio"
                                        value={criterion.comment}
                                        onChange={(e) => handleCriteriaChange(index, "comment", e.target.value)}
                                        className="min-h-[80px] resize-none"
                                    />
                                </td>
                                <td className="p-2">
                                    <Textarea
                                        placeholder="Descripción nivel 1"
                                        value={criterion.level1}
                                        onChange={(e) => handleCriteriaChange(index, "level1", e.target.value)}
                                        className="min-h-[80px] resize-none"
                                    />
                                </td>
                                <td className="p-2">
                                    <Textarea
                                        placeholder="Descripción nivel 2"
                                        value={criterion.level2}
                                        onChange={(e) => handleCriteriaChange(index, "level2", e.target.value)}
                                        className="min-h-[80px] resize-none"
                                    />
                                </td>
                                <td className="p-2">
                                    <Textarea
                                        placeholder="Descripción nivel 3"
                                        value={criterion.level3}
                                        onChange={(e) => handleCriteriaChange(index, "level3", e.target.value)}
                                        className="min-h-[80px] resize-none"
                                    />
                                </td>
                                <td className="p-2 text-center">
                                    <Button
                                        variant="ghost"
                                        size="icon"
                                        onClick={() => removeCriteria(index)}
                                        className="text-red-500 hover:text-red-700 hover:bg-red-50"
                                    >
                                        <Trash2 className="h-5 w-5" />
                                    </Button>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>

                    <Button variant="outline" onClick={addCriteria} className="mt-4 bg-[#0a0a6b] text-white hover:bg-[#0a0a9b]">
                        <Plus className="mr-2 h-4 w-4" /> Añadir Criterio
                    </Button>
            </div>

                <div className="flex justify-between mt-8">
                    <Button variant="outline">Cancelar</Button>
                    <Button className="bg-[#0a0a6b] hover:bg-[#0a0a9b]">Guardar</Button>
                </div>
            </CardContent>
        </Card>
    )
}

