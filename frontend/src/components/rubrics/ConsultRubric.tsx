"use client"

"use client"

import { useEffect, useState } from "react";
import { Search, Plus, Pencil, Trash2, Share2, Copy } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import {
    Table,
    TableHeader,
    TableRow,
    TableHead,
    TableBody,
    TableCell
} from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import {
    Select,
    SelectContent,
    SelectItem,
    SelectTrigger,
    SelectValue
} from "@/components/ui/select";
import { Skeleton } from "@/components/ui/skeleton";
import { useNavigate } from "react-router-dom";
import Notification from "@/components/notifications/notification";
import { RubricInterface } from "@/interfaces/RubricInterface";
import { getAllRubrics, deleteRubric, createRubric } from "@/services/rubricService";
import DuplicateRubricModal from "@/components/Modal/DuplicateRubricModal.tsx";
import ShareRubricModal from "@/components/Modal/ShareRubricModal.tsx";
import ConfirmDeleteModal from "@/components/Modal/ConfirmDeleteModal";
import { RubricInterfacePeticion } from "@/interfaces/RubricInterfacePeticion.ts";

type NotificationType = {
    type: "error" | "info" | "success";
    title: string;
    message: string;
};

export default function ConsultarRubrica() {
    const [searchTerm, setSearchTerm] = useState("");
    const [selectedMateria, setSelectedMateria] = useState("");
    const [selectedEstado, setSelectedEstado] = useState("");
    const [rubrics, setRubrics] = useState<RubricInterface[]>([]);
    const [isLoading, setIsLoading] = useState(true);
    const navigate = useNavigate();
    const [selectedRubricId, setSelectedRubricId] = useState<string | null>(null);
    const [showDuplicateModal, setShowDuplicateModal] = useState(false);
    const [rubricToDuplicate, setRubricToDuplicate] = useState<RubricInterface | null>(null);
    const [showShareModal, setShowShareModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [rubricToDelete, setRubricToDelete] = useState<RubricInterface | null>(null);
    const [notification, setNotification] = useState<NotificationType | null>(null);

    useEffect(() => {
        setIsLoading(true);
        getAllRubrics()
            .then(data => {
                setRubrics(data);
                setIsLoading(false);
            })
            .catch((error) => {
                console.error(error);
                setIsLoading(false);
            });
    }, []);

    useEffect(() => {
        if (notification) {
            const timeout = setTimeout(() => setNotification(null), 4000);
            return () => clearTimeout(timeout);
        }
    }, [notification]);

    const filteredRubrics = rubrics.filter(
        (rubric) =>
            (rubric.nombreRubrica.toLowerCase().includes(searchTerm.toLowerCase()) ||
                String(rubric.idRubrica).includes(searchTerm)) &&
            (selectedMateria == "Todas las materias" || selectedMateria === "" || rubric.nombreMateria === selectedMateria) &&
            (selectedEstado == "Todos los estados" || selectedEstado === "" || rubric.estado === selectedEstado)
    );

    const handleEdit = (id: number) => {
        navigate(`/rubricas/editar/${id}`);
    };
    const handleAdd = () => {
        navigate(`/rubricas/crear`);
    };
    const handleDetail = (id: number) => {
        setSelectedRubricId(String(id));
        navigate(`/rubricas/detalle/${id}`);
    };

    // Eliminar rúbrica
    const handleDelete = async (id: number) => {
        const success = await deleteRubric(id);
        if (success) {
            setRubrics(rubrics.map(rubric =>
                rubric.idRubrica === id
                    ? { ...rubric, estado: "INACTIVO" }
                    : rubric
            ));
            setNotification({
                type: "success",
                title: "Rúbrica inactivada",
                message: "La rúbrica ha sido inactivada correctamente.",
            });
        } else {
            setNotification({
                type: "error",
                title: "Error",
                message: "No se pudo inactivar la rúbrica.",
            });
        }
    };

    const handleOpenDuplicateModal = (rubric: RubricInterface) => {
        setRubricToDuplicate(rubric);
        setShowDuplicateModal(true);
    };

    // Duplicar rúbrica usando la interfaz correcta
    const handleDuplicate = async (data: { newName: string; shareWithSamePeople: boolean; copyComments: boolean; resolvedComments: boolean }) => {
        if (!rubricToDuplicate) return;

        /*const duplicatedRubric: RubricInterface = {
            idRubrica: 0, // El ID se asignará automáticamente al crear la rúbrica
            nombreRubrica: `${data.newName}`,
            materia: rubricToDuplicate.materia,
            notaRubrica: rubricToDuplicate.notaRubrica,
            objetivoEstudio: rubricToDuplicate.objetivoEstudio,
            estado: "ACTIVO",
            criterios: rubricToDuplicate.criterios.map(criterio => ({
                idCriterio: 0,
                crfDescripcion: criterio.crfDescripcion,
                crfPorcentaje: criterio.crfPorcentaje,
                crfNota: criterio.crfNota,
                crfComentario: criterio.crfComentario,
                niveles: criterio.niveles
            })),
            raId: rubricToDuplicate.raId,
        };*/
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
    const handleShareRubric = async (data: {
        email: string;
        permission: string;
        message: string;
        notifyUsers: boolean;
        createLink: boolean;
        linkPermission: string
    }) => {
        if (!rubricToDuplicate) return;
        // Lógica de compartir
        console.log("Compartiendo rúbrica:", rubricToDuplicate, data);
        setShowShareModal(false);
    };

    const handleOpenShareModal = (rubric: RubricInterface) => {
        setRubricToDuplicate(rubric);
        setShowShareModal(true);
    }

    const getStatusBadge = (status: string) => {
        switch (status) {
            case "ACTIVO":
                return <Badge className="bg-green-500">Activo</Badge>;
            case "INACTIVO":
                return <Badge className="bg-gray-500">Inactivo</Badge>;
            default:
                return <Badge className="text-purple-500">Desconocido</Badge>;
        }
    };

    const handleOpenDeleteModal = (rubric: RubricInterface) => {
        setRubricToDelete(rubric);
        setShowDeleteModal(true);
    };

    const handleConfirmDelete = async () => {
        if (rubricToDelete) {
            await handleDelete(rubricToDelete.idRubrica);
            setRubricToDelete(null);
            setShowDeleteModal(false);
        }
    };

    return (
        <div>
            <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>
                Mis Rubricas
            </h2>
            <main className="max-w-7xl mx-auto py-8">
                <div className="mb-6 flex flex-col sm:flex-row gap-4 justify-between items-start sm:items-center">
                    <div className="relative flex-1 max-w-md">
                        <Input type="text" placeholder=" Ingrese Rubrica a buscar..." value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)} style={{ width: "100%" }} className="pl-10 w-full"
                        />
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                    </div>
                    <div className="flex gap-2">
                        <Select value={selectedMateria} onValueChange={setSelectedMateria}>
                            <SelectTrigger className="w-[180px]">
                                <SelectValue placeholder="Filtrar por materia" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value="Todas las materias">Todas las materias </SelectItem>
                                {[...new Set(rubrics.map(r => r.nombreMateria))]
                                    .filter((materia): materia is string => typeof materia === "string")
                                    .map((materia) => (
                                        <SelectItem key={materia} value={materia}>
                                            {materia}
                                        </SelectItem>
                                    ))}
                            </SelectContent>
                        </Select>
                        <Select value={selectedEstado} onValueChange={setSelectedEstado}>
                            <SelectTrigger className="w-[180px]">
                                <SelectValue placeholder="Filtrar por estado" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value="Todos los estados">Todos los estados</SelectItem>
                                <SelectItem value="ACTIVO">Activo</SelectItem>
                                <SelectItem value="INACTIVO">Inactivo</SelectItem>
                            </SelectContent>
                        </Select>
                    </div>
                    <div className="flex gap-2">
                        <Button className="outline" onClick={handleAdd}>
                            <Plus className="h-4 w-4 mr-2" />
                            Crear Rubrica
                        </Button>
                    </div>
                </div>
                <div>
                    <Table>
                        <TableHeader>
                            <TableRow className="bg-[#000066] hover:bg-[#000066]">
                                <TableHead className="text-white">Identificador</TableHead>
                                <TableHead className="text-white">Nombre Rubrica</TableHead>
                                <TableHead className="text-white">Materia</TableHead>
                                <TableHead className="text-white">Estado</TableHead>
                                <TableHead className="text-center text-white">Acciones</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {isLoading ? (
                                Array.from({ length: 5 }).map((_, index) => (
                                    <TableRow key={index}>
                                        <TableCell>
                                            <Skeleton className="h-4 w-[50px]" />
                                        </TableCell>
                                        <TableCell>
                                            <Skeleton className="h-4 w-[200px]" />
                                        </TableCell>
                                        <TableCell>
                                            <Skeleton className="h-4 w-[150px]" />
                                        </TableCell>
                                        <TableCell>
                                            <Skeleton className="h-6 w-[70px] rounded-full" />
                                        </TableCell>
                                        <TableCell>
                                            <div className="flex justify-center gap-2">
                                                {Array.from({ length: 4 }).map((_, i) => (
                                                    <Skeleton key={i} className="h-8 w-8 rounded-md" />
                                                ))}
                                            </div>
                                        </TableCell>
                                    </TableRow>
                                ))
                            ) : (
                                filteredRubrics.map((rubric) => (
                                    <TableRow key={rubric.idRubrica} className={rubric.estado === "INACTIVO" ? "opacity-70" : ""}>
                                        <TableCell
                                            className={`cursor-pointer ${selectedRubricId === String(rubric.idRubrica) ? "text-blue-600" : ""}`}
                                            onClick={() => handleDetail(rubric.idRubrica)}
                                        >
                                            {rubric.idRubrica}
                                        </TableCell>
                                        <TableCell
                                            className={`cursor-pointer ${selectedRubricId === String(rubric.idRubrica) ? "text-blue-600" : ""}`}
                                            onClick={() => handleDetail(rubric.idRubrica)}
                                        >
                                            {rubric.nombreRubrica}
                                        </TableCell>
                                        <TableCell
                                            className={`cursor-pointer ${selectedRubricId === String(rubric.idRubrica) ? "text-blue-600" : ""}`}
                                            onClick={() => handleDetail(rubric.idRubrica)}
                                        >
                                            {rubric.nombreMateria}
                                        </TableCell>
                                        <TableCell>
                                            {getStatusBadge(rubric.estado)}
                                        </TableCell>
                                        <TableCell>
                                            <div className="flex justify-center gap-2">
                                                <Button
                                                    variant="ghost"
                                                    size="icon"
                                                    className="h-8 w-8"
                                                    onClick={() => handleEdit(rubric.idRubrica)}
                                                    disabled={rubric.estado === "INACTIVO"}
                                                >
                                                    <Pencil className="h-4 w-4" />
                                                </Button>
                                                <Button
                                                    variant="ghost"
                                                    size="icon"
                                                    className="h-8 w-8 text-orange-500 hover:text-orange-600"
                                                    onClick={() => handleOpenDeleteModal(rubric)}
                                                >
                                                    <Trash2 className="h-4 w-4" />
                                                </Button>
                                                <Button
                                                    variant="ghost"
                                                    size="icon"
                                                    className="h-8 w-8 text-indigo-500 hover:text-indigo-600"
                                                    onClick={() => handleOpenDuplicateModal(rubric)}
                                                    disabled={rubric.estado === "INACTIVO"}
                                                >
                                                    <Copy className="h-4 w-4" />
                                                </Button>
                                                <Button
                                                    variant="ghost"
                                                    size="icon"
                                                    className="h-8 w-8 text-black-500 hover:text-black-600"
                                                    onClick={() => handleOpenShareModal(rubric)}
                                                    disabled={rubric.estado === "INACTIVO"}
                                                >
                                                    <Share2 className="h-4 w-4" />
                                                </Button>
                                                {notification && (
                                                    <Notification
                                                        type={notification.type}
                                                        title={notification.title}
                                                        message={notification.message}
                                                        onClose={() => setNotification(null)}
                                                    />
                                                )}
                                            </div>
                                        </TableCell>
                                    </TableRow>
                                ))
                            )}
                        </TableBody>
                    </Table>
                </div>
                <DuplicateRubricModal
                    open={showDuplicateModal}
                    onClose={() => setShowDuplicateModal(false)}
                    originalName={rubricToDuplicate?.nombreRubrica || ""}
                    onDuplicate={handleDuplicate}
                />
                <ShareRubricModal
                    open={showShareModal}
                    onClose={() => setShowShareModal(false)}
                    onShare={handleShareRubric}
                    rubricName={rubricToDuplicate?.nombreRubrica || ""}
                />
                <ConfirmDeleteModal
                    open={showDeleteModal}
                    onClose={() => setShowDeleteModal(false)}
                    onConfirmDelete={handleConfirmDelete}
                    rubricName={rubricToDelete?.nombreRubrica || ""}
                />
            </main>
        </div>
    );
}