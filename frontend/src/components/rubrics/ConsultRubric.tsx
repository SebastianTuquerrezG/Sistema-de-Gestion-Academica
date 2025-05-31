"use client"

import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { Search, Plus, Pencil, Trash2, Share2 } from "lucide-react";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router-dom";
import { Copy } from "lucide-react";

import { getAllRubrics } from "@/services/rubricService";
import Notification from "@/components/notifications/notification";
import { RubricInterface } from "@/interfaces/RubricInterface";
import { deleteRubric } from "@/services/rubricService";
import { createRubric } from "@/services/rubricService";
import DuplicateRubricModal from "@/components/Modal/DuplicateRubricModal.tsx";
import ShareRubricModal from "@/components/Modal/ShareRubricModal.tsx";
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select.tsx";
import { TableCell } from "@/components/ui/table.tsx";
import { Badge } from "@/components/ui/badge.tsx";
import ConfirmDeleteModal from "@/components/Modal/ConfirmDeleteModal";
//import rubricaInfo from "@/views/Evaluaciones/rubricaInfo.tsx";

type NotificationType = {
    type: "error" | "info" | "success";
    title: string;
    message: string;
};

export default function ConsultarRubrica() {
    const [searchTerm, setSearchTerm] = useState("");
    const [rubrics, setRubrics] = useState<RubricInterface[]>([]);
    const navigate = useNavigate(); // Hook para navegación entre páginas
    const [selectedRubricId, setSelectedRubricId] = useState<string | null>(null);
    const [showDuplicateModal, setShowDuplicateModal] = useState(false);
    const [rubricToDuplicate, setRubricToDuplicate] = useState<RubricInterface | null>(null);
    const [showShareModal, setShowShareModal] = useState(false);
    const [showDeleteModal, setShowDeleteModal] = useState(false);
    const [rubricToDelete, setRubricToDelete] = useState<RubricInterface | null>(null);
    /*
    useEffect(() => {
        fetch('/rubricas.json')
            .then((response) => {
                if (!response.ok) {
                    throw new Error('Error al cargar el archivo JSON');
                }
                return response.json();
            })
            .then((data) => {
                const activeRubrics = data.filter((rubric: RubricInterface) => rubric.estado !== "INACTIVO");
                setRubrics(activeRubrics);
            })
            .catch((error) => console.error(error));
    }, []);*/

    useEffect(() => {
        getAllRubrics()
            .then(data => {
                setRubrics(Array.isArray(data) ? data : []);
            })
            .catch((error) => {
                console.log(error.message);
            });
    }, [navigate]);

    const filteredRubrics = rubrics.filter(
        (rubric) =>
            rubric.nombreRubrica.toLowerCase().includes(searchTerm.toLowerCase()) ||
            rubric.idRubrica.toLowerCase().includes(searchTerm.toLowerCase())
        /*       rubric.materia.toLowerCase().includes(searchTerm.toLowerCase())*/
    );

    // Funcion para navegar a la pagina de editar
    const handleEdit = (id: string) => {
        navigate(`/rubricas/editar/${id}`);
    };
    // Function para navegar a la pagina de crear
    const handleAdd = () => {
        navigate(`/rubricas/crear`);
    };

    // Función para navegar a RubricDetail
    const handleDetail = (id: string) => {
        setSelectedRubricId(id);
        navigate(`/rubricas/detalle/${id}`);
    };

    // Function to delete a rubric
    const handleDelete = async (id: string) => {
        const success = await deleteRubric(id);
        if (success) {
            setRubrics(rubrics.filter(rubric => rubric.idRubrica !== id));
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
            })
        }
    };

    // Function to open the duplicate modal
    const handleOpenDuplicateModal = (rubric: RubricInterface) => {
        setRubricToDuplicate(rubric);
        setShowDuplicateModal(true);
    };
    const handleOpenShareModal = (rubric: RubricInterface) => {
        setRubricToDuplicate(rubric);
        setShowShareModal(true);
    }

    // Function to handle rubric duplication
    const handleDuplicate = async (data: { newName: string; shareWithSamePeople: boolean; copyComments: boolean; resolvedComments: boolean }) => {
        if (!rubricToDuplicate) return;

        const duplicatedRubric: RubricInterface = {
            idRubrica: '', // id generado por el backend
            nombreRubrica: `${data.newName}`,
            materia: rubricToDuplicate.materia,
            nombreMateria: rubricToDuplicate.nombreMateria,
            notaRubrica: rubricToDuplicate.notaRubrica,
            objetivoEstudio: rubricToDuplicate.objetivoEstudio,
            estado: "ACTIVO",
            criterios: rubricToDuplicate.criterios,
            raId: rubricToDuplicate.raId,
        };

        const response = await createRubric(duplicatedRubric);
        if (response) {
            setRubrics((prev) => [...prev, duplicatedRubric]);
        } else {
            alert("Error duplicating rubric");
        }
        setShowDuplicateModal(false);
    };
    // Function to handle rubric sharing
    const handleShareRubric = async (data: {
        email: string;
        permission: string;
        message: string;
        notifyUsers: boolean;
        createLink: boolean;
        linkPermission: string
    }) => {
        if (!rubricToDuplicate) return;
        setShowShareModal(false);
    };

    const [notification, setNotification] = useState<NotificationType | null>(null);

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
            <main className="max-w-7xl mx-auto px-4 py-8">
                <div className="mb-6 flex flex-col sm:flex-row gap-4 justify-between items-start sm:items-center">
                    <div className="relative flex-1 max-w-md">
                        <Input
                            type="text"
                            placeholder="Ingrese Rubrica a buscar..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                        />
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                    </div>
                    <div className="flex gap-2">
                        <Select>
                            <SelectTrigger className="w-[180px]">
                                <SelectValue placeholder="Filtrar por materia" />
                            </SelectTrigger>
                            <SelectContent>
                                {rubrics.map((rubric) => (
                                    <SelectItem key={rubric.idRubrica} value={rubric.nombreRubrica}>
                                        {rubric.nombreMateria}
                                    </SelectItem>
                                ))}
                            </SelectContent>
                        </Select>
                        <Select>
                            <SelectTrigger className="w-[180px]">
                                <SelectValue placeholder="Filtrar por estado" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value="ACTIVO">Activo</SelectItem>
                                <SelectItem value="INACTIVO">Inactivo</SelectItem>
                            </SelectContent>
                        </Select>
                    </div>
                    <div className="flex gap-2">
                        <Button className="outline" onClick={() => handleAdd()}>
                            <Plus className="h-4 w-4 mr-2" />
                            Añadir Rubrica
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
                                <th className="px-6 py-3 text-left">Estado</th>
                                <th className="px-6 py-3 text-center">Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            {filteredRubrics.map((rubric, index) => (
                                <tr key={rubric.idRubrica} className={index % 2 === 0 ? "bg-gray-50" : "bg-white"}>
                                    <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`}
                                        onClick={() => handleDetail(rubric.idRubrica)}>
                                        {rubric.idRubrica}
                                    </td>
                                    <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`}
                                        onClick={() => handleDetail(rubric.idRubrica)}>
                                        {rubric.nombreRubrica}
                                    </td>
                                    <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`}
                                        onClick={() => handleDetail(rubric.idRubrica)}>
                                        {rubric.nombreMateria}
                                    </td>
                                    <TableCell>{getStatusBadge(rubric.estado)}</TableCell>
                                    <td className="px-6 py-4">
                                        <div className="flex justify-center gap-2">
                                            <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => handleEdit(rubric.idRubrica)}>
                                                <Pencil className="h-4 w-4" />
                                            </Button>
                                            <Button variant="ghost" size="icon" className="h-8 w-8 text-orange-500 hover:text-orange-600" onClick={() => handleOpenDeleteModal(rubric)}>
                                                <Trash2 className="h-4 w-4" />
                                            </Button>
                                            <Button variant="ghost" size="icon" className="h-8 w-8 text-indigo-500 hover:text-indigo-600" onClick={() => handleOpenDuplicateModal(rubric)}>
                                                <Copy className="h-4 w-4" />
                                            </Button>

                                            <Button variant="ghost" size="icon" className="h-8 w-8 text-black-500 hover:text-black-600" onClick={() => handleOpenShareModal(rubric)}>
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
                                    </td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                </div>
                <DuplicateRubricModal
                    open={showDuplicateModal}
                    onClose={() => setShowDuplicateModal(false)}
                    originalName={rubricToDuplicate?.nombreRubrica || ""}
                    onDuplicate={(data) => handleDuplicate(data)}
                />
                <ShareRubricModal
                    open={showShareModal}
                    onClose={() => setShowShareModal(false)}
                    onShare={(data) => handleShareRubric(data)}
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