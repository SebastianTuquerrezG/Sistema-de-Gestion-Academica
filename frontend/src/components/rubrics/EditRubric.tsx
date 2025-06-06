import { useEffect, useState } from "react";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Plus, PlusCircle, Trash2 } from "lucide-react";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Table, TableHeader, TableBody, TableRow, TableHead, TableCell } from "@/components/ui/table";
import Notification from "@/components/notifications/notification";
import { getAllMaterias, updateRubric } from "@/services/rubricService";
import { useNavigate } from "react-router-dom";
import { MateriaInterface } from "@/interfaces/MateriaInterface.ts";
import { RubricInterfacePeticion } from "@/interfaces/RubricInterfacePeticion.ts";
import { useParams } from "react-router-dom";
import { getAllRAs } from "@/services/raService.ts";
import { RaInteface } from "@/interfaces/RaInterface";
import { getRubricById } from "@/services/rubricService";
import {
    Select,
    SelectContent,
    SelectGroup,
    SelectItem,
    SelectLabel,
    SelectTrigger,
    SelectValue,
} from "@/components/ui/select"
//Definicion del tipo de notificacion
type NotificationType = {
    type: "error" | "info" | "success";
    title: string;
    message: string;
};

export default function CreateRubric() {
    const { id } = useParams<{ id: string }>();
    const navigate = useNavigate(); // Hook para la navegación

    // Estado local para almacenar el valor de la nota (puede ser número o vacío)
    const [nota, setNota] = useState<number | "">("");

    // Estado para guardar la nota máxima ingresada por el usuario.
    //const [notaMaxima] = useState<number | null>(null);

    const [levels, setLevels] = useState([
        { idNivel: 1, nivelDescripcion: "", rangoNota: "" },
        { idNivel: 2, nivelDescripcion: "", rangoNota: "" },
        { idNivel: 3, nivelDescripcion: "", rangoNota: "" }
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
    const [materia, setMateria] = useState("");
    const [materias, setMaterias] = useState<MateriaInterface[]>([]);
    const [resultadosAprendizaje, setResultadosAprendizaje] = useState<RaInteface[]>([]);
    const [resultadoAprendizaje, setResultadoAprendizaje] = useState("");

    useEffect(() => {
        if (id) {
            getRubricById(id).then((data) => {
                if (!data) return;
                setNota(data.notaRubrica || "");
                setMateria(data.idMateria?.toString() || "");
                setResultadoAprendizaje(data.raId?.toString() || "");
                if (data.criterios && data.criterios.length > 0) {
                    setLevels(data.criterios[0].niveles.map((nivel: any) => ({
                        idNivel: nivel.idNivel,
                        nivelDescripcion: nivel.nivelDescripcion,
                        rangoNota: nivel.rangoNota
                    })));
                }
                setRows(data.criterios.map((criterio: any, idx: number) => ({
                    idCriterio: idx + 1,
                    crfDescripcion: criterio.crfDescripcion,
                    niveles: criterio.niveles.map((nivel: any) => ({
                        idNivel: nivel.idNivel,
                        nivelDescripcion: nivel.nivelDescripcion,
                        rangoNota: nivel.rangoNota
                    })),
                    crfPorcentaje: criterio.crfPorcentaje.toString(),
                    crfNota: criterio.crfNota,
                    crfComentario: criterio.crfComentario || ""
                })));
                // Rellena los inputs manualmente si no son controlados
                (document.getElementById("nombreRubrica") as HTMLInputElement).value = data.nombreRubrica || "";
                (document.getElementById("objetivoEstudio") as HTMLInputElement).value = data.objetivoEstudio || "";
            });
        }
    }, [id]);

    //Efecto de la notificacion
    useEffect(() => {
        if (notification) {
            const timeout = setTimeout(() => setNotification(null), 4000);
            return () => clearTimeout(timeout);
        }
    }, [notification]);

    //Efecto para traer las materias
    useEffect(() => {
        getAllMaterias()
            .then(data => {
                setMaterias(data);
            })
            .catch(() => setMaterias([]));
    }, []);

    useEffect(() => {
        getAllRAs()
            .then(data => {
                setResultadosAprendizaje(data);
            })
            .catch(() => setResultadosAprendizaje([]));
    }, []);

    // Añade un nuevo nivel a la rúbrica, hasta un máximo de 5
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
        const nuevosLevels = [
            ...levels,
            { idNivel: newId, nivelDescripcion: "", rangoNota: "" }
        ];

        // Recalcular rangos para todos los niveles
        const actualizados = handleRangosChanges(typeof nota === "number" ? nota : null, nuevosLevels);

        setLevels(actualizados);

        setRows(rows.map(row => ({
            ...row,
            niveles: nuevosLevels.map((level, index) => ({
                ...level,
                nivelDescripcion: row.niveles[index]?.nivelDescripcion || "",
                rangoNota: level.rangoNota
            }))
        })));
    };

    // Elimina el último nivel de la lista, asegurando al menos 1 nivel
    const removeLevel = () => {
        if (levels.length <= 1) {
            setNotification({
                type: "error",
                title: "Acción no permitida",
                message: "Debe haber al menos un nivel en la rúbrica."
            });
            return;
        }

        const nuevosLevels = levels.slice(0, -1);

        const notaActual = typeof nota === "number" ? nota : null;
        // Recalcular rangos y preservar los existentes
        const actualizados = handleRangosChanges(notaActual, nuevosLevels);

        setLevels(actualizados);

        setRows(rows.map(row => ({
            ...row,
            niveles: row.niveles.slice(0, -1).map((nivel, index) => ({
                ...nivel,
                nivelDescripcion: row.niveles[index]?.nivelDescripcion || "",
                rangoNota: actualizados[index]?.rangoNota || nivel.rangoNota
            }))
        })));
    };

    //Manejador que calcula los rangos de nota para cada nivel segun la nota maxima
    const handleRangosChanges = (nuevaNotaMaxima: number | null, nivelesActuales: typeof levels) => {
        if (!nuevaNotaMaxima || nivelesActuales.length === 0) {
            // Si no hay nota máxima, dejamos los rangos vacíos
            return nivelesActuales.map(level => ({ ...level, rangoNota: "" }));
        }

        // Calculamos el tamaño de cada paso
        const paso = nuevaNotaMaxima / nivelesActuales.length;

        // Creamos un rango para cada nivel basado en el paso
        return nivelesActuales.map((level, index) => ({
            ...level,
            rangoNota: `${(paso * index).toFixed(2)}-${(paso * (index + 1)).toFixed(2)}`
        }));
    };

    // Manejador que se ejecuta cuando cambia el valor del input en Nota Maxima de la Rubrica
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.target.value;

        // Validar formato con expresión regular (un dígito entero entre 0-5 y hasta dos decimales)
        const regex = /^[0-5](\.\d{0,2})?$/;

        if (!regex.test(value)) {
            setNotification({
                type: "error",
                title: "Formato Inválido",
                message: "La nota debe estar entre 0.00 y 5.00, con un máximo de dos decimales.",
            });
            setNota("");
            return;
        }

        const numericValue = parseFloat(value);

        // Validar rango entre 0.00 y 5.00
        if (numericValue > 5 || numericValue < 0) {
            setNotification({
                type: "error",
                title: "Nota Fuera de Rango",
                message: "La nota debe estar entre 0.00 y 5.00.",
            });
            setNota("");
        } else {
            setNota(value === "" ? "" : parseFloat(value));

            // Recalcular los rangos de los niveles sin borrar las descripciones existentes
            const nuevosLevels = handleRangosChanges(numericValue, levels);
            setLevels(nuevosLevels);

            // Actualizar los niveles en las filas de criterios sin borrar las descripciones
            setRows(rows.map(row => ({
                ...row,
                niveles: row.niveles.map((nivel, index) => ({
                    ...nivel,
                    rangoNota: nuevosLevels[index]?.rangoNota || nivel.rangoNota
                }))
            })));
        }
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

    // Manejador que se ejecuta cuando cambia el valor del input en Porcentaje de un criterio
    const handlePorcentajeChange = (rowIndex: number, value: string) => {
        const numericValue = parseFloat(value);
        //validacion individual de rango
        if (!isNaN(numericValue) && (numericValue < 0 || numericValue > 100)) {
            setNotification({
                type: "error",
                title: "Porcentaje Fuera de Rango",
                message: "El valor ingresado debe estar entre 0% y 100%"
            });
            return;
        }
        //clonar filas yactualizar el valor en la fila correspondiente
        const newRows = [...rows];
        newRows[rowIndex].crfPorcentaje = value;
        setRows(newRows);

        // Convertir todos los valores a número y calcular la suma total
        const totalPorcentaje = newRows.reduce((total, row) => {
            const val = parseFloat(row.crfPorcentaje);
            return total + (isNaN(val) ? 0 : val);
        }, 0);

        // Mostrar notificaciones según el total
        if (totalPorcentaje > 100) {
            setNotification({
                type: "error",
                title: "Porcentaje Excedido",
                message: `La suma total de los porcentajes es ${totalPorcentaje}%. No puede exceder 100%.`,
            });
        } else if (totalPorcentaje < 100) {
            setNotification({
                type: "info",
                title: "Suma Incompleta",
                message: `La suma total es ${totalPorcentaje}%. Asegúrate de que la suma sea exactamente 100%.`,
            });
        } else {
            setNotification({
                type: "success",
                title: "Porcentaje Correcto",
                message: "La suma total de los porcentajes es 100%.",
            });
        }
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
            { idNivel: 1, nivelDescripcion: "", rangoNota: "" },
            { idNivel: 2, nivelDescripcion: "", rangoNota: "" },
            { idNivel: 3, nivelDescripcion: "", rangoNota: "" }
        ]);
        setRows([
            {
                idCriterio: 1,
                crfDescripcion: "",
                niveles: [
                    { idNivel: 1, nivelDescripcion: "", rangoNota: "" },
                    { idNivel: 2, nivelDescripcion: "", rangoNota: "" },
                    { idNivel: 3, nivelDescripcion: "", rangoNota: "" }
                ],
                crfPorcentaje: "",
                crfNota: 0,
                crfComentario: ""
            }
        ]);
    };

    const handleCreate = async () => {
        // Validar campos obligatorios
        const requiredFields = [
            { field: (document.getElementById("nombreRubrica") as HTMLInputElement)?.value, name: "Nombre Rúbrica" },
            { field: materia, name: "Materia" },
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
        if (Math.abs(totalPercentage - 100) > 0.0001) {
            setNotification({
                type: "error",
                title: "Porcentaje inválido",
                message: "La suma de los porcentajes debe ser exactamente 100%."
            });
            return;
        }
        const nuevosLevels = handleRangosChanges(typeof nota === "number" ? nota : null, levels);

        const rubricData: RubricInterfacePeticion = {
            idRubrica: Number(id),
            nombreRubrica: (document.getElementById("nombreRubrica") as HTMLInputElement)?.value,
            idMateria: parseFloat(materia),
            notaRubrica: parseFloat((document.getElementById("notaRubrica") as HTMLInputElement)?.value || "0"),
            objetivoEstudio: (document.getElementById("objetivoEstudio") as HTMLInputElement)?.value,
            criterios: rows.map(row => ({
                crfDescripcion: row.crfDescripcion,
                crfPorcentaje: parseFloat(row.crfPorcentaje),
                crfNota: row.crfNota,
                crfComentario: row.crfComentario,
                niveles: row.niveles.map((nivel, index) => ({
                    idCriterio: null,
                    nivelDescripcion: nivel.nivelDescripcion,
                    rangoNota: nuevosLevels[index]?.rangoNota || nivel.rangoNota,
                    idNivel: nivel.idNivel
                })),
                idRubrica: null
            })),
            raId: resultadoAprendizaje ? parseInt(resultadoAprendizaje) : 0,
            estado: "ACTIVO"
        };
        try {
            await updateRubric(rubricData.idRubrica, rubricData);
            setNotification({
                type: "success",
                title: "Éxito",
                message: "La rúbrica ha sido actualizada correctamente."
            });
            handleCancel();
        } catch (error) {
            console.error("Error al actualizar la rúbrica:", error);
            setNotification({
                type: "error",
                title: "Error",
                message: "Ocurrió un error al actualizar la rúbrica."
            });
        }
    };
    const handleBackToHome = () => {
        navigate(`/rubricas/`);
    }

    return (
        <div className="container mx-auto p-4 space-y-6">
            <div className="flex justify-between items-center">
                <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>
                    Editar Rúbrica
                </h2>
            </div>
            <Card>
                {notification && (
                    <Notification
                        type={notification.type}
                        title={notification.title}
                        message={notification.message}
                        onClose={() => setNotification(null)}
                    />
                )}
                <CardHeader>
                    <CardTitle>Editar Rúbrica de Evaluación</CardTitle>
                    <CardDescription>Ingresa los datos de la rúbrica en el formulario.</CardDescription>
                </CardHeader>
                <CardContent>
                    <div className="space-y-4">
                        <div className="flex flex-col gap-4 sm:grid sm:grid-cols-2">
                            <div className="grid w-full items-center gap-1.5">
                                <Label htmlFor="nombreRubrica">Nombre Rúbrica</Label>
                                <Input id="nombreRubrica" placeholder="Nombre de la rúbrica" />
                            </div>
                            <div className="grid w-full items-center gap-1.5">
                                <Label htmlFor="materia">Materia</Label>
                                <Select value={materia} onValueChange={setMateria}>
                                    <SelectTrigger className="w-50">
                                        <SelectValue placeholder="Seleccione una materia" />
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectGroup>
                                            <SelectLabel>Materias</SelectLabel>
                                            {materias.map((mat) => (
                                                <SelectItem key={mat.idMateria} value={mat.idMateria.toString()}>
                                                    {mat.name}
                                                </SelectItem>
                                            ))}
                                        </SelectGroup>
                                    </SelectContent>
                                </Select>
                            </div>
                            <div className="grid w-full items-center gap-1.5">
                                <Label htmlFor="resultadoAprendizaje">Resultado de Aprendizaje</Label>
                                <Select value={resultadoAprendizaje} onValueChange={setResultadoAprendizaje}>
                                    <SelectTrigger className="w-50">
                                        <SelectValue placeholder="Seleccione un resultado" />
                                    </SelectTrigger>
                                    <SelectContent>
                                        <SelectGroup>
                                            <SelectLabel>Resultados de Aprendizaje</SelectLabel>
                                            {resultadosAprendizaje.map((ra) => (
                                                <SelectItem key={ra.id} value={ra.id.toString()}>
                                                    {ra.name}
                                                </SelectItem>
                                            ))}
                                        </SelectGroup>
                                    </SelectContent>
                                </Select>
                            </div>
                            <div className="grid w-full items-center gap-1.5">
                                <Label htmlFor="notaRubrica" className="whitespace-nowrap">Nota Máxima Rúbrica</Label>
                                <Input
                                    id="notaRubrica"
                                    type="number"
                                    min={0}
                                    max={5}
                                    step={0.1}
                                    value={nota}
                                    onChange={handleChange}
                                    placeholder="0.0 - 5.0"
                                />
                            </div>
                            <div className="grid w-full items-center gap-1.5 sm:col-span-2">
                                <Label htmlFor="objetivoEstudio">Objetivo de Estudio</Label>
                                <Input id="objetivoEstudio" placeholder="Objetivo de la evaluación" />
                            </div>
                        </div>
                    </div>

                    <CardTitle className="mt-6">Criterios de Evaluación</CardTitle>
                    <CardDescription className="mb-4">Ingresa los criterios de evaluación y sus niveles.</CardDescription>

                    <div className="overflow-x-auto">
                        <Table className="min-w-[800px]">
                            <TableHeader>
                                <TableRow>
                                    <TableHead className="min-w-[150px]">Criterio Evaluación</TableHead>
                                    <TableHead className="w-[100px]">Porcentaje</TableHead>
                                    <TableHead className="min-w-[150px]">Comentario</TableHead>
                                    {levels.map((level, index) => (
                                        <TableHead key={index} className="min-w-[150px]">
                                            <div className="flex flex-col">
                                                <span>Nivel {level.idNivel}</span>
                                                <span className="text-xs">{level.rangoNota}</span>
                                            </div>
                                        </TableHead>
                                    ))}
                                    <TableHead className="w-[100px]">
                                        <div className="flex gap-1">
                                            <Button onClick={addLevel} variant="ghost" size="icon">
                                                <PlusCircle className="h-4 w-4" />
                                            </Button>
                                            <Button onClick={removeLevel} variant="ghost" size="icon" className="text-red-600">
                                                <Trash2 className="h-4 w-4" />
                                            </Button>
                                        </div>
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
                                                className="min-h-[80px]"
                                            />
                                        </TableCell>
                                        <TableCell>
                                            <Input
                                                type="number"
                                                min="0"
                                                max="100"
                                                step="0.1"
                                                onChange={(e) => handlePorcentajeChange(rowIndex, e.target.value)}
                                                value={row.crfPorcentaje}
                                                placeholder="0.0%"
                                            />
                                        </TableCell>
                                        <TableCell>
                                            <Textarea
                                                value={row.crfComentario}
                                                onChange={(e) => handleComentarioChange(rowIndex, e.target.value)}
                                                placeholder="Comentario para el criterio"
                                                className="min-h-[80px]"
                                            />
                                        </TableCell>
                                        {row.niveles.map((nivel, nivelIndex) => (
                                            <TableCell key={nivelIndex}>
                                                <Textarea
                                                    value={nivel.nivelDescripcion}
                                                    onChange={(e) => handleNivelChange(rowIndex, nivelIndex, e.target.value)}
                                                    placeholder={`Descripción nivel ${nivel.idNivel}`}
                                                    className="min-h-[80px]"
                                                />
                                            </TableCell>
                                        ))}
                                        <TableCell className="text-center">
                                            <Button
                                                onClick={() => removeRow(rowIndex)}
                                                variant="ghost"
                                                size="icon"
                                                className="text-red-600"
                                            >
                                                <Trash2 className="h-4 w-4" />
                                            </Button>
                                        </TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </div>

                    <div className="mt-4">
                        <Button onClick={addRow}>
                            <Plus className="h-4 w-4 mr-2" />Añadir Criterio
                        </Button>
                    </div>
                </CardContent>
                <CardFooter className="flex flex-col gap-4 sm:flex-row sm:justify-between">
                    <Button onClick={handleCancel} variant="outline" className="w-full sm:w-auto">Cancelar</Button>
                    <div className="flex flex-col gap-2 sm:flex-row sm:gap-4 w-full sm:w-auto">
                        <Button onClick={handleBackToHome} variant="outline" className="w-full sm:w-auto">Volver</Button>
                        <Button onClick={handleCreate} className="w-full sm:w-auto">Editar</Button>
                    </div>
                </CardFooter>
            </Card>
        </div >
    );
}