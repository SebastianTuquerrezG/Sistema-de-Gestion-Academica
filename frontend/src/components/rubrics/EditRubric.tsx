import type React from "react"
import {useNavigate} from "react-router-dom";
import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"
import { useState, useEffect } from "react"

export default function EditRubric({ params }: { params: { data: any} }) {
    const navigate = useNavigate()
    const { data } = params
    const [formData, setFormData] = useState({
        id: data.id,
        nombre: data.nombre,
        criterio: "",
        puntuacion: "",
        descripcion: "",
    })
    // Cargar datos de la rúbrica seleccionada
   // Cargar datos de la rúbrica seleccionada
   useEffect(() => {
       if (data) {
           setFormData(data);
       }
   }, [data]);

    // Manejar cambios en los inputs
    const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const { id, value } = e.target
        setFormData((prev) => ({
            ...prev,
            [id]: value,
        }))
    }
    // Manejar el envío del formulario
    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault()
        // Aquí se implementa la lógica para guardar los cambios
        alert(`Rúbrica ${id} actualizada correctamente`)
        navigate("/rubricas")
    }
    // Volver a la página anterior
    const handleCancel = () => {
       navigate("/rubricas")
    }

    return (
            <Card className="w-full max-w-[450px] text-[28px]">
                <CardHeader>
                    <CardTitle>Editar Rubrica de Evaluación</CardTitle>
                    <CardDescription>Edita los datos de la rubrica en el formulario:</CardDescription>
                </CardHeader>
                <CardContent>
                    <form onSubmit={handleSubmit}>
                        <div className="grid w-full items-center gap-4">
                            <div className="flex flex-col space-y-1.5">
                                <Label htmlFor="nombre">Nombre</Label>
                                <Input id="nombre" placeholder="Nombre de la rubrica" value={formData.nombre} onChange={handleChange} />
                            </div>
                            <div className="flex flex-col space-y-1.5">
                                <Label htmlFor="criterio">Criterio</Label>
                                <Input
                                    id="criterio"
                                    placeholder="Criterio de evaluación"
                                    value={formData.criterio}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="flex flex-col space-y-1.5">
                                <Label htmlFor="puntuacion">Puntuación Máxima</Label>
                                <Input
                                    id="puntuacion"
                                    type="number"
                                    placeholder="Puntuación máxima"
                                    value={formData.puntuacion}
                                    onChange={handleChange}
                                />
                            </div>
                            <div className="flex flex-col space-y-1.5">
                                <Label htmlFor="descripcion">Descripción</Label>
                                <Input
                                    id="descripcion"
                                    placeholder="Descripción del criterio"
                                    value={formData.descripcion}
                                    onChange={handleChange}
                                />
                            </div>
                        </div>
                    </form>
                </CardContent>
                <CardFooter className="flex justify-between">
                    <Button variant="outline" onClick={handleCancel}>
                        Cancelar
                    </Button>
                    <Button onClick={handleSubmit}>Guardar</Button>
                </CardFooter>
            </Card>
    )
}

