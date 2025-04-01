import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Plus, PlusCircle, Trash2 } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Table, TableHeader, TableBody, TableRow, TableHead, TableCell } from "@/components/ui/table";

export default function CreateRubric() {
    const [levels, setLevels] = useState(["Nivel 1", "Nivel 2", "Nivel 3"]);
    const [rows, setRows] = useState([
        { criterioEval: "", notas: Array(levels.length).fill(""), porcentaje: "" },
        { criterioEval: "", notas: Array(levels.length).fill(""), porcentaje: "" },
        { criterioEval: "", notas: Array(levels.length).fill(""), porcentaje: "" },
    ]);

    const addLevel = () => {
        setLevels([...levels, `Nivel ${levels.length + 1}`]);
        setRows(rows.map(row => ({ ...row, notas: [...row.notas, ""] })));
    };

    const removeLevel = () => {
        if (levels.length > 1) {
            setLevels(levels.slice(0, -1));
            setRows(rows.map(row => ({ ...row, notas: row.notas.slice(0, -1) })));
        }
    };

    const handleCriterioChange = (rowIndex: number, value: string) => {
        const newRows = [...rows];
        newRows[rowIndex].criterioEval = value;
        setRows(newRows);
    };

    const handleChange = (rowIndex: number, colIndex: number, value: string) => {
        const newRows = [...rows];
        newRows[rowIndex].notas[colIndex] = value;
        setRows(newRows);
    };

    const addRow = () => {
        setRows([...rows, { criterioEval: "", notas: Array(levels.length).fill(""), porcentaje: "" }]);
    };

    const removeRow = (index: number) => {
        if (rows.length > 1) {
            setRows(rows.filter((_, i) => i !== index));
        }
    };

    const handleCancel = () => {
        setLevels(["Nivel 1", "Nivel 2", "Nivel 3"]);
        setRows([
            {
                criterioEval: "", notas: Array(3).fill(""),
                porcentaje: ""
            },
            {
                criterioEval: "", notas: Array(3).fill(""),
                porcentaje: ""
            },
            {
                criterioEval: "", notas: Array(3).fill(""),
                porcentaje: ""
            },
        ]);
    };

    // Función para manejar la acción del botón "Crear"
    const handleCreate = () => {
        const totalPercentage = rows.reduce((sum, row) => sum + Number(row.porcentaje || 0), 0);

        if (totalPercentage !== 100) {
            alert("La suma de los porcentajes debe ser exactamente 100%.");
            return;
        }

        const rubricData = {
            nombre: (document.getElementById("nombre") as HTMLInputElement)?.value,
            descripcion: (document.getElementById("descripcion-rubrica") as HTMLInputElement)?.value,
            niveles: levels,
            criterios: rows,
        };

        localStorage.setItem("rubrica", JSON.stringify(rubricData));
        console.log("Datos guardados en localStorage:", rubricData);
        try {
            handleCancel();
        } catch (error) {
            console.error("Error al guardar los datos en localStorage:", error);
        }
    };


    return (
        <Card className="w-full max-w-[850px]">
            <CardHeader>
                <CardTitle>Crear Rúbrica de Evaluación</CardTitle>
                <CardDescription>Ingresa los datos de la nueva rúbrica en el formulario.</CardDescription>
            </CardHeader>
            <CardContent>
                <div className="space-y-4">
                    <div>
                        <Label htmlFor="nombre">Nombre</Label>
                        <Input id="nombre" placeholder="Nombre de la rúbrica" />
                    </div>
                    <div>
                        <Label htmlFor="descripcion">Descripción</Label>
                        <Input id="descripcion" placeholder="Descripción del criterio" />
                    </div>
                </div>
                <CardTitle className="mt-6">Criterios de Evaluación</CardTitle>
                <CardDescription className="mb-4">Ingresa los criterios de evaluación y sus niveles.</CardDescription>
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead>Criterio Evaluación</TableHead>
                            <TableHead>Porcentajes</TableHead>
                            {levels.map((level, index) => (
                                <TableHead key={index}>{level}</TableHead>
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
                        {rows.map((row, rowIndex) => (
                            <TableRow key={rowIndex}>
                                <TableCell>
                                    <Textarea value={row.criterioEval} onChange={(e) => handleCriterioChange(rowIndex, e.target.value)} placeholder="Descripción Criterio" />
                                </TableCell>
                                <TableCell>
                                    <div className="flex items-center">
                                        <Input
                                            type="number"
                                            min="0"
                                            max="100"
                                            onChange={(e) => {
                                                const newRows = [...rows];
                                                newRows[rowIndex].porcentaje = e.target.value;
                                                setRows(newRows);
                                            }}
                                            value={row.porcentaje || 0}
                                        />
                                        <span className="ml-1">%</span>
                                    </div>
                                </TableCell>
                                {row.notas.map((nota, colIndex) => (
                                    <TableCell key={colIndex}>
                                        <Textarea value={nota} onChange={(e) => handleChange(rowIndex, colIndex, e.target.value)} placeholder="Descripción" />
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
                        <Plus className="h-4 w-4 mr-2" />Añadir Rubrica
                    </Button>
                </div>
            </CardContent>
            <CardFooter className="flex justify-between">
                <Button onClick={handleCancel} variant="outline">Cancelar</Button>
                <Button onClick={handleCreate}>Crear</Button>
            </CardFooter>
        </Card>
    );
}
