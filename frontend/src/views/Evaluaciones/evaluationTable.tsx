import React, { useState, useEffect } from "react";
import "./EvaluacionesCSS/evaluationTable.css";
import Notification from "../../components/notifications/notification";
import { Button } from "@/components/ui/button.tsx";

type NotificationType = {
  type: "error" | "info";
  title: string;
  message: string;
};

interface Descriptor {
  nivel: string;
  texto: string;
  inferior: number;
  superior: number;
}

interface Criterio {
  criterio: string;
  porcentaje: number;
  descriptores: Descriptor[];
}

interface Props {
  criterios: Criterio[];
  estudiante: string;
}

const EvaluationTable: React.FC<Props> = ({ criterios, estudiante }) => {
  const data = criterios;

  // Extraer niveles únicos de todos los descriptores
  const nivelesUnicos = Array.from(
    new Set(data.flatMap((c) => c.descriptores.map((d) => d.nivel)))
  );

  const coloresBase = ["#2e2ebe", "#22229e", "#13137c", "#0d0d66", "#050545"];
  const rangos = nivelesUnicos.map((nivel, index) => ({
    nivel,
    color: coloresBase[index % coloresBase.length],
  }));

  const [notification, setNotification] = useState<NotificationType | null>(
    null
  );
  const [hasChanges, setHasChanges] = useState(false);
  const [valores, setValores] = useState<(number | "")[]>(
    Array(data.length).fill("")
  );
  const [comentarios, setComentarios] = useState<string[]>(
    Array(data.length).fill("")
  );

  const handleChange = (
    index: number,
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    const value = event.target.value;

    // Si el usuario ingresa solo "-", o un número fuera del rango permitido, se reinicia el campo
    if (
      value === "-" ||
      value.includes("-") ||
      (/^\d*\.?\d?$/.test(value) &&
        value !== "" &&
        (parseFloat(value) < 0 || parseFloat(value) > 5))
    ) {
      alert("La calificación debe estar entre 0 y 5.");
      setValores((prevValores) => {
        const resetValores = [...prevValores];
        resetValores[index] = "";
        return resetValores;
      });
      return;
    }

    // Validar formato: números con un decimal opcional
    if (/^\d*\.?\d?$/.test(value) || value === "") {
      const numericValue = value === "" ? "" : parseFloat(value);
      setValores((prevValores) => {
        const newValores = [...prevValores];
        newValores[index] = value === "" ? "" : numericValue;
        return newValores;
      });
    }
  };

  // Detección dinámica del nivel según los rangos del descriptor
  const getNivel = (valor: number, descriptores: Descriptor[]): string => {
    const descriptor = descriptores.find(
      (d) => valor >= d.inferior && valor <= d.superior
    );
    return descriptor?.nivel || "";
  };
  // Función para evitar que el usuario presione "-"
  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "-") {
      event.preventDefault(); // Bloquea la tecla "-"
    }
  };

  const handleGuardarEvaluacion = () => {
    const incompletos = valores.some((v) => v === "");
    const comentarioExcedido = comentarios.some((c) => c.length > 250);

    if (incompletos) {
      setNotification({
        type: "error",
        title: "Campos incompletos",
        message: "Debes ingresar todas las calificaciones antes de guardar.",
      });
      return;
    }

    if (comentarioExcedido) {
      setNotification({
        type: "error",
        title: "Comentario demasiado largo",
        message: "Cada comentario debe tener máximo 250 caracteres.",
      });
      return;
    }

    const evaluacion = data.map((criterio, index) => ({
      criterio: criterio.criterio,
      porcentaje: criterio.porcentaje,
      calificacion: valores[index] || 0,
      comentario: comentarios[index],
      nivel: getNivel(Number(valores[index]) || 0, criterio.descriptores),
    }));

    console.log("Evaluación enviada al backend:", evaluacion);

    setNotification({
      type: "info",
      title: "Éxito",
      message: "La evaluación fue guardada correctamente.",
    });

    setHasChanges(false);
  };

  // Limpiar datos al cambiar de estudiante
  useEffect(() => {
    setValores(Array(criterios.length).fill(""));
    setComentarios(Array(criterios.length).fill(""));
    setHasChanges(false);
  }, [estudiante]);

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

  useEffect(() => {
    if (notification) {
      const timeout = setTimeout(() => setNotification(null), 4000);
      return () => clearTimeout(timeout);
    }
  }, [notification]);

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
              <th key={i} style={{ backgroundColor: r.color }}>
                {r.nivel}
              </th>
            ))}
          </tr>
          <tr>
            {rangos.map((rango, i) => {
              const descriptor = data[0]?.descriptores.find(
                (d) => d.nivel === rango.nivel
              );
              return (
                <th key={i}>
                  {descriptor
                    ? `${descriptor.inferior} - ${descriptor.superior}`
                    : "—"}
                </th>
              );
            })}
          </tr>
        </thead>
        <tbody>
          {data.map((row, index) => {
            const valorActual = valores[index];
            const nivelActual =
              valorActual === ""
                ? ""
                : getNivel(Number(valorActual), row.descriptores);
            const ponderado =
              valorActual === ""
                ? ""
                : (Number(valorActual) * (row.porcentaje / 100)).toFixed(2);

            return (
              <tr key={index}>
                <td>{row.criterio}</td>
                {rangos.map((rango, i) => {
                  const descriptor = row.descriptores.find(
                    (d) => d.nivel === rango.nivel
                  );
                  return (
                    <td
                      key={i}
                      style={{
                        backgroundColor:
                          nivelActual === rango.nivel
                            ? rango.color
                            : "transparent",
                        color: nivelActual === rango.nivel ? "white" : "black",
                      }}
                    >
                      {descriptor?.texto || "-"}
                    </td>
                  );
                })}
                <td>{row.porcentaje}%</td>
                <td>
                  <input
                    type="number"
                    value={valores[index] || ""}
                    onChange={(e) => handleChange(index, e)}
                    onKeyDown={handleKeyDown}
                    step="0.1"
                    min="0"
                    max="5"
                    placeholder="0.0"
                    className="evaluation-input"
                  />
                </td>
                <td>{ponderado}</td>
                <td>
                  <div>
                    <textarea
                      value={comentarios[index]}
                      onChange={(e) => {
                        const copy = [...comentarios];
                        copy[index] = e.target.value;
                        setComentarios(copy);
                      }}
                      className="comment-box"
                      placeholder="Escribe un comentario..."
                      maxLength={250}
                    />
                    <div
                      className={`char-count ${
                        comentarios[index]?.length === 250
                          ? "char-limit-reached"
                          : ""
                      }`}
                    >
                      {comentarios[index]?.length ?? 0}/250
                    </div>
                  </div>
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
              {data
                .reduce(
                  (acc, row, i) =>
                    acc + (valores[i] || 0) * (row.porcentaje / 100),
                  0
                )
                .toFixed(2)}
            </td>
          </tr>
        </tfoot>
      </table>

      <div className="button-container">
        <Button onClick={handleGuardarEvaluacion}>Guardar evaluación</Button>
      </div>
    </div>
  );
};

export default EvaluationTable;
