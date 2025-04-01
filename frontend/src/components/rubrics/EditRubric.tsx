"use client"

import { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Textarea } from "@/components/ui/textarea";
import { Plus, Trash2, PlusCircle } from "lucide-react";
import { Card, CardHeader, CardContent, CardDescription, CardTitle, CardFooter } from "@/components/ui/card";
import { Label } from "@/components/ui/label";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";

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

export default function EditRubric() {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
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

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { id, value } = e.target;
        setRubric((prevData) => prevData ? { ...prevData, [id]: value } : null);
    };

    const handleCriteriaChange = (index: number, field: string, value: string) => {
        if (!rubric) return;
        const updatedCriteria = [...rubric.criterios];
        updatedCriteria[index] = {
            ...updatedCriteria[index],
            [field]: value,
        };
        setRubric({ ...rubric, criterios: updatedCriteria });
    };

    const addLevel = () => {
        if (!rubric) return;
        const newLevel: Nivel = { idNivel: rubric.criterios[0].niveles.length + 1, nivelDescripcion: "", rangoNota: "" };
        const updatedCriteria = rubric.criterios.map(criterio => ({
            ...criterio,
            niveles: [...criterio.niveles, newLevel]
        }));
        setRubric({ ...rubric, criterios: updatedCriteria });
    };

    const removeLevel = () => {
        if (!rubric) return;
        const updatedCriteria = rubric.criterios.map(criterio => ({
            ...criterio,
            niveles: criterio.niveles.slice(0, -1)
        }));
        setRubric({ ...rubric, criterios: updatedCriteria });
    };

    const addRow = () => {
        if (!rubric) return;
        const newCriterio: Criterio = {
            idCriterio: (rubric.criterios.length + 1).toString(),
            crfDescripcion: "",
            crfPorcentaje: 0,
            niveles: rubric.criterios[0].niveles.map(nivel => ({ ...nivel, nivelDescripcion: "" }))
        };
        setRubric({ ...rubric, criterios: [...rubric.criterios, newCriterio] });
    };

    const removeRow = (index: number) => {
        if (!rubric) return;
        const updatedCriteria = rubric.criterios.filter((_, i) => i !== index);
        setRubric({ ...rubric, criterios: updatedCriteria });
    };

    return (
        <Card className="w-full max-w-[1200px]">
            <CardHeader>
                <CardTitle>Editar Rúbrica de Evaluación</CardTitle>
                <CardDescription>Ingresa los datos de la nueva rúbrica en el formulario.</CardDescription>
            </CardHeader>
            <CardContent>
                <div className="space-y-4">
                    <div className="grid grid-cols-2 gap-4">
                        <div className="grid w-full max-w-sm items-center gap-1.5">
                            <Label htmlFor="idRubrica">ID Rúbrica</Label>
                            <Input id="idRubrica" placeholder="Ej: IS102" value={rubric.idRubrica} onChange={handleInputChange} />
                        </div>
                        <div className="grid w-full max-w-sm items-center gap-1.5">
                            <Label htmlFor="nombreRubrica">Nombre Rúbrica</Label>
                            <Input id="nombreRubrica" placeholder="Nombre de la rúbrica" value={rubric.nombreRubrica} onChange={handleInputChange} />
                        </div>
                        <div className="grid w-full max-w-sm items-center gap-1.5">
                            <Label htmlFor="materia">Materia</Label>
                            <Input id="materia" placeholder="Nombre de la materia" value={rubric.materia} onChange={handleInputChange} />
                        </div>
                    </div>
                </div>
                <CardTitle className="mt-6">Criterios de Evaluación</CardTitle>
                <CardDescription className="mb-4">Ingresa los criterios de evaluación y sus niveles.</CardDescription>
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>Criterio Evaluación</TableHead>
                            <TableHead>Porcentaje</TableHead>
                            {rubric.criterios[0]?.niveles.map((nivel, index) => (
                                <TableHead key={index}>
                                    <div className="flex flex-col">
                                        <span>Nivel {nivel.idNivel}</span>
                                        <span className="text-xs">{nivel.rangoNota}</span>
                                    </div>
                                </TableHead>
                            ))}
                            <TableHead>
                                <Button onClick={addLevel} variant="ghost" size="icon">
                                    <PlusCircle className="h-4 w-4" />
                                </Button>
                                <Button onClick={removeLevel} variant="ghost" size="icon" className="text-red-600">
                                    <Trash2 className="h-4 w-4" />
                                </Button>
                            </TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {rubric.criterios.map((criterio, rowIndex) => (
                            <TableRow key={rowIndex}>
                                <TableCell>
                                    <Textarea
                                        value={criterio.crfDescripcion}
                                        onChange={(e) => handleCriteriaChange(rowIndex, "crfDescripcion", e.target.value)}
                                        placeholder="Descripción del criterio"
                                    />
                                </TableCell>
                                <TableCell>
                                    <Input
                                        type="number"
                                        min="0"
                                        max="1"
                                        step="0.01"
                                        onChange={(e) => handleCriteriaChange(rowIndex, "crfPorcentaje", e.target.value)}
                                        value={criterio.crfPorcentaje.toString()}
                                        placeholder="0.00"
                                    />
                                </TableCell>
                                {criterio.niveles.map((nivel, nivelIndex) => (
                                    <TableCell key={nivelIndex}>
                                        <Textarea
                                            value={nivel.nivelDescripcion}
                                            onChange={(e) => handleCriteriaChange(rowIndex, `niveles.${nivelIndex}.nivelDescripcion`, e.target.value)}
                                            placeholder={`Descripción nivel ${nivel.idNivel}`}
                                        />
                                    </TableCell>
                                ))}
                                <TableCell className="text-center">
                                    <Button onClick={() => removeRow(rowIndex)} variant="ghost" size="icon" className="text-red-600">
                                        <Trash2 className="h-1 w-1" />
                                    </Button>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
                <div className="mt-4">
                    <Button onClick={addRow}>
                        <Plus className="h-4 w-4 mr-2" />Añadir Criterio
                    </Button>
                </div>
            </CardContent>
            <CardFooter className="flex justify-between">
                <Button variant="outline" onClick={() => navigate("/rubricas")}>Cancelar</Button>
                <Button className="bg-[#0a0a6b] hover:bg-[#0a0a9b]">Guardar</Button>
            </CardFooter>
        </Card>
    );
}