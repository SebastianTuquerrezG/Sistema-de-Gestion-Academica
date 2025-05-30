"use client"

import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { Search, Copy, Eye,Filter } from "lucide-react";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router-dom";
import { RubricInterface } from "@/interfaces/RubricInterface";
import Notification from "@/components/notifications/notification";
import DuplicateRubricModal from "@/components/Modal/DuplicateRubricModal.tsx";
import {createRubric} from "@/services/rubricService.ts";
//import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar"
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from "@/components/ui/select"
import { Badge } from "@/components/ui/badge"
import { TableCell } from "@/components/ui/table";
import {RubricInterfacePeticion} from "@/interfaces/RubricInterfacePeticion.ts";
//import { z } from "zod"
//import { useForm } from "react-hook-form";
//import { zodResolver } from "@hookform/resolvers/zod"


/*const ShareSchema = z.object({
    email: z.string().email("Correo no valido"),
})*/

type NotificationType = {
    type: "error" | "info" | "success";
    title: string;
    message: string;
}

export default function RepositorioRubricas(){
    const [notification, setNotification] = useState<NotificationType | null>(null);
    const [searchTerm, setSearchTerm] = useState("");
    const [rubrics, setRubrics] = useState<RubricInterface[]>([])
    const navigate = useNavigate(); 
    const [selectedRubricId, setSelectedRubricId] = useState<number | null>(null);
    const[showDuplicateModal, setShowDuplicateModal] = useState(false);
    const [rubricToDuplicate, setRubricToDuplicate] = useState<RubricInterface | null>(null);
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

    const filteredRubrics = rubrics.filter((rubric) =>
        rubric.nombreRubrica.toLowerCase().includes(searchTerm.toLowerCase()) ||
        rubric.idRubrica
    );
    //Funcion para mostrar el detalle de la rúbrica
    const handleDetail  = (id: number) => {
        setSelectedRubricId(id);
        navigate(`rubricas/detalle/${id}`);
    };

    // Function para abrir el modal de duplicar
    const handleOpenDuplicateModal = (rubric: RubricInterface) => {
        setRubricToDuplicate(rubric);
        setShowDuplicateModal(true);
    };

    // Function para duplicar la rúbrica
    const handleDuplicate = async ( data: { newName: string; shareWithSamePeople: boolean; copyComments: boolean; resolvedComments: boolean }) => {
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

    /*const form = useForm<z.infer<typeof ShareSchema>>({
        resolver: zodResolver(ShareSchema),
        defaultValues: {
            email: "",
        },
    });*/


    return (
        <div>
            <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>
                Repositorio de Rúbricas
            </h2>
            <main className="max-w-7xl mx-auto px-4 py-8">
                <div className="mb-6 flex flex-col sm:flex-row gap-4 justify-between items-start sm:items-center">
                    <div className="relative flex-1 max-w-md">
                        <Input type="text"  placeholder="Buscar en repositorio..." value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)} style={{ width: "100%" }}
                            className="pl-10 w-full"
                        />
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                    </div>
                    <div className="flex gap-2">
                        <Select defaultValue="todas">
                            <SelectTrigger className="w-[180px]">
                                <SelectValue placeholder="Materia" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value="todas">Todas las materias</SelectItem>
                                <SelectItem value="v">Ingeniería de Software III</SelectItem>
                                <SelectItem value="v">Diseño de Experiencia de Usuario</SelectItem>
                            </SelectContent>
                        </Select>
                        <Select defaultValue="todas">
                            <SelectTrigger className="w-[180px]">
                                <SelectValue placeholder="Estado" />
                            </SelectTrigger>
                            <SelectContent>
                                <SelectItem value="todas">Todos los estados</SelectItem>
                                <SelectItem value="activo">Activo</SelectItem>
                                <SelectItem value="inactivo">Inactivo</SelectItem>
                            </SelectContent>
                        </Select>
                        <Button variant="ghost" size="icon">
                            <Filter className="h-4 w-4" />
                        </Button>
                    </div>
                </div>
                <div>
                    <table className="w-full">
                        <thead>
                        <tr className="title5 bg-[#000066] text-white ">
                            <th className="py-3 text-left">Identificador</th>
                            <th className="px-6 py-3 text-left">Nombre Rubrica</th>
                            <th className="px-6 py-3 text-left">Materia</th>
                            <th className="px-6 py-3 text-left">Estado</th>
                            <th className="px-6 py-3 text-center">Acciones</th>
                        </tr>
                        </thead>
                        <tbody>
                        {filteredRubrics.map((rubric, index) => (
                            <tr key={rubric.idRubrica} className={index % 2 === 0 ? "bg-gray-50" : "bg-white"}>
                                <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`} onClick={() => handleDetail(rubric.idRubrica)}                                >
                                    {rubric.idRubrica}
                                </td>
                                <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`} onClick={() => handleDetail(rubric.idRubrica)}>
                                    {rubric.nombreRubrica}
                                </td>
                                <td className={`px-6 py-4 cursor-pointer ${selectedRubricId === rubric.idRubrica ? "text-blue-600" : ""}`} onClick={() => handleDetail(rubric.idRubrica)}>
                                    {rubric.materia}
                                </td>
                                <td>
                                    <TableCell>{getStatusBadge(rubric.estado)}</TableCell>
                                </td>

                                <td className="px-6 py-4">
                                    <div className="flex justify-center gap-2">
                                        <Button variant="ghost" size="icon" className="h-8 w-8 text-indigo-500 hover:text-indigo-600"  onClick={() => handleDetail(rubric.idRubrica)}>
                                            <Eye className="h-4 w-4" />
                                        </Button>
                                        <Button variant="ghost" size="icon" className="h-8 w-8 text-orange-500 hover:text-black-600" onClick={() => handleOpenDuplicateModal(rubric)}>
                                            <Copy className="h-4 w-4" />
                                        </Button>
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
            </main>
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