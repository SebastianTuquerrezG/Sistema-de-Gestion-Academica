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
import { RubricInterface } from "@/interfaces/RubricInterface.ts";
import { LevelInterface } from "@/interfaces/LevelInterface.ts";
import { CriterionInterface } from "@/interfaces/CriterionInterface.ts";
import {getRubricById, updateRubric} from "@/services/rubricService.ts";

export default function EditRubric() {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate();
    const [rubric, setRubric] = useState<RubricInterface | null>(null);
    const [notification, setNotification] = useState<{ type: string, title: string, message: string } | null>(null);

    useEffect(() => {
        if (id) {
            getRubricById(id)
                .then((data) => setRubric(data))
                .catch((error) => console.error(error));
        }
    }, [id]);

    useEffect(() => {
        if (notification) {
            const timeout = setTimeout(() => setNotification(null), 4000);
            return () => clearTimeout(timeout);
        }
    }, [notification]);

    if (!rubric) {
        return <p className="text-center text-red-500">Rúbrica no encontrada.</p>;
    }

    const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
        const { id, value } = e.target;
        setRubric((prevData) => {
            if (!prevData) return null;
            return {
                ...prevData,
                [id]: id === "notaRubrica" ? parseFloat(value) : value
            };
        });
    };

   const handleCriteriaChange = (index: number, field: keyof CriterionInterface | `niveles.${number}.nivelDescripcion`, value: string) => {
       if (!rubric) return;
       const updatedCriteria = [...rubric.criterios];
       if (typeof field === "string" && field.startsWith("niveles.")) {
           const levelIndex = parseInt(field.split(".")[1]);
           if (!updatedCriteria[index].niveles[levelIndex]) {
               updatedCriteria[index].niveles[levelIndex] = { idNivel: levelIndex + 1, nivelDescripcion: "", rangoNota: "" };
           }
           updatedCriteria[index].niveles[levelIndex].nivelDescripcion = value;
       } else {
           const key = field as keyof CriterionInterface;
           if (key === "crfPorcentaje") {
               updatedCriteria[index][key] = parseFloat(value) as unknown as CriterionInterface[keyof CriterionInterface];
           } else {
               updatedCriteria[index][key] = value as unknown as CriterionInterface[keyof CriterionInterface];
           }
       }
       setRubric({ ...rubric, criterios: updatedCriteria });
   };

    const addLevel = () => {
        if (!rubric || rubric.criterios.length === 0) return;
        const newLevel: LevelInterface = { idNivel: rubric.criterios[0].niveles.length + 1, nivelDescripcion: "", rangoNota: "" };
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
        if (!rubric || rubric.criterios.length === 0) return;
        const newCriterio: CriterionInterface = {
            idCriterio: null,
            crfDescripcion: "",
            crfPorcentaje: 0,
            crfNota: 0,
            crfComentario: "",
            niveles: rubric.criterios[0].niveles.map(nivel => ({ ...nivel, nivelDescripcion: "" }))
        };
        setRubric({ ...rubric, criterios: [...rubric.criterios, newCriterio] });
    };


    const removeRow = (index: number) => {
        if (!rubric) return;
        const updatedCriteria = rubric.criterios.filter((_, i) => i !== index);
        setRubric({ ...rubric, criterios: updatedCriteria });
    };

   const handleSave = async () => {

       // Validar campos obligatorios
       const requiredFields = [
           { field: (document.getElementById("idRubrica") as HTMLInputElement)?.value, name: "ID Rúbrica" },
           { field: (document.getElementById("nombreRubrica") as HTMLInputElement)?.value, name: "Nombre Rúbrica" },
           { field: (document.getElementById("materia") as HTMLInputElement)?.value, name: "Materia" },
           { field: (document.getElementById("objetivoEstudio") as HTMLInputElement)?.value, name: "Objetivo de Estudio" }
       ];

       const missingField = requiredFields.find(f => !f.field);
       if (missingField) {
           setNotification({
               type: "error",
               title: "Campo requerido",
               message: `El campo "${missingField.name}" es obligatorio.`
           });
          console.log(`Guardando rúbrica... Campos Vacio superado ${missingField.name}`);
           return;
       }

       // Validar descripciones de criterios
       const emptyCriteria = rubric.criterios.find(row => !row.crfDescripcion.trim());
       if (emptyCriteria) {
           setNotification({
               type: "error",
               title: "Campo requerido",
               message: "Todos los criterios deben tener una descripción."
           });
           return;
       }

       // Validar descripciones de niveles
       const emptyLevel = rubric.criterios.some(row =>
           row.niveles.some(nivel => !nivel.nivelDescripcion.trim())
       );
       if (emptyLevel) {
           setNotification({
               type: "error",
               title: "Campo requerido",
               message: "Todos los niveles deben tener una descripción."
           });
           return;
       }

       const totalPercentage = rubric.criterios.reduce((sum, row) => sum + (parseFloat(row.crfPorcentaje.toString()) || 0), 0);
       if (Math.abs(totalPercentage - 1) > 0.0001) {
           setNotification({
               type: "error",
               title: "Porcentaje inválido",
               message: "La suma de los porcentajes debe ser exactamente 100% (1 en decimal)."
           });
           return;
       }

       const rubricData: RubricInterface = {
           idRubrica: (document.getElementById("idRubrica") as HTMLInputElement)?.value,
           nombreRubrica: (document.getElementById("nombreRubrica") as HTMLInputElement)?.value,
           materia: (document.getElementById("materia") as HTMLInputElement)?.value,
           notaRubrica: parseFloat((document.getElementById("notaRubrica") as HTMLInputElement)?.value || "0"),
           objetivoEstudio: (document.getElementById("objetivoEstudio") as HTMLInputElement)?.value,
           criterios: rubric.criterios.map(row => ({
            idCriterio: row.idCriterio,
            crfDescripcion: row.crfDescripcion,
            crfPorcentaje: parseFloat(row.crfPorcentaje.toString()),
            crfNota: row.crfNota,
            crfComentario: row.crfComentario,
            niveles: row.niveles
        })),
           estado: "ACTIVO"
       };
       console.log("Guardando rúbrica...");
       try {
           console.log("Guardando rúbrica... Intentado envio");
         const response = await updateRubric(rubric.idRubrica, rubricData);

           console.log("Guardando rúbrica..." , response);
           if (response) {
               setNotification({
                   type: "success",
                   title: "Éxito",
                   message: "Rúbrica guardada exitosamente."
               });
           } else {
               throw new Error("Error al guardar la rúbrica.");
           }
       } catch (e) {
           setNotification({
               type: "error",
               title: "Error",
               message: "Error al guardar la rúbrica."
           });
           console.error(e);
       }
   };

    const maxLevels = Math.max(...rubric.criterios.map(criterio => criterio.niveles.length));

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
                        <div className="grid w-full max-w-sm items-center gap-1.5 col-span-2">
                            <Label htmlFor="objetivoEstudio">Objetivo de Estudio</Label>
                            <Input id="objetivoEstudio" placeholder="Objetivo de la evaluación" value={rubric.objetivoEstudio} onChange={handleInputChange} />
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
                            {Array.from({ length: maxLevels }).map((_, index) => (
                                <TableHead key={index}>
                                    <div className="flex flex-col">
                                        <span>Nivel {index + 1}</span>
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
                                {Array.from({ length: maxLevels }).map((_, nivelIndex) => (
                                    <TableCell key={nivelIndex}>
                                        <Textarea
                                            value={criterio.niveles[nivelIndex]?.nivelDescripcion || ""}
                                            onChange={(e) => handleCriteriaChange(rowIndex, `niveles.${nivelIndex}.nivelDescripcion`, e.target.value)}
                                            placeholder={`Descripción nivel ${nivelIndex + 1}`}
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
                <Button onClick={handleSave} className="bg-[#0a0a6b] hover:bg-[#0a0a9b]">Guardar</Button>
            </CardFooter>
        </Card>
    );
}