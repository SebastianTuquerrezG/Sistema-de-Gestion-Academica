import {useEffect, useState} from "react";
import { Dialog, DialogContent, DialogTitle, DialogDescription } from "@/components/ui/dialog.tsx";
import { Input } from "@/components/ui/input.tsx";
import { Checkbox } from "@/components/ui/checkbox.tsx";
import { Button } from "@/components/ui/button.tsx";
import {  Copy } from "lucide-react"
import { ModalProps } from "@/interfaces/ModalProps.ts";

interface Props extends ModalProps {

    originalName: string;
    onDuplicate: (data: {
        newName: string;
        shareWithSamePeople: boolean;
        copyComments: boolean;
        resolvedComments: boolean;
    }) => void;
}


export default function DuplicateRubricModal({
    open,
    onClose,
    originalName,
    onDuplicate,
}: Props) {
    const [newName, setNewName] = useState('');
    const [shareWithSamePeople, setShareWithSamePeople] = useState(false);
    const [copyComments, setCopyComments] = useState(false);
    const [resolvedComments, setResolvedComments] = useState(false);


    const handleDuplicate = () => {
        onDuplicate({ newName, shareWithSamePeople, copyComments, resolvedComments });
        onClose();
    };
    //Resetea el nombre de la rubrica al abrir el modal
    useEffect(() => {
        if(open) {
            setNewName(originalName); //coloca el nombre original en el input al abrir el modal
        }
    }, [open, originalName]);

    return (
        <Dialog open={open} onOpenChange={onClose}>
            <DialogContent className="w-full max-w-md p-6">
                <DialogTitle  className="flex items-center gap-2 text-lg">
                    <Copy className="h-5 w-5" />
                    Duplicar Rúbrica
                </DialogTitle>
                <DialogDescription>Crear una copia de "{originalName}":</DialogDescription>
                <label className="block text-sm font-medium mt-4">Nombre</label>
                <Input value={newName}  onChange={e => setNewName(e.target.value)}  style={{ width: '100%' }}
                />
                <div className="mt-4 space-y-2">
                    <div className="flex items-center gap-2">
                        <Checkbox
                            checked={shareWithSamePeople}
                            onCheckedChange={() => setShareWithSamePeople(!shareWithSamePeople)}
                        />
                        <span>Compartir con las mismas personas</span>
                    </div>
                    <div className="flex items-center gap-2">
                        <Checkbox
                            checked={copyComments}
                            onCheckedChange={() => setCopyComments(!copyComments)}
                        />
                        <span>Copiar los comentarios y sugerencias</span>
                    </div>
                    <div className="flex items-center gap-2 pl-6">
                        <Checkbox
                            checked={resolvedComments}
                            onCheckedChange={() => setResolvedComments(!resolvedComments)}
                            disabled={!copyComments}
                        />
                        <span>Incluir sugerencias y comentarios resueltos</span>
                    </div>
                </div>

                <div className="flex justify-end mt-6 gap-2">
                    <Button variant="outline" onClick={onClose}>
                        Cancelar
                    </Button>
                    <Button onClick={handleDuplicate}>
                        Hacer una copia
                    </Button>
                </div>
            </DialogContent>
        </Dialog>
    );
}