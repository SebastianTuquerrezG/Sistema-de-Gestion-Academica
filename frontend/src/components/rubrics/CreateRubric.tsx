import { useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import {Plus, PlusCircle, Trash2} from "lucide-react"
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";

export default function CreateRubric()
{
    const [levels, setLevels] = useState(["Nivel 1", "Nivel 2", "Nivel 3"]);
    const [rows, setRows] = useState([
        { criterioEval: "", notas: Array(levels.length).fill("") },
        { criterioEval: "", notas: Array(levels.length).fill("") },
        { criterioEval: "", notas: Array(levels.length).fill("") },
    ]);

    // Add level
    const addLevel = () => {
        const newLevel = `Nivel ${levels.length + 1}`;
        setLevels([...levels, newLevel]);
        setRows(rows.map(row => ({ ...row, notas: [...row.notas, ""] })));
    };
    // Remove level
    const removeLevel = () => {
        if (levels.length > 1) {
            setLevels(levels.slice(0, -1));
            setRows(rows.map(row => ({ ...row, notas: row.notas.slice(0, -1) })));
        }
    };

    const handleChange = (rowIndex: number, colIndex: number, value: string) => {
        const newRows = [...rows];
        newRows[rowIndex].notas[colIndex] = value;
        setRows(newRows);
    };

    // Función para manejar cambios en el criterio de evaluación
    const handleCriterioChange = (rowIndex: number, value: string) => {
        const newRows = [...rows];
        newRows[rowIndex].criterioEval = value;
        setRows(newRows);
    };

    // Función para agregar una nueva fila
    const addRow = () => {
        setRows([...rows, { criterioEval: "", notas: Array(levels.length).fill("") }]);
    };

    // Función para eliminar una fila
    const removeRow = (index: number) => {
        if (rows.length > 1) {
            setRows(rows.filter((_, i) => i !== index));
        }
    };

    // Calcular las notas dividiendo 5 entre el número de niveles
    const notaValue = (5 / levels.length).toFixed(2);

    // Función para manejar la acción del botón "Crear"
    const handleCreate = () => {
        const rubricData = {
            nombre: (document.getElementById("nombre") as HTMLInputElement)?.value,
            descripcion: (document.getElementById("descripcion-rubrica") as HTMLInputElement)?.value,
            niveles: levels,
            criterios: rows,
        };

        localStorage.setItem("rubrica", JSON.stringify(rubricData));
        console.log("Datos guardados en localStorage:", rubricData);
    };
    //Funcion que reinicia los valores de levels y rows
    const handleCancel = () => {
        setLevels(["Nivel 1", "Nivel 2", "Nivel 3"]); // Restaurar niveles iniciales
        setRows([
            { criterioEval: "", notas: Array(3).fill("") },
            { criterioEval: "", notas: Array(3).fill("") },
            { criterioEval: "", notas: Array(3).fill("") },
        ]);

        // Limpiar los campos de entrada del formulario
        (document.getElementById("nombre") as HTMLInputElement).value = "";
        (document.getElementById("descripcion-rubrica") as HTMLInputElement).value = "";
    };

    return (
        <Card id="CrearRubrica" className="w-full max-w-[850px] text-[28px]">
            <CardHeader>
                <CardTitle>Crear Rúbrica de Evaluación</CardTitle>
                <CardDescription>Ingresa los datos de la nueva rubrica en el formulario.</CardDescription>
            </CardHeader>
            <CardContent>
                <form>
                    <div className="grid w-full items-center gap-4">
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="nombre">Nombre</Label>
                            <Input id="nombre" placeholder="Nombre de la rubrica" />
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="descripcion-rubrica">Descripción</Label>
                            <Input id="descripcion-rubrica" placeholder="Descripción del criterio" />
                        </div>
                        <div className="flex flex-col space-y-1.5">

                        </div>
                    </div>
                </form>
                <CardTitle>Criterios de Evaluación</CardTitle>
                <CardDescription className="mb-4">A continuación podrá ingregar la información de los criterios de evaluación de la rúbrica y sus niveles </CardDescription>

                <table className="w-full">
                    <thead>
                        <tr className="title5 bg-[#000066] text-white ">
                            <th className="border p-2 text-center ">Criterio Evaluación</th>
                            {levels.map((level, index) => (
                                <th key={index} className="border p-2">{level}</th>
                            ))}
                            <th className=" bg-white px-6 py-3 text-left">
                                <Button onClick={addLevel} variant="ghost" size="icon" className="h-8 w-8 text-[#000066] hover:text-[#3333FF]">
                                    <PlusCircle className="h-4 w-4" />
                                </Button>
                                <Button onClick={removeLevel} variant="ghost" size="icon" className="h-8 w-8 text-red-700 hover:text-red-400">
                                    <Trash2 className="h-4 w-4" />
                                </Button>
                            </th>
                        </tr>
                    </thead>
                    <tbody className="text-base">
                        <tr className="bg-gray-100">
                            <td className="border border-gray-300 px-4 py-2 title5">Nota</td>
                            {levels.map((_, index) => (
                                <td key={index} className="border p-2 text-center title5">{notaValue}</td>
                            ))}
                            <td className="border p-2"></td>
                        </tr>
                        {rows.map((row, rowIndex) => (
                            <tr key={rowIndex}>
                                <td className="border p-2">
                                <textarea
                                    value={row.criterioEval}
                                    onChange={(e) => handleCriterioChange(rowIndex, e.target.value)}
                                    placeholder="Descripcion Criterio"
                                    className="w-full p-1 border"
                                />
                                </td>
                                {row.notas.map((nota, colIndex) => (
                                    <td key={colIndex} className="border p-2">
                                    <textarea
                                        value={nota}
                                        onChange={(e) => handleChange(rowIndex, colIndex, e.target.value)}
                                        placeholder="Descripción"
                                        className="w-full p-1 border"
                                    />
                                    </td>
                                ))}
                                <td className="border p-2">
                                    <Button onClick={() => removeRow(rowIndex)} variant="ghost" size="icon" className="h-8 w-8 text-red-700 hover:text-red-400">
                                        <Trash2 className="h-4 w-4" />
                                    </Button>
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
                <div className="mt-4">
                    <Button onClick={addRow} className="bg-[#000066] text-white px-4 py-2 rounded">
                        <Plus className="h-4 w-4 mr-2" />
                        Añadir Rubrica
                    </Button>
                </div>
            </CardContent>
            <CardFooter className="flex justify-between">
                <Button onClick={handleCancel} variant="outline">Cancelar</Button>
                <Button onClick={handleCreate}>Crear</Button>
            </CardFooter>
        </Card>
    );
};