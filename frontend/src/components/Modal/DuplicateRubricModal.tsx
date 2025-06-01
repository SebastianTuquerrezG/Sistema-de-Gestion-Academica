import { useEffect, useState, useRef } from "react";
import { Dialog, DialogContent, DialogTitle, DialogDescription } from "@/components/ui/dialog.tsx";
import { Input } from "@/components/ui/input.tsx";
import { Button } from "@/components/ui/button.tsx";
import { Copy } from "lucide-react";
import { ModalProps } from "@/interfaces/ModalProps.ts";

interface Props extends ModalProps {
    originalName: string;
    onDuplicate: (data: { newName: string }) => void;
}

export default function DuplicateRubricModal({
                                                 open,
                                                 onClose,
                                                 originalName,
                                                 onDuplicate,
                                             }: Props) {
    const [newName, setNewName] = useState('');
    const inputRef = useRef<HTMLInputElement>(null);

    useEffect(() => {
        if (open) {
            const defaultName = `Copia de ${originalName}`;
            setNewName(defaultName);
            setTimeout(() => {
                inputRef.current?.focus();
                inputRef.current?.setSelectionRange(0, defaultName.length);
            }, 50); // da tiempo a que el modal renderice antes de seleccionar
        }
    }, [open, originalName]);

    const handleDuplicate = () => {
        if (newName.trim() !== '') {
            onDuplicate({ newName });
        } else {
            console.error("El nombre de la nueva rúbrica no puede estar vacío.");
        }
    };

    return (
        <Dialog open={open} onOpenChange={onClose}>
            <DialogContent className="w-full max-w-md p-6 space-y-4">
                <div className="flex items-center gap-2">
                    <Copy className="h-5 w-5 text-indigo-600" />
                    <DialogTitle className="text-lg font-semibold">
                        Duplicar Rúbrica
                    </DialogTitle>
                </div>
                <DialogDescription className="text-sm text-gray-600">
                    Estás a punto de crear una copia de <span className="font-medium text-gray-800">"{originalName}"</span>.
                    Puedes modificar el nombre si lo deseas:
                </DialogDescription>

                <div>
                    <label className="block text-sm font-medium mb-1">Nuevo nombre</label>
                    <Input
                        ref={inputRef}
                        value={newName}
                        onChange={(e) => setNewName(e.target.value)}
                        placeholder="Nombre de la nueva rúbrica"
                    />
                </div>

                <div className="flex justify-end gap-3 pt-4">
                    <Button variant="outline" onClick={onClose}>
                        Cancelar
                    </Button>
                    <Button onClick={handleDuplicate} disabled={newName.trim() === ''}>
                        Crear copia
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
}
