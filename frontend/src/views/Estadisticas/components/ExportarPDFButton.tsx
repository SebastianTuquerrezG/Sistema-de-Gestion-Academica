import React from "react";
import html2canvas from "html2canvas-pro";
import jsPDF from "jspdf";

interface ExportarPDFButtonProps {
  targetRef: React.RefObject<HTMLDivElement | null>;
}

const ExportarPDFButton: React.FC<ExportarPDFButtonProps> = ({ targetRef }) => {
  const handleExportPDF = async () => {
    if (!targetRef.current) return;

    try {
      const clone = targetRef.current.cloneNode(true) as HTMLElement;
      const exportButtons = clone.querySelectorAll(
        ".estadisticas-export-buttons, .header-row"
      );
      exportButtons.forEach((btn) => btn.remove());
      const replaceOklchColors = (element: HTMLElement) => {
        const walker = document.createTreeWalker(
          element,
          NodeFilter.SHOW_ELEMENT
        );
        while (walker.nextNode()) {
          const el = walker.currentNode as HTMLElement;
          const style = window.getComputedStyle(el);
          const properties = ["color", "backgroundColor", "borderColor"];
          for (const prop of properties) {
            const value = style[prop as keyof CSSStyleDeclaration] as string;
            if (value && value.includes("oklch")) {
              switch (prop) {
                case "color":
                  el.style.color = "#000000";
                  break;
                case "backgroundColor":
                  el.style.backgroundColor = "#ffffff";
                  break;
                case "borderColor":
                  el.style.borderColor = "#cccccc";
                  break;
              }
            }
          }
        }
      };

      replaceOklchColors(clone);

      const tempContainer = document.createElement("div");
      tempContainer.style.position = "fixed";
      tempContainer.style.left = "-9999px";
      tempContainer.appendChild(clone);
      document.body.appendChild(tempContainer);

      const canvas = await html2canvas(clone, { scale: 2 });

      document.body.removeChild(tempContainer);

      const imgData = canvas.toDataURL("image/png");

      const pdf = new jsPDF({
        orientation: "portrait",
        unit: "px",
        format: "a4",
      });

      const pdfWidth = pdf.internal.pageSize.getWidth();
      const pdfHeight = pdf.internal.pageSize.getHeight();

      const imgProps = pdf.getImageProperties(imgData);
      const imgWidth = pdfWidth;
      const imgHeight = (imgProps.height * imgWidth) / imgProps.width;

      let heightLeft = imgHeight;
      let position = 0;

      while (heightLeft > 0) {
        pdf.addImage(imgData, "PNG", 0, position, imgWidth, imgHeight);
        heightLeft -= pdfHeight;
        if (heightLeft > 0) {
          pdf.addPage();
          position -= pdfHeight;
        }
      }

      pdf.save("estadisticas.pdf");
    } catch (error) {
      console.error("Error al exportar a PDF:", error);
    }
  };

  return (
    <button
      type="button"
      onClick={handleExportPDF}
      className="bg-red-600 hover:bg-red-700 text-white rounded-lg px-6 py-2 font-semibold flex items-center gap-2 shadow-md"
    >
      <span className="material-symbols-outlined" style={{ fontSize: 22 }}>
        download
      </span>
      Exportar a PDF
    </button>
  );
};

export default ExportarPDFButton;
