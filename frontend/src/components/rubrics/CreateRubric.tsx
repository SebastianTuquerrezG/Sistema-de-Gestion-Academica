import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Plus, PlusCircle, Trash2 } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Table, TableHeader, TableBody, TableRow, TableHead, TableCell } from "@/components/ui/table";
import Notification from "@/components/notifications/notification";
import { createRubric } from "@/services/rubricService";
import {RubricInterface} from "@/interfaces/RubricInterface.ts";


type NotificationType = {
    type: "error" | "info" | "success";
    title: string;
    message: string;
};

export default function CreateRubric() {
    const [levels, setLevels] = useState([
        { idNivel: 1, nivelDescripcion: "", rangoNota: "0-1" },
        { idNivel: 2, nivelDescripcion: "", rangoNota: "1-2" },
        { idNivel: 3, nivelDescripcion: "", rangoNota: "2-3" }
    ]);

    const [rows, setRows] = useState([
        {
            idCriterio: 1,
            crfDescripcion: "",
            niveles: levels.map(level => ({ ...level, nivelDescripcion: "" })),
            crfPorcentaje: "",
            crfNota: 0,
            crfComentario: ""
        },
        {
            idCriterio: 2,
            crfDescripcion: "",
            niveles: levels.map(level => ({ ...level, nivelDescripcion: "" })),
            crfPorcentaje: "",
            crfNota: 0,
            crfComentario: ""
        }
    ]);

    const [notification, setNotification] = useState<NotificationType | null>(null);

    useEffect(() => {
        if (notification) {
            const timeout = setTimeout(() => setNotification(null), 4000);
            return () => clearTimeout(timeout);
        }
    }, [notification]);

    const addLevel = () => {
        if (levels.length >= 5) {
            setNotification({
                type: "error",
                title: "Límite alcanzado",
                message: "No puedes tener más de 5 niveles en una rúbrica."
            });
            return;
        }

        const newId = levels.length > 0 ? Math.max(...levels.map(l => l.idNivel)) + 1 : 1;
        const newLevel = {
            idNivel: newId,
            nivelDescripcion: "",
            rangoNota: `${newId - 1}-${newId}`
        };
        setLevels([...levels, newLevel]);
        setRows(rows.map(row => ({
            ...row,
            niveles: [...row.niveles, { ...newLevel, nivelDescripcion: "" }]
        })));
    };

    const removeLevel = () => {
        if (levels.length <= 1) {
            setNotification({
                type: "error",
                title: "Acción no permitida",
                message: "Debe haber al menos un nivel en la rúbrica."
            });
            return;
        }

        setLevels(levels.slice(0, -1));
        setRows(rows.map(row => ({
            ...row,
            niveles: row.niveles.slice(0, -1)
        })));
    };

    const handleCriterioChange = (rowIndex: number, value: string) => {
        const newRows = [...rows];
        newRows[rowIndex].crfDescripcion = value;
        setRows(newRows);
    };

    const handleNivelChange = (rowIndex: number, nivelIndex: number, value: string) => {
        const newRows = [...rows];
        newRows[rowIndex].niveles[nivelIndex].nivelDescripcion = value;
        setRows(newRows);
    };

    const handlePorcentajeChange = (rowIndex: number, value: string) => {
        const numericValue = parseFloat(value);
        if (!isNaN(numericValue) && (numericValue < 0 || numericValue > 1)) {
            setNotification({
                type: "error",
                title: "Error",
                message: "El porcentaje debe estar entre 0 y 1."
            });
            return;
        }

        const newRows = [...rows];
        newRows[rowIndex].crfPorcentaje = value;
        setRows(newRows);
    };

    const handleComentarioChange = (rowIndex: number, value: string) => {
        const newRows = [...rows];
        newRows[rowIndex].crfComentario = value;
        setRows(newRows);
    };

    const addRow = () => {
        if (rows.length >= 10) {
            setNotification({
                type: "error",
                title: "Límite alcanzado",
                message: "No puedes tener más de 10 criterios en una rúbrica."
            });
            return;
        }

        const newId = rows.length > 0 ? Math.max(...rows.map(r => r.idCriterio)) + 1 : 1;
        setRows([...rows, {
            idCriterio: newId,
            crfDescripcion: "",
            niveles: levels.map(level => ({ ...level, nivelDescripcion: "" })),
            crfPorcentaje: "",
            crfNota: 0,
            crfComentario: ""
        }]);
    };

    const removeRow = (index: number) => {
        if (rows.length <= 1) {
            setNotification({
                type: "error",
                title: "Acción no permitida",
                message: "Debe haber al menos un criterio en la rúbrica."
            });
            return;
        }

        setRows(rows.filter((_, i) => i !== index));
    };

    const handleCancel = () => {
        setLevels([
            { idNivel: 1, nivelDescripcion: "", rangoNota: "0-1" },
            { idNivel: 2, nivelDescripcion: "", rangoNota: "1-2" },
            { idNivel: 3, nivelDescripcion: "", rangoNota: "2-3" }
        ]);
        setRows([
            {
                idCriterio: 1,
                crfDescripcion: "",
                niveles: [
                    { idNivel: 1, nivelDescripcion: "", rangoNota: "0-1" },
                    { idNivel: 2, nivelDescripcion: "", rangoNota: "1-2" },
                    { idNivel: 3, nivelDescripcion: "", rangoNota: "2-3" }
                ],
                crfPorcentaje: "",
                crfNota: 0,
                crfComentario: ""
            },
            {
                idCriterio: 2,
                crfDescripcion: "",
                niveles: [
                    { idNivel: 1, nivelDescripcion: "", rangoNota: "0-1" },
                    { idNivel: 2, nivelDescripcion: "", rangoNota: "1-2" },
                    { idNivel: 3, nivelDescripcion: "", rangoNota: "2-3" }
                ],
                crfPorcentaje: "",
                crfNota: 0,
                crfComentario: ""
            }
        ]);
    };

    const handleCreate = () => {
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
            return;
        }

        // Validar descripciones de criterios
        const emptyCriteria = rows.find(row => !row.crfDescripcion.trim());
        if (emptyCriteria) {
            setNotification({
                type: "error",
                title: "Campo requerido",
                message: "Todos los criterios deben tener una descripción."
            });
            return;
        }

        // Validar descripciones de niveles
        const emptyLevel = rows.some(row =>
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

        const totalPercentage = rows.reduce((sum, row) => sum + (parseFloat(row.crfPorcentaje) || 0), 0);
        if (Math.abs(totalPercentage - 1) > 0.0001) {
            setNotification({
                type: "error",
                title: "Porcentaje inválido",
                message: "La suma de los porcentajes debe ser exactamente 100% (1 en decimal)."
            });
            return;
        }

        const rubricData: RubricInterface = {
            rubricaId: '' ,
            nombreRubrica: (document.getElementById("nombreRubrica") as HTMLInputElement)?.value,
            materia: (document.getElementById("materia") as HTMLInputElement)?.value,
            notaRubrica: parseFloat((document.getElementById("notaRubrica") as HTMLInputElement)?.value || "0"),
            objetivoEstudio: (document.getElementById("objetivoEstudio") as HTMLInputElement)?.value,
            criterios: rows.map(row => ({
                idCriterio: row.idCriterio,
                crfDescripcion: row.crfDescripcion,
                crfPorcentaje: parseFloat(row.crfPorcentaje),
                crfNota: row.crfNota,
                crfComentario: row.crfComentario,
                niveles: row.niveles
            })),
            estado: "ACTIVO"
        };

        try {
            createRubric(rubricData).then(r => console.log(r));
            setNotification({
                type: "success",
                title: "Éxito",
                message: "La rúbrica ha sido creada correctamente."
            });
            handleCancel();
        } catch (error) {
            console.error("Error al crear la rúbrica:", error);
            setNotification({
                type: "error",
                title: "Error",
                message: "Ocurrió un error al crear la rúbrica."
            });
        }
    };

    return (
        <Card className="w-full max-w-[1200px] relative">
            {notification && (
                <Notification
                    type={notification.type}
                    title={notification.title}
                    message={notification.message}
                    onClose={() => setNotification(null)}
                />
            )}

            <CardHeader>
                <CardTitle>Crear Rúbrica de Evaluación</CardTitle>
                <CardDescription>Ingresa los datos de la nueva rúbrica en el formulario.</CardDescription>
            </CardHeader>
            <CardContent>
                <div className="space-y-4">
                    <div className="grid grid-cols-2 gap-4">
                        <div className="grid w-full max-w-sm items-center gap-1.5">
                            <Label htmlFor="idRubrica">ID Rúbrica</Label>
                            <Input id="idRubrica" placeholder="Ej: IS102" />
                        </div>
                        <div className="grid w-full max-w-sm items-center gap-1.5">
                            <Label htmlFor="nombreRubrica">Nombre Rúbrica</Label>
                            <Input id="nombreRubrica" placeholder="Nombre de la rúbrica" />
                        </div>
                        <div className="grid w-full max-w-sm items-center gap-1.5">
                            <Label htmlFor="materia">Materia</Label>
                            <Input id="materia" placeholder="Nombre de la materia" />
                        </div>
                        <div className="grid w-full max-w-sm items-center gap-1.5">
                            <Label htmlFor="notaRubrica">Nota Máxima Rúbrica</Label>
                            <Input id="notaRubrica" type="number" placeholder="Ej: 3" />
                        </div>
                        <div className="grid w-full max-w-sm items-center gap-1.5 col-span-2">
                            <Label htmlFor="objetivoEstudio">Objetivo de Estudio</Label>
                            <Input id="objetivoEstudio" placeholder="Objetivo de la evaluación" />
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
                            <TableHead>Comentario</TableHead>
                            {levels.map((level, index) => (
                                <TableHead key={index}>
                                    <div className="flex flex-col">
                                        <span>Nivel {level.idNivel}</span>
                                        <span className="text-xs">{level.rangoNota}</span>
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
                        {rows.map((row, rowIndex) => (
                            <TableRow key={rowIndex}>
                                <TableCell>
                                    <Textarea
                                        value={row.crfDescripcion}
                                        onChange={(e) => handleCriterioChange(rowIndex, e.target.value)}
                                        placeholder="Descripción del criterio"
                                    />
                                </TableCell>
                                <TableCell>
                                    <div className="flex items-center">
                                        <Input
                                            type="number"
                                            min="0"
                                            max="1"
                                            step="0.01"
                                            onChange={(e) => handlePorcentajeChange(rowIndex, e.target.value)}
                                            value={row.crfPorcentaje}
                                            placeholder="0.00"
                                        />
                                    </div>
                                </TableCell>
                                <TableCell>
                                    <Textarea
                                        value={row.crfComentario}
                                        onChange={(e) => handleComentarioChange(rowIndex, e.target.value)}
                                        placeholder="Comentario para el criterio"
                                    />
                                </TableCell>
                                {row.niveles.map((nivel, nivelIndex) => (
                                    <TableCell key={nivelIndex}>
                                        <Textarea
                                            value={nivel.nivelDescripcion}
                                            onChange={(e) => handleNivelChange(rowIndex, nivelIndex, e.target.value)}
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
                <Button onClick={handleCancel} variant="outline">Cancelar</Button>
                <Button onClick={handleCreate}>Crear</Button>
            </CardFooter>
        </Card>
    );
}