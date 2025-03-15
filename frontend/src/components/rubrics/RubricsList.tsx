import { Button } from "@/components/ui/button";
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";

const rubricsMock = [
    {
        id: 1,
        nombre: "Rubrica 1",
        criterio: "Criterio 1",
        puntuacion: 10,
        descripcion: "Descripción del criterio 1"
    },
    {
        id: 2,
        nombre: "Rubrica 2",
        criterio: "Criterio 2",
        puntuacion: 8,
        descripcion: "Descripción del criterio 2"
    },
    {
        id: 3,
        nombre: "Rubrica 3",
        criterio: "Criterio 3",
        puntuacion: 9,
        descripcion: "Descripción del criterio 3"
    },
    {
        id: 4,
        nombre: "Rubrica 4",
        criterio: "Criterio 4",
        puntuacion: 7,
        descripcion: "Descripción del criterio 4"
    },
    {
        id: 5,
        nombre: "Rubrica 5",
        criterio: "Criterio 5",
        puntuacion: 6,
        descripcion: "Descripción del criterio 5"
    },
    {
        id: 6,
        nombre: "Rubrica 6",
        criterio: "Criterio 6",
        puntuacion: 5,
        descripcion: "Descripción del criterio 6"
    },
    {
        id: 7,
        nombre: "Rubrica 7",
        criterio: "Criterio 7",
        puntuacion: 8,
        descripcion: "Descripción del criterio 7"
    },
    {
        id: 8,
        nombre: "Rubrica 8",
        criterio: "Criterio 8",
        puntuacion: 9,
        descripcion: "Descripción del criterio 8"
    },
    {
        id: 9,
        nombre: "Rubrica 9",
        criterio: "Criterio 9",
        puntuacion: 10,
        descripcion: "Descripción del criterio 9"
    },
    {
        id: 10,
        nombre: "Rubrica 10",
        criterio: "Criterio 10",
        puntuacion: 7,
        descripcion: "Descripción del criterio 10"
    }
];

export default function RubricsList() {
    return (
        <div className="w-full max-w-[800px] mx-auto">
            <Card className="mb-4">
                <CardHeader>
                    <CardTitle>Lista de Rúbricas de Evaluación</CardTitle>
                    <CardDescription>A continuación se muestra una lista de rúbricas de evaluación:</CardDescription>
                </CardHeader>
                <CardContent>
                    {rubricsMock.map((rubric) => (
                        <div key={rubric.id} className="mb-4 p-4 border rounded">
                            <h2 className="text-xl font-bold">{rubric.nombre}</h2>
                            <p><strong>Criterio:</strong> {rubric.criterio}</p>
                            <p><strong>Puntuación Máxima:</strong> {rubric.puntuacion}</p>
                            <p><strong>Descripción:</strong> {rubric.descripcion}</p>
                        </div>
                    ))}
                </CardContent>
                <CardFooter className="flex justify-end">
                    <Button variant="outline">Agregar Nueva Rúbrica</Button>
                </CardFooter>
            </Card>
        </div>
    );
};