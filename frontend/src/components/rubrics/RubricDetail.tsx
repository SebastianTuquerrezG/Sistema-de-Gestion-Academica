"use client"

import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getRubricById } from "@/services/rubricService";
import { RubricInterface } from "@/interfaces/RubricInterface.ts";
import { useNavigate } from "react-router-dom";
import { Card, CardHeader, CardTitle, CardContent, CardFooter } from "@/components/ui/card";
import { Table, TableHeader, TableRow, TableHead, TableBody, TableCell } from "@/components/ui/table";
import { Badge } from "@/components/ui/badge";
import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { AlertTriangle } from "lucide-react";
import { Skeleton } from "@/components/ui/skeleton";

export default function RubricDetail() {
    const { id } = useParams<{ id: string }>();
    const [rubric, setRubric] = useState<RubricInterface | null>(null);
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        if (id) {
            setLoading(true);
            getRubricById(id)
                .then((data) => {
                    setRubric(data);
                    setLoading(false);
                })
                .catch((error) => {
                    console.error(error);
                    setLoading(false);
                });
        } else {
            setLoading(false);
        }
    }, [id]);

    if (loading) {
        return (
            <div className="container mx-auto p-4 space-y-6">
                <div className="flex justify-between items-center">
                    <Skeleton className="h-8 w-64" />
                </div>

                <Card>
                    <CardHeader>
                        <Skeleton className="h-6 w-3/4" />
                    </CardHeader>
                    <CardContent className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                        {[...Array(5)].map((_, i) => (
                            <div key={i} className="space-y-2">
                                <Skeleton className="h-4 w-24" />
                                <Skeleton className="h-9 w-full" />
                            </div>
                        ))}
                    </CardContent>
                </Card>

                <Card>
                    <CardHeader>
                        <Skeleton className="h-6 w-48" />
                    </CardHeader>
                    <CardContent>
                        <Table>
                            <TableHeader>
                                <TableRow>
                                    <TableHead className="w-[300px]"><Skeleton className="h-4 w-full" /></TableHead>
                                    <TableHead className="text-right"><Skeleton className="h-4 w-full" /></TableHead>
                                    <TableHead className="text-right"><Skeleton className="h-4 w-full" /></TableHead>
                                    <TableHead><Skeleton className="h-4 w-full" /></TableHead>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {[...Array(3)].map((_, i) => (
                                    <TableRow key={i}>
                                        <TableCell><Skeleton className="h-4 w-full" /></TableCell>
                                        <TableCell><Skeleton className="h-4 w-full" /></TableCell>
                                        <TableCell><Skeleton className="h-4 w-full" /></TableCell>
                                        <TableCell><Skeleton className="h-4 w-full" /></TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </CardContent>
                    <CardFooter className="flex justify-between">
                        <Skeleton className="h-4 w-32" />
                        <Skeleton className="h-4 w-24" />
                    </CardFooter>
                </Card>
            </div>
        );
    }

    if (!rubric) {
        return (
            <div className="min-h-screen bg-background flex items-center justify-center p-4">
                <Card className="min-h-[60vh] flex flex-col items-center justify-center text-primary bg-background rounded-xl shadow-sm p-8 max-w-md mx-auto">
                    <AlertTriangle className="h-16 w-16 text-destructive mb-4" />
                    <h2 className="m-0 mb-3 font-bold text-4xl text-center">Rúbrica no encontrada</h2>
                    <p className="m-0 mb-6 text-lg text-muted-foreground text-center">
                        La rúbrica que buscas no existe o no se pudo encontrar.
                    </p>
                    <Button variant="default" onClick={() => navigate(-1)}>
                        Ir atrás
                    </Button>
                </Card>
            </div>
        );
    }

    return (
        <div className="container mx-auto p-4 space-y-6">
            <div className="flex justify-between items-center">
                <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>
                    Detalles de la Rúbrica
                </h2>
            </div>

            <Card>
                <CardHeader>
                    <CardTitle className="text-xl">{rubric.nombreRubrica}</CardTitle>
                </CardHeader>
                <CardContent className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                    <div className="space-y-1">
                        <Label htmlFor="id">Identificador</Label>
                        <Input id="id" value={rubric.idRubrica} readOnly />
                    </div>
                    <div className="space-y-1">
                        <Label htmlFor="materia">Materia</Label>
                        <Input id="materia" value={rubric.nombreMateria} readOnly />
                    </div>
                    <div className="space-y-1">
                        <Label htmlFor="nota">Nota</Label>
                        <Input id="nota" value={rubric.notaRubrica} readOnly />
                    </div>
                    <div className="space-y-1">
                        <Label htmlFor="objetivo">Objetivo de estudio</Label>
                        <Input id="objetivo" value={rubric.objetivoEstudio} readOnly />
                    </div>
                    <div className="space-y-1">
                        <Label htmlFor="estado">Estado</Label>
                        <div>
                            {
                                (() => {
                                    switch (rubric.estado) {
                                        case "ACTIVO":
                                            return <Badge className="bg-green-500">Activo</Badge>;
                                        case "INACTIVO":
                                            return <Badge className="bg-gray-500">Inactivo</Badge>;
                                        default:
                                            return <Badge className="text-purple-500">Desconocido</Badge>;
                                    }
                                })()
                            }
                        </div>
                    </div>
                </CardContent>
            </Card>

            <Card>
                <CardHeader>
                    <CardTitle className="text-xl">Criterios de evaluación</CardTitle>
                </CardHeader>
                <CardContent>
                    <Table>
                        <TableHeader>
                            <TableRow>
                                <TableHead className="w-[300px]">Criterio</TableHead>
                                <TableHead className="text-right">Porcentaje</TableHead>
                                <TableHead className="text-right">Nota</TableHead>
                                <TableHead>Comentario</TableHead>
                            </TableRow>
                        </TableHeader>
                        <TableBody>
                            {rubric.criterios.map((criterio) => (
                                <TableRow key={criterio.idCriterio}>
                                    <TableCell className="font-medium">{criterio.crfDescripcion}</TableCell>
                                    <TableCell className="text-right">{criterio.crfPorcentaje}%</TableCell>
                                    <TableCell className="text-right">{criterio.crfNota}</TableCell>
                                    <TableCell>{criterio.crfComentario}</TableCell>
                                </TableRow>
                            ))}
                        </TableBody>
                    </Table>
                </CardContent>
                <CardFooter className="flex justify-between">
                    <div className="text-sm text-muted-foreground">
                        Total criterios: {rubric.criterios.length}
                    </div>
                    <div className="text-sm font-medium">
                        Nota final: {rubric.notaRubrica}
                    </div>
                </CardFooter>
            </Card>
        </div>
    );
}