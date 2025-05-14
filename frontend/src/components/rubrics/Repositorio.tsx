"use client"

import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { Search, Copy, Share2 } from "lucide-react";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router-dom";
import { RubricInterface } from "@/interfaces/RubricInterface";
import Notification from "@/components/notifications/notification";

import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog"

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
    const [searchTerm, setSearchTerm] = useState("");
    const [rubrics, setRubrics] = useState<RubricInterface[]>([]);
    const navigate = useNavigate();
    const [selectedRubricId, setSelectedRubricId] = useState<string | null>(null);

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

    const filteredRubrics = rubrics.filter((rubric) =>
        rubric.nombreRubrica.toLowerCase().includes(searchTerm.toLowerCase()) ||
        rubric.idRubrica.toLowerCase().includes(searchTerm.toLowerCase()),
    );

    const handleDetail  = (id: string) => {
        setSelectedRubricId(id);
        navigate(`rubricas/detalle/${id}`);
    };



    /*const form = useForm<z.infer<typeof ShareSchema>>({
        resolver: zodResolver(ShareSchema),
        defaultValues: {
            email: "",
        },
    });*/

    const [notification, setNotification] = useState<NotificationType | null>(null);

    return (
        <div>
            <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>
                Repositorio de Rúbricas
            </h2>
            <main className="max-w-7xl mx-auto px-4 py-8">
                <div className="mb-6 flex flex-col sm:flex-row gap-4 justify-between items-start sm:items-center">
                    <div className="relative flex-1 max-w-md">
                        <Input
                            type="text"
                            placeholder="Buscar en repositorio..."
                            value={searchTerm}
                            onChange={(e) => setSearchTerm(e.target.value)}
                            className="pl-10 w-full"
                        />
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
                    </div>
                </div>
                <div>
                    <table className="w-full">
                        <thead>
                        <tr className="title5 bg-[#000066] text-white ">
                            <th className="py-3 text-left">Identificador</th>
                            <th className="px-6 py-3 text-left">Nombre Rubrica</th>
                            <th className="px-6 py-3 text-left">Materia</th>
                            <th className="px-6 py-3 text-left">Creador</th>
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

                                <td className="px-6 py-4">
                                    {"el sistema más perron"}
                                </td>

                                <td className="px-6 py-4">
                                    <div className="flex justify-center gap-2">
                                        <Button
                                            variant="ghost"
                                            size="icon"
                                            className="h-8 w-8"

                                            >
                                            <Copy className="h-4 w-4" />
                                        </Button>

                                        <Dialog>
                                            <DialogTrigger>
                                                <Button variant="ghost" size="icon" className="h-8 w-8">
                                                    <Share2 className="h-4 w-4" />
                                                </Button>
                                            </DialogTrigger>
                                            <DialogContent>
                                                <DialogHeader>
                                                    <DialogTitle>Compartir Rubrica</DialogTitle>
                                                    <DialogDescription>
                                                        Esta rúbrica es parte del repositorio institucional.
                                                    </DialogDescription>
                                                </DialogHeader>
                                            </DialogContent>
                                        </Dialog>
                                    </div>
                                </td>
                            </tr>
                        ))}
                        </tbody>
                    </table>
                </div>
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