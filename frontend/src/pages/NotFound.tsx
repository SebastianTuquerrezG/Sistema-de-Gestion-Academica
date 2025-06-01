import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { AlertTriangle } from "lucide-react";
import { useNavigate } from "react-router-dom";

const NotFound = () => {
    const navigate = useNavigate();
    return (
        <div className="min-h-screen bg-background flex items-center justify-center p-4">
            <Card className="min-h-[60vh] flex flex-col items-center justify-center text-primary bg-background rounded-xl shadow-sm p-8 max-w-md mx-auto">
                <AlertTriangle className="h-16 w-16 text-destructive mb-4" />
                <h2 className="m-0 mb-3 font-bold text-4xl text-center">No Encontrado</h2>
                <p className="m-0 mb-6 text-lg text-muted-foreground text-center">
                    La sección que buscas no existe o no se pudo encontrar.
                </p>
                <Button variant="default" onClick={() => navigate(-1)}>
                    Ir atrás
                </Button>
            </Card>
        </div>
    );
};
export default NotFound;