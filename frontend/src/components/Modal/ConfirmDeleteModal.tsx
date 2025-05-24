import React from "react";
import { Button } from "@/components/ui/button.tsx";
import { ModalProps } from "@/interfaces/ModalProps.ts";
import { X } from "lucide-react";

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
  if (!open) return null;

  const handleConfirmDelete = () => {
    onConfirmDelete();
    onClose();
  };

  return (
      <div className="fixed inset-0 bg-white/30 backdrop-blur-sm flex items-center justify-center z-50">
      <div className="bg-white rounded-lg shadow-lg p-8 w-full max-w-md relative">
          {/* Botón de cerrar */}
          <button
              onClick={onClose}
              className="absolute top-4 right-4 text-gray-400 hover:text-black"
          >
            <X size={20} />
          </button>

          {/* Título */}
          <h2 className="text-lg font-semibold text-gray-900 mb-2">Confirmar eliminación</h2>

          {/* Subtítulo */}
          <p className="text-sm text-gray-700 mb-6">
            ¿Estás seguro de que deseas eliminar la rúbrica <strong>{rubricName}</strong>?
          </p>

          {/* Botones */}
          <div className="flex justify-end gap-4">
            <Button variant="outline" onClick={onClose}>
              Cancelar
            </Button>
            <Button className="bg-red-600 text-white hover:bg-red-700" onClick={handleConfirmDelete}>
              Eliminar
            </Button>
          </div>
        </div>
      </div>
  );
};

export default ConfirmDeleteModal;
