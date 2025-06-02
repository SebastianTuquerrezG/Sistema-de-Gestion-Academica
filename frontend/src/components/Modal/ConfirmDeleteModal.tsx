import { Dialog, DialogContent, DialogHeader, DialogTitle, DialogDescription } from "@/components/ui/dialog";
import { Button } from "@/components/ui/button";
import { X } from "lucide-react";
import { ModalProps } from "@/interfaces/ModalProps";

interface ConfirmDeleteModalProps extends ModalProps {
  rubricName: string;
  onConfirmDelete: () => void;
}

const ConfirmDeleteModal: React.FC<ConfirmDeleteModalProps> = ({
  open,
  onClose,
  onConfirmDelete,
  rubricName,
}) => {
  const handleConfirmDelete = () => {
    onConfirmDelete();
    onClose();
  };

  return (
    <Dialog open={open} onOpenChange={onClose}>
      <DialogContent className="sm:max-w-md">
        <DialogHeader>
          <DialogTitle className="text-lg font-semibold text-gray-900">
            Confirmar Desvinculación
          </DialogTitle>
          <DialogDescription>
            ¿Estás seguro de que deseas desvincular la rúbrica{" "}
            <span className="font-bold">{rubricName}</span>?
          </DialogDescription>
        </DialogHeader>

        <div className="flex justify-end gap-4 mt-4">
          <Button variant="outline" onClick={onClose}>
            Cancelar
          </Button>
          <Button
            variant="destructive"
            onClick={handleConfirmDelete}
          >
            Desvincular
          </Button>
        </div>
      </DialogContent>
    </Dialog>
  );
};

export default ConfirmDeleteModal;