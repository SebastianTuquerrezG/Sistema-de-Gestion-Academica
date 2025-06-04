import { Button } from "@/components/ui/button";
import { Card } from "@/components/ui/card";
import { Lock } from "lucide-react";

const AccessDenied = () => {
    return (
        <div className="min-h-screen bg-background flex items-center justify-center p-4">
            <Card className="min-h-[60vh] flex flex-col items-center justify-center text-primary bg-background rounded-xl shadow-sm p-8 max-w-md mx-auto">
                <Lock className="h-16 w-16 text-destructive mb-4" />
                <h2 className="m-0 mb-3 font-bold text-4xl">Acceso Denegado</h2>
                <p className="m-0 mb-6 text-lg text-muted-foreground">
                    No tienes permisos para acceder a esta sección.
                </p>
                <Button asChild variant="default">
                    <a href="/" className="no-underline">
                        Ir al inicio
                    </a>
                </Button>
            </Card>
        </div>
    );
};

export default AccessDenied;