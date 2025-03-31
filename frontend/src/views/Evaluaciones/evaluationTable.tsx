import React, { useState, useEffect } from "react";
import "./EvaluacionesCSS/evaluationTable.css";
import Notification from "../../components/notifications/notification";

// Definición de tipo para la notificación
type NotificationType = {
  type: "error" | "info";
  title: string;
  message: string;
};

const EvaluationTable: React.FC = () => {
  const rangos = [
    { nivel: "Nivel 5", superior: 0.9, inferior: 0.0, color: "#13137c" },
    { nivel: "Nivel 4", superior: 1.9, inferior: 1.0, color: "#13137c" },
    { nivel: "Nivel 3", superior: 2.9, inferior: 2.0, color: "#2e2ebe" },
    { nivel: "Nivel 2", superior: 3.9, inferior: 3.0, color: "#22229e" },
    { nivel: "Nivel 1", superior: 5.0, inferior: 4.0, color: "#13137c" },
  ];

  const data = [
    {
      criterio: "Genera ideas que dan solución a situaciones planteadas",
      descriptores: [
        { nivel: "Nivel 3", texto: "La idea planteada satisface la necesidad generada y contempla aspectos de creatividad" },
        { nivel: "Nivel 2", texto: "Genera ideas que dan solución a las situaciones planteadas" },
        { nivel: "Nivel 1", texto: "Las ideas generadas apuntan a dar solución a la situación planteada pero no alcanzan a plasmarse en el prototipo presentado" },
      ],
      porcentaje: 15,
    },
    {
      criterio: "Propuesta de Diseño claro y uso de herramientas acordes con la propuesta",
      descriptores: [
        { nivel: "Nivel 3", texto: "Introduce herramientas computacionales novedosas al diseño trabajado" },
        { nivel: "Nivel 2", texto: "Propuesta de Diseño claro y uso de herramientas acordes con la propuesta" },
        { nivel: "Nivel 1", texto: "No hace uso de herramientas adecuadas para presentar el diseño" },
      ],
      porcentaje: 30,
    },
    {
      criterio: "Evidencia Funcional del Diseño Propuesto",
      descriptores: [
        { nivel: "Nivel 3", texto: "Utiliza nuevos planteamientos de validación del diseño" },
        { nivel: "Nivel 2", texto: "Evidencia funcional del diseño propuesto" },
        { nivel: "Nivel 1", texto: "La validación del diseño, presenta inconsistencias" },
      ],
      porcentaje: 20,
    },
  ];

  const [notification, setNotification] = useState<NotificationType | null>(null);
  const [hasChanges, setHasChanges] = useState(false);
  const [valores, setValores] = useState<(number | "")[]>(Array(data.length).fill(""));
  const [comentarios, setComentarios] = useState<string[]>(Array(data.length).fill(""));

  const handleChange = (index: number, event: React.ChangeEvent<HTMLInputElement>) => {
    let value = event.target.value;
    const numericValue = parseFloat(value);
    if ((!isNaN(numericValue) && numericValue > 5) || /[^\d.]/.test(value)) {
      setNotification({ type: "error", title: "Error", message: "Rango de calificación [0, 5]." });
      setValores((prev) => { const copy = [...prev]; copy[index] = ""; return copy; });
      return;
    }
    setValores((prev) => { const copy = [...prev]; copy[index] = value === "" ? "" : numericValue; return copy; });
    setHasChanges(true);
  };

  const getNivel = (valor: number) => {
    return rangos.find((r) => valor >= r.inferior && valor <= r.superior)?.nivel || "";
  };

  useEffect(() => {
    const handleBeforeUnload = (e: BeforeUnloadEvent) => {
      if (hasChanges) {
        e.preventDefault();
        e.returnValue = "";
      }
    };
    window.addEventListener("beforeunload", handleBeforeUnload);
    return () => window.removeEventListener("beforeunload", handleBeforeUnload);
  }, [hasChanges]);

  return (
    <div className="evaluation-table-container">
      {notification && (
        <Notification
          type={notification.type}
          title={notification.title}
          message={notification.message}
          onClose={() => setNotification(null)}
        />
      )}

      <table className="evaluation-table">
        <thead>
          <tr>
            <th rowSpan={3}>Criterios</th>
            <th colSpan={rangos.length}>Descriptores</th>
            <th rowSpan={2}>Porcentaje</th>
            <th rowSpan={2}>Calificación</th>
            <th rowSpan={2}>Ponderación</th>
            <th rowSpan={2}>Comentario</th>
          </tr>
          <tr>
            {rangos.map((r, i) => (
              <th key={i} style={{ backgroundColor: r.color }}>{r.nivel}</th>
            ))}
          </tr>
          <tr>
            {rangos.map((r, i) => (
              <th key={i}>{r.inferior} - {r.superior}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((row, index) => {
            const valorActual = valores[index];
            const nivelActual = valorActual === "" ? "" : getNivel(Number(valorActual));
            const ponderado = valorActual === "" ? "" : (Number(valorActual) * (row.porcentaje / 100)).toFixed(2);

            return (
              <tr key={index}>
                <td>{row.criterio}</td>
                {rangos.map((rango, i) => (
                  <td
                    key={i}
                    style={{
                      backgroundColor: nivelActual === rango.nivel ? rango.color : "transparent",
                      color: nivelActual === rango.nivel ? "white" : "black",
                    }}
                  >
                    {row.descriptores[i]?.texto || "-"}
                  </td>
                ))}
                <td>{row.porcentaje}%</td>
                <td>
                  <input
                    type="number"
                    value={valores[index] || ""}
                    onChange={(e) => handleChange(index, e)}
                    step="0.1"
                    min="0"
                    max="5"
                    className="evaluation-input"
                  />
                </td>
                <td>{ponderado}</td>
                <td>
                  <textarea
                    value={comentarios[index]}
                    onChange={(e) => {
                      const copy = [...comentarios];
                      copy[index] = e.target.value;
                      setComentarios(copy);
                    }}
                    className="comment-box"
                    placeholder="Escribe un comentario..."
                  />
                </td>
              </tr>
            );
          })}
        </tbody>
        <tfoot>
          <tr>
            <td colSpan={rangos.length + 2}></td>
            <td>TOTAL</td>
            <td>
              {data.reduce(
                (acc, row, i) => acc + (valores[i] || 0) * (row.porcentaje / 100),
                0
              ).toFixed(2)}
            </td>
          </tr>
        </tfoot>
      </table>
    </div>
  );
};

export default EvaluationTable;
