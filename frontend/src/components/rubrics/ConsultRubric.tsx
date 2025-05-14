"use client"

import { Button } from "@/components/ui/button";
import { useEffect, useState } from "react";
import { Search, Plus, Pencil, Trash2, Share2 } from "lucide-react";
import { Input } from "@/components/ui/input";
import { useNavigate } from "react-router-dom";
//import { getAllRubrics } from "@/services/rubricService";
import Notification from "@/components/notifications/notification";
import { RubricInterface } from "@/interfaces/RubricInterface";
import { deleteRubric } from "@/services/rubricService";

import {
    Dialog,
    DialogContent,
    DialogDescription,
    DialogHeader,
    DialogTitle,
    DialogTrigger,
} from "@/components/ui/dialog"

import { z } from "zod"
import { useForm } from "react-hook-form"
import { zodResolver } from "@hookform/resolvers/zod"
import {
    Form,
    FormControl,
    FormField,
    FormItem,
    FormLabel,
    FormMessage,
} from "@/components/ui/form"

const ShareSchema = z.object({
    email: z.string().email("Correo no válido"),
})

type NotificationType = {
    type: "error" | "info" | "success";
    title: string;
    message: string;
};

export default function ConsultarRubrica() {
    const [searchTerm, setSearchTerm] = useState("");
    const [rubrics, setRubrics] = useState<RubricInterface[]>([]);
    const navigate = useNavigate(); // Hook to navigate between pages
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
                const activeRubrics = data.filter((rubric: RubricInterface) => rubric.estado !== "INACTIVO");
                setRubrics(activeRubrics);
            })
            .catch((error) => console.error(error));
    }, []);

    /* useEffect(() => {
        getAllRubrics()
            .then((data) => {
                const activeRubrics = data.filter((rubric: RubricInterface) => rubric.estado !== "INACTIVO");
                setRubrics(activeRubrics);
            })
            .catch((error) => console.error(error));
    }, []); */

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
        } else {
            alert("Error deleting rubric");
        }
    }

    const form = useForm<z.infer<typeof ShareSchema>>({
        resolver: zodResolver(ShareSchema),
        defaultValues: {
            email: "",
        },
    });

    const [notification, setNotification] = useState<NotificationType | null>(null);

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
                            className="pl-10 w-full"
                        />
                        <Search className="absolute left-3 top-1/2 -translate-y-1/2 h-4 w-4 text-gray-400" />
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
                                        {rubric.materia}
                                    </td>
                                    <td className="px-6 py-4">
                                        <div className="flex justify-center gap-2">
                                            <Button variant="ghost" size="icon" className="h-8 w-8" onClick={() => handleEdit(rubric.idRubrica)}>
                                                <Pencil className="h-4 w-4" />
                                            </Button>
                                            <Button variant="ghost" size="icon" className="h-8 w-8 text-orange-500 hover:text-orange-600" onClick={() => handleDelete(rubric.idRubrica)}>
                                                <Trash2 className="h-4 w-4" />
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
                                                            Selecciona una opción para compartir esta rúbrica.
                                                        </DialogDescription>
                                                    </DialogHeader>
                                                    <div className="space-y-6">
                                                        {/* Compartir con una persona específica */}
                                                        <div>
                                                            <h3 className="text-sm font-medium text-gray-700">Compartir con una persona</h3>

                                                            <Form {...form}>
                                                                <form
                                                                    onSubmit={form.handleSubmit((data) => {
                                                                        setNotification({
                                                                            type: "success",
                                                                            title: "Compartido",
                                                                            message: `Rúbrica compartida con ${data.email}`,
                                                                        });
                                                                        form.reset();
                                                                    })}
                                                                    className="space-y-4 mt-2"
                                                                >
                                                                    <FormField
                                                                        control={form.control}
                                                                        name="email"
                                                                        render={({ field }) => (
                                                                            <FormItem>
                                                                                <FormLabel>Correo</FormLabel>
                                                                                <FormControl>
                                                                                    <Input placeholder="usuario@unicauca.com" {...field} />
                                                                                </FormControl>
                                                                                <FormMessage />
                                                                            </FormItem>
                                                                        )}
                                                                    />
                                                                    <div className="flex gap-2">
                                                                        <Button type="button" variant="outline" onClick={() => form.reset()}>
                                                                            Cancelar
                                                                        </Button>
                                                                        <Button type="submit">Compartir</Button>
                                                                    </div>
                                                                </form>
                                                            </Form>
                                                        </div>

                                                        {/* Compartir públicamente */}
                                                        <div>
                                                            <h3 className="text-sm font-medium text-gray-700">Hacer público</h3>
                                                            <p className="text-sm text-gray-500">
                                                                Esta opción hará que la rúbrica sea accesible para todos.
                                                            </p>
                                                            <div className="mt-4 flex gap-2">
                                                                <Button
                                                                    variant="outline"
                                                                    onClick={() =>
                                                                        setNotification({
                                                                            type: "info",
                                                                            title: "Cancelado",
                                                                            message: "Acción cancelada",
                                                                        })
                                                                    }
                                                                >
                                                                    Cancelar
                                                                </Button>
                                                                <Button
                                                                    onClick={() =>
                                                                        setNotification({
                                                                            type: "success",
                                                                            title: "Hecho Público",
                                                                            message: "Rúbrica hecha pública exitosamente",
                                                                        })
                                                                    }
                                                                >
                                                                    Hacer Público
                                                                </Button>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </DialogContent>
                                            </Dialog>
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
            </main>
        </div>
    );
}