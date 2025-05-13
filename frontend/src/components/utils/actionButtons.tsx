import React, { useRef } from "react";
import "../../assets/css/actionButtons.css";

const ActionButtons: React.FC = () => {
  const fileInputRef = useRef<HTMLInputElement>(null);

  const handleUploadClick = () => {
    if (fileInputRef.current) fileInputRef.current.click();
  };

  const handleFileChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const files = event.target.files;
    if (files && files.length > 0) {
      console.log("Archivos seleccionados:", files);
    }
  };

  return (
    <div className="action-buttons">
      <button className="action-button" onClick={handleUploadClick}>
        <span className="material-symbols-outlined">upload</span>
        Subir evidencias
      </button>
      <input
        type="file"
        ref={fileInputRef}
        style={{ display: "none" }}
        onChange={handleFileChange}
        multiple
      />

      <button className="action-button">
        <span className="material-symbols-outlined">download</span>
        Descargar evidencias
      </button>

      <button className="action-button">
        <span className="material-symbols-outlined">description</span>
        Informes
      </button>

      <button className="action-button">
        <span className="material-symbols-outlined">share</span>
        Compartir
      </button>
    </div>
  );
};

export default ActionButtons;
