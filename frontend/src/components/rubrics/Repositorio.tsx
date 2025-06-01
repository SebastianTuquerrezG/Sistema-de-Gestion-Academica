"use client"

import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { Search, Copy, Eye, Filter } from "lucide-react";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router-dom";
import { RubricInterface } from "@/interfaces/RubricInterface";
import Notification from "@/components/notifications/notification";
import DuplicateRubricModal from "@/components/Modal/DuplicateRubricModal.tsx";
import { createRubric } from "@/services/rubricService.ts";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Badge } from "@/components/ui/badge"
import { RubricInterfacePeticion } from "@/interfaces/RubricInterfacePeticion.ts";
import { Table, TableHeader, TableBody, TableRow, TableHead, TableCell } from "@/components/ui/table";
import { Tooltip, TooltipContent, TooltipTrigger } from "@/components/ui/tooltip";


type NotificationType = {
    type: "error" | "info" | "success";
    title: string;
    message: string;
}

export default function RepositorioRubricas() {
    const [notification, setNotification] = useState<NotificationType | null>(null);
    const [searchTerm, setSearchTerm] = useState("");
    const [rubrics, setRubrics] = useState<RubricInterface[]>([])
    const navigate = useNavigate();
    const [selectedRubricId, setSelectedRubricId] = useState<number | null>(null);
    const [showDuplicateModal, setShowDuplicateModal] = useState(false);
    const [rubricToDuplicate, setRubricToDuplicate] = useState<RubricInterface | null>(null);
    const [materiaFilter, setMateriaFilter] = useState("todas");
    const [estadoFilter, setEstadoFilter] = useState("todas");

    useEffect(() => {
        fetch('/rubricas.json')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Error al cargar el archivo JSON');
                }
                return response.json();
            })
            .then((data) => {
                setRubrics(data);
            })
            .catch((error) => console.error(error));
    }, []);
    useEffect(() => {
        if (notification) {
            const timeout = setTimeout(() => setNotification(null), 4000);
            return () => clearTimeout(timeout);
        }
    }, [notification]);

    const filteredRubrics = rubrics.filter((rubric) => {
        // Filtro de búsqueda
        const matchesSearch = rubric.nombreRubrica.toLowerCase().includes(searchTerm.toLowerCase()) ||
            rubric.idRubrica.toString().includes(searchTerm);

        // Filtro de materia
        const matchesMateria = materiaFilter === "todas" ||
            (materiaFilter === "v" && rubric.nombreMateria === "Ingeniería de Software III") ||
            (materiaFilter === "v" && rubric.nombreMateria === "Diseño de Experiencia de Usuario");

        // Filtro de estado
        const matchesEstado = estadoFilter === "todas" ||
            (estadoFilter === "activo" && rubric.estado === "ACTIVO") ||
            (estadoFilter === "inactivo" && rubric.estado === "INACTIVO");

        return matchesSearch && matchesMateria && matchesEstado;
    });

    //Funcion para mostrar el detalle de la rúbrica
    const handleDetail = (id: number) => {
        setSelectedRubricId(id);
        navigate(`/rubricas/detalle/${id}`);
    };

    // Function para abrir el modal de duplicar
    const handleOpenDuplicateModal = (rubric: RubricInterface) => {
        setRubricToDuplicate(rubric);
        setShowDuplicateModal(true);
    };

    // Function para duplicar la rúbrica
    const handleDuplicate = async (data: { newName: string; shareWithSamePeople: boolean; copyComments: boolean; resolvedComments: boolean }) => {
        if (!rubricToDuplicate) return;
        const duplicatedRubric: RubricInterfacePeticion = {
            idRubrica: null,
            nombreRubrica: data.newName,
            idMateria: rubricToDuplicate.materia,
            notaRubrica: rubricToDuplicate.notaRubrica,
            objetivoEstudio: rubricToDuplicate.objetivoEstudio,
            estado: "ACTIVO",
            criterios: rubricToDuplicate.criterios.map(criterio => ({
                ...criterio,
                idRubrica: null // o el id correspondiente si aplica
            })),
            raId: rubricToDuplicate.raId,
        };

        const response = await createRubric(duplicatedRubric);
        if (response && response.idRubrica !== null) {
            const newRubric: RubricInterface = {
                ...response,
                idRubrica: response.idRubrica, // Asegúrate de que el ID se asigne correctamente
                materia: rubricToDuplicate.materia,
                nombreMateria: rubricToDuplicate.nombreMateria,
                criterios: response.criterios.map(criterio => ({
                    ...criterio
                    // Completa aquí si faltan campos para CriterionInterface
                }))
            };
            setRubrics((prev) => [...prev, newRubric]);
            setNotification({
                type: "success",
                title: "Rúbrica duplicada",
                message: `La rúbrica ${data.newName} ha sido duplicada exitosamente.`,
            });
        }
        setShowDuplicateModal(false)

    };
    //Fucnion para mostrar el badge de estado
    const getStatusBadge = (status: string) => {
        switch (status) {
            case "ACTIVO":
                return <Badge className="bg-green-500">Activo</Badge>
            case "INACTIVO":
                return <Badge className="bg-gray-500">Inactivo</Badge>
            default:
                return <Badge>{status}</Badge>
        }
    }

    return (
        <div className="container mx-auto p-4">
            <h2 className="title2 border-b-2 border-red-500 inline-block text-primary mb-4">
                Repositorio de Rúbricas
            </h2>

            {/* Filtros y búsqueda */}
            <div className="mb-6 space-y-4">
                <div className="flex flex-col md:flex-row gap-4 items-stretch">
                    <div className="relative flex-1">
                        <Input
                            type="text"
                            placeholder="Buscar en repositorio..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="pl-10 w-full"
                        />
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-muted-foreground" />
                    </div>

                    <div className="flex flex-col sm:flex-row gap-2 flex-1">
                        <Select
                            defaultValue="todas"
                            onValueChange={(value) => setMateriaFilter(value)}
                        >
                            <SelectTrigger className="w-full">
                                <SelectValue placeholder="Materia" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value="todas">Todas las materias</SelectItem>
                                <SelectItem value="v">Ingeniería de Software III</SelectItem>
                                <SelectItem value="v">Diseño de Experiencia de Usuario</SelectItem>
                            </SelectContent>
                        </Select>

                        <Select
                            defaultValue="todas"
                            onValueChange={(value) => setEstadoFilter(value)}
                        >
                            <SelectTrigger className="w-full">
                                <SelectValue placeholder="Estado" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value="todas">Todos los estados</SelectItem>
                                <SelectItem value="activo">Activo</SelectItem>
                                <SelectItem value="inactivo">Inactivo</SelectItem>
                            </SelectContent>
                        </Select>

                        <Button variant="outline" className="w-full sm:w-auto">
                            <Filter className="h-4 w-4 mr-2" />
                            Filtros
                        </Button>
                    </div>
                </div>
            </div>

            {/* Tabla con componentes shadcn */}
            <div className="overflow-x-auto border border-gray-200 rounded-md">
                <Table className="min-w-[600px]">
                    <TableHeader className="sticky top-0 z-10 bg-primary">
                        <TableRow className="hover:bg-primary">
                            <TableHead className="text-primary-foreground py-3">Identificador</TableHead>
                            <TableHead className="text-primary-foreground py-3">Nombre Rubrica</TableHead>
                            <TableHead className="text-primary-foreground py-3 hidden sm:table-cell">Materia</TableHead>
                            <TableHead className="text-primary-foreground py-3 hidden md:table-cell">Estado</TableHead>
                            <TableHead className="text-primary-foreground py-3 text-center">Acciones</TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        {filteredRubrics.map((rubric) => (
                            <TableRow key={rubric.idRubrica} className="hover:bg-muted/50">
                                <TableCell
                                    className={`py-2 font-medium cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`}
                                    onClick={() => handleDetail(rubric.idRubrica)}
                                >
                                    {rubric.idRubrica}
                                </TableCell>
                                <TableCell
                                    className={`py-2 cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`}
                                    onClick={() => handleDetail(rubric.idRubrica)}
                                >
                                    {rubric.nombreRubrica}
                                </TableCell>
                                <TableCell
                                    className={`py-2 hidden sm:table-cell cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`}
                                    onClick={() => handleDetail(rubric.idRubrica)}
                                >
                                    {rubric.materia}
                                </TableCell>
                                <TableCell className="py-2 hidden md:table-cell">
                                    {getStatusBadge(rubric.estado)}
                                </TableCell>
                                <TableCell className="py-2">
                                    <div className="flex justify-center gap-1 sm:gap-2 flex-wrap">
                                        <Tooltip>
                                            <TooltipTrigger asChild>
                                                <Button
                                                    variant="ghost"
                                                    size="icon"
                                                    className="h-7 w-7 p-0 text-primary hover:text-primary/80"
                                                    onClick={() => handleDetail(rubric.idRubrica)}
                                                >
                                                    <Eye className="h-3.5 w-3.5" />
                                                    <span className="sr-only">Ver detalles</span>
                                                </Button>
                                            </TooltipTrigger>
                                            <TooltipContent>
                                                <p>Ver detalles</p>
                                            </TooltipContent>
                                        </Tooltip>

                                        <Tooltip>
                                            <TooltipTrigger asChild>
                                                <Button
                                                    variant="ghost"
                                                    size="icon"
                                                    className="h-7 w-7 p-0 text-orange-500 hover:text-orange-600"
                                                    onClick={() => handleOpenDuplicateModal(rubric)}
                                                >
                                                    <Copy className="h-3.5 w-3.5" />
                                                    <span className="sr-only">Duplicar</span>
                                                </Button>
                                            </TooltipTrigger>
                                            <TooltipContent>
                                                <p>Duplicar rúbrica</p>
                                            </TooltipContent>
                                        </Tooltip>
                                    </div>
                                </TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </div>

            {/* Modal de duplicación */}
            <DuplicateRubricModal
                open={showDuplicateModal}
                onClose={() => setShowDuplicateModal(false)}
                originalName={rubricToDuplicate?.nombreRubrica || ""}
                onDuplicate={(data) => handleDuplicate(data)}
            />

            {/* Notificación */}
            {notification && (
                <Notification
                    type={notification.type}
                    title={notification.title}
                    message={notification.message}
                    onClose={() => setNotification(null)}
                />
            )}
        </div>
    );
}