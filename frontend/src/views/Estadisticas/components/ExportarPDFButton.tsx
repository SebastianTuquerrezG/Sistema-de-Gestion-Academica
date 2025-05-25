import React, { useState } from "react";
import html2canvas from "html2canvas-pro";
import jsPDF from "jspdf";

interface ExportarPDFButtonProps {
  targetRef: React.RefObject<HTMLDivElement | null>;
  onBeforePrint?: () => void;
  onAfterPrint?: () => void;
}

const LoadingOverlay = () => (
  <div 
    style={{
      position: 'fixed',
      top: 0,
      left: 0,
      right: 0,
      bottom: 0,
      backgroundColor: 'rgba(255, 255, 255, 0.8)',
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      justifyContent: 'center',
      zIndex: 9999
    }}
  >
    <div 
      style={{
        width: '50px',
        height: '50px',
        border: '5px solid #f3f3f3',
        borderTop: '5px solid #0d47a1',
        borderRadius: '50%',
        animation: 'spin 1s linear infinite',
        marginBottom: '20px'
      }}
    />
    <style>
      {`
        @keyframes spin {
          0% { transform: rotate(0deg); }
          100% { transform: rotate(360deg); }
        }
      `}
    </style>
    <div style={{ 
      color: '#0d47a1', 
      fontSize: '18px',
      fontWeight: 600,
      textAlign: 'center'
    }}>
      Generando PDF
      <br />
      <span style={{ fontSize: '14px', fontWeight: 400, color: '#666' }}>
        Por favor, espere un momento...
      </span>
    </div>
  </div>
);

const ExportarPDFButton: React.FC<ExportarPDFButtonProps> = ({ 
  targetRef,
  onBeforePrint,
  onAfterPrint
}) => {
  const [isGenerating, setIsGenerating] = useState(false);

  const handleExportPDF = async () => {
    if (!targetRef.current) return;

    try {
      setIsGenerating(true);
      onBeforePrint?.();

      // Esperar a que el DOM se actualice
      await new Promise(resolve => setTimeout(resolve, 1000));

      const element = targetRef.current;

      // Crear un contenedor temporal
      const tempContainer = document.createElement('div');
      tempContainer.style.position = 'absolute';
      tempContainer.style.left = '-9999px';
      tempContainer.style.backgroundColor = '#ffffff';
      tempContainer.style.width = '800px';
      tempContainer.style.padding = '20px';
      document.body.appendChild(tempContainer);

      // Clonar el contenido
      const clone = element.cloneNode(true) as HTMLElement;
      
      // Ocultar elementos no deseados en el clon
      const elementsToHide = clone.querySelectorAll(
        ".estadisticas-export-buttons, .header-row, .recharts-tooltip-wrapper"
      );
      elementsToHide.forEach(el => (el as HTMLElement).style.display = 'none');

      // Ajustar los gráficos en el clon
      const charts = clone.querySelectorAll('.recharts-responsive-container');
      charts.forEach(chart => {
        (chart as HTMLElement).style.height = '300px';
        (chart as HTMLElement).style.marginBottom = '40px';
      });

      tempContainer.appendChild(clone);

      // Configurar el PDF
      const pdf = new jsPDF({
        orientation: "portrait",
        unit: "px",
        format: "a4"
      });

      const pdfWidth = pdf.internal.pageSize.getWidth();
      const pdfHeight = pdf.internal.pageSize.getHeight();
      const margin = 20;
      const contentWidth = pdfWidth - (2 * margin);

      // Capturar el contenido
      const canvas = await html2canvas(clone, {
        scale: 4,
        backgroundColor: '#ffffff',
        width: tempContainer.offsetWidth - 40,
        height: tempContainer.scrollHeight,
        logging: false,
        onclone: (clonedDoc) => {
          const clonedCharts = clonedDoc.querySelectorAll('.recharts-responsive-container');
          clonedCharts.forEach(chart => {
            (chart as HTMLElement).style.height = '300px';
            (chart as HTMLElement).style.marginBottom = '40px';
          });
        }
      });

      // Limpiar
      document.body.removeChild(tempContainer);

      // Calcular dimensiones
      const imgWidth = contentWidth;
      const imgHeight = (canvas.height * contentWidth) / canvas.width;
      
      let heightLeft = imgHeight;
      let position = margin;
      let page = 1;

      // Agregar páginas
      while (heightLeft >= 0) {
        pdf.addImage(
          canvas.toDataURL('image/png', 1.0),
          'PNG',
          margin,
          position,
          imgWidth,
          imgHeight
        );
        
        heightLeft -= (pdfHeight - (2 * margin));
        
        if (heightLeft > 0) {
          position -= (pdfHeight - (2 * margin));
          pdf.addPage();
          page++;
        }
      }

      // Guardar PDF
      pdf.save("estadisticas.pdf");
      
      onAfterPrint?.();
    } catch (error) {
      console.error("Error al exportar a PDF:", error);
      alert("Hubo un error al generar el PDF. Por favor, intente nuevamente.");
    } finally {
      setIsGenerating(false);
    }
  };

  return (
    <>
      {isGenerating && <LoadingOverlay />}
      <button
        type="button"
        onClick={handleExportPDF}
        className="bg-red-600 hover:bg-red-700 text-white rounded-lg px-6 py-2 font-semibold flex items-center gap-2 shadow-md"
        disabled={isGenerating}
      >
        <span className="material-symbols-outlined" style={{ fontSize: 22 }}>
          download
        </span>
        {isGenerating ? 'Generando...' : 'Exportar a PDF'}
      </button>
    </>
  );
};

export default ExportarPDFButton;
