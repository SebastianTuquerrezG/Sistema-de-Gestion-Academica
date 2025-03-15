import { Button } from "@/components/ui/button"
import { Card, CardContent, CardDescription, CardFooter, CardHeader, CardTitle } from "@/components/ui/card"
import { Input } from "@/components/ui/input"
import { Label } from "@/components/ui/label"

export default function EditRubric() {
    return (
        <Card className="w-full max-w-[450px] text-[28px]">
            <CardHeader>
                <CardTitle>Editar Rubrica de Evaluación</CardTitle>
                <CardDescription>Edita los datos de la rubrica en el formulario:</CardDescription>
            </CardHeader>
            <CardContent>
                <form>
                    <div className="grid w-full items-center gap-4">
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="nombre">Nombre</Label>
                            <Input id="nombre" placeholder="Nombre de la rubrica" />
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="criterio">Criterio</Label>
                            <Input id="criterio" placeholder="Criterio de evaluación" />
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="puntuacion">Puntuación Máxima</Label>
                            <Input id="puntuacion" type="number" placeholder="Puntuación máxima" />
                        </div>
                        <div className="flex flex-col space-y-1.5">
                            <Label htmlFor="descripcion">Descripción</Label>
                            <Input id="descripcion" placeholder="Descripción del criterio" />
                        </div>
                    </div>
                </form>
            </CardContent>
            <CardFooter className="flex justify-between">
                <Button variant="outline">Cancelar</Button>
                <Button>Guardar</Button>
            </CardFooter>
        </Card>
    );
};