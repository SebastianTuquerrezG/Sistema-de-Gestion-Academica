import React, { useState, useEffect } from "react";
import "./EvaluacionesCSS/evaluationTable.css";
import Notification from "../../components/notifications/notification";
import { Button } from "@/components/ui/button.tsx";
import {
  submitEvaluation,
  submitCalificationRegister,
  getEvaluationByEnroll,
  getCalificationsByEvaluationId,
  updateCriterios,
} from "../../services/evaluationService";

type NotificationType = {
  type: "error" | "info";
  title: string;
  message: string;
};

interface Descriptor {
  nivel: string;
  texto: string;
}

interface Criterio {
  idCriterio: number;
  criterio: string;
  porcentaje: number;
  descriptores: Descriptor[];
}

interface Props {
  criterios: Criterio[];
}

const EvaluationTable: React.FC<Props> = ({ criterios }) => {
  const data = criterios;

  // Extraer niveles únicos dinámicamente
  const nivelesUnicos = Array.from(
    new Set(data.flatMap((c) => c.descriptores.map((d) => d.nivel)))
  );
  const [valores, setValores] = useState<(number | "")[]>(
    Array(criterios.length).fill("")
  );
  const [comentarios, setComentarios] = useState<string[]>(
    Array(criterios.length).fill("")
  );
  const [evaluationId, setEvaluationId] = useState<number | null>(null);
  const [disabledInputs, setDisabledInputs] = useState<boolean>(false);

  const nivelesUnicos = Array.from(
    new Set(criterios.flatMap((c) => c.descriptores.map((d) => d.nivel)))
  );
  const coloresBase = ["#2e2ebe", "#22229e", "#13137c", "#0d0d66", "#050545"];
  const rangos = nivelesUnicos.map((nivel, index) => ({
    nivel,
    color: coloresBase[index % coloresBase.length],
  }));

  useEffect(() => {
    const checkExistingEvaluation = async () => {
      try {
        const evaluation = await getEvaluationByEnroll(enrollId);
        if (evaluation) {
          setEvaluationId(evaluation.id);
          setDisabledInputs(true);

  const handleChange = (index: number, event: React.ChangeEvent<HTMLInputElement>) => {
    let value = event.target.value;
    const numericValue = parseFloat(value);
    if ((!isNaN(numericValue) && numericValue > 5) || /[^\d.]/.test(value)) {
      setNotification({ type: "error", title: "Error", message: "Rango de calificación [0, 5]." });
      setValores((prev) => { const copy = [...prev]; copy[index] = ""; return copy; });
      return;
    }

    if (/^\d*\.?\d?$/.test(value) || value === "") {
      const numericValue = value === "" ? "" : parseFloat(value);
      setValores((prev) => {
        const copy = [...prev];
        copy[index] = numericValue;
        return copy;
      });
    }
  };

  const getNivel = (valor: number) => {
    if (valor >= 4.0) return "Nivel 3";
    if (valor >= 3.0) return "Nivel 2";
    return "Nivel 1";
  };

  const handleNivelClick = (
    index: number,
    nivel: string,
    descriptores: Descriptor[]
  ) => {
    const descriptor = descriptores.find((d) => d.nivel === nivel);
    if (descriptor) {
      const maxCalificacion = descriptor.superior;
      setValores((prev) => {
        const copy = [...prev];
        copy[index] = maxCalificacion;
        return copy;
      });
    }
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "-") {
      event.preventDefault();
    }
  };

  const handleGuardarEvaluacion = async () => {
    const incompletos = valores.some((v) => v === "");
    if (incompletos) {
      setNotification({
        type: "error",
        title: "Campos incompletos",
        message: "Debes ingresar todas las calificaciones antes de guardar.",
      });
      return;
    }

    const totalScore = criterios.reduce(
      (acc, row, i) => acc + (valores[i] || 0) * (row.porcentaje / 100),
      0
    );
    const evaluationPayload = {
      enroll: enrollId,
      rubric: rubricaId,
      description: `Evaluación de ${estudiante}`,
      evaluationStatus: "EVALUADO",
      score: totalScore,
      evidenceUrl: "https://ejemplo.com/evidencia.pdf",
    };

    try {
      const evaluation = await submitEvaluation(evaluationPayload);

      await Promise.all(
        criterios.map((criterio, index) => {
          console.log(
            "Datos que se van a enviar para el criterio:",
            {
              crfDescripcion: criterio.criterio,
              crfPorcentaje: criterio.porcentaje,
              crfNota: valores[index], // Nota del criterio
              crfComentario: comentarios[index] || "", // Comentario, vacío si no hay
              niveles: criterio.descriptores.map((descriptor) => ({
                idCriterio: criterio.idCriterio, // Aquí pasas el idCriterio de este criterio
                nivelDescripcion: descriptor.nivelDescripcion, // Descripción del nivel
                rangoNota: `${descriptor.inferior} - ${descriptor.superior}`, // Rango de nota
              })),
              idRubrica: rubricaId, // ID de la rúbrica
            },
            "criterioId:",
            criterio.idCriterio
          );

          // Llamada a la función updateCriterios para cada criterio
          return updateCriterios(
            {
              crfDescripcion: criterio.criterio,
              crfPorcentaje: criterio.porcentaje / 100,
              crfNota: valores[index],
              crfComentario: comentarios[index] || "",
              niveles: criterio.descriptores.map((descriptor) => ({
                idCriterio: criterio.idCriterio,
                nivelDescripcion: descriptor.nivelDescripcion,
                rangoNota: `${descriptor.inferior} - ${descriptor.superior}`,
              })),
              idRubrica: Number(rubricaId), // ID de la rúbrica
            },
            criterio.idCriterio // ID del criterio a actualizar
          );
        })
      );

      await Promise.all(
        criterios.map((criterio, index) =>
          submitCalificationRegister({
            calification: valores[index],
            message: comentarios[index] || "",
            level: getNivel(Number(valores[index]), criterio.descriptores),
            evaluationId: evaluation.id,
          })
        )
      );

      setNotification({
        type: "info",
        title: "Éxito",
        message: "Evaluación guardada correctamente.",
      });
      setDisabledInputs(true);
    } catch (error) {
      setNotification({
        type: "error",
        title: "Error",
        message: "No se pudo guardar la evaluación.",
      });
    }

    const evaluacion = data.map((criterio, index) => ({
      criterio: criterio.criterio,
      porcentaje: criterio.porcentaje,
      calificacion: valores[index] || 0,
      comentario: comentarios[index],
      nivel: getNivel(Number(valores[index]) || 0),
    }));

    console.log("Evaluación enviada al backend:", evaluacion);

    setNotification({
      type: "info",
      title: "Éxito",
      message: "La evaluación fue guardada correctamente.",
    });

    setHasChanges(false);
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
            {rangos.map((_, i) => (
              <th key={i}>—</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {criterios.map((row, index) => {
            const valorActual = valores[index];
            const nivelActual = valorActual === "" ? "" : getNivel(Number(valorActual));
            const ponderado = valorActual === "" ? "" : (Number(valorActual) * (row.porcentaje / 100)).toFixed(2);

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
                        cursor: disabledInputs ? "default" : "pointer",
                      }}
                      onClick={() =>
                        !disabledInputs &&
                        handleNivelClick(index, rango.nivel, row.descriptores)
                      }
                    >
                      {descriptor?.nivelDescripcion || "-"}
                    </td>
                  );
                })}
                <td>{row.porcentaje}%</td>
                <td>
                  <input
                    type="number"
                    value={valores[index] || ""}
                    onChange={(e) => handleChange(index, e)}
                    onKeyDown={(e) => {
                      if (!disabledInputs) handleKeyDown(e);
                    }}
                    disabled={disabledInputs}
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
                        if (!disabledInputs) {
                          const copy = [...comentarios];
                          copy[index] = e.target.value;
                          setComentarios(copy);
                        }
                      }}
                      disabled={disabledInputs}
                      className="comment-box"
                      placeholder="Escribe un comentario..."
                      maxLength={250}
                    />
                    <div
                      className={`char-count ${comentarios[index].length === 250 ? "char-limit-reached" : ""}`}
                    >
                      {comentarios[index].length}/250
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
              {valores.every((v) => v !== "")
                ? criterios
                    .reduce(
                      (acc, row, i) =>
                        typeof valores[i] === "number"
                          ? acc + valores[i]! * (row.porcentaje / 100)
                          : acc,
                      0
                    )
                    .toFixed(2)
                : "—"}
            </td>
          </tr>
        </tfoot>
      </table>
      {valores.some((v) => v === "") && (
        <p style={{ color: "red", textAlign: "right", marginTop: "8px" }}>
          Completa todas las calificaciones para ver el total ponderado.
        </p>
      )}

      <div className="button-container">
        {disabledInputs && (
          <span className="lock-icon material-symbols-outlined">lock</span>
        )}
        <Button
          onClick={handleGuardarEvaluacion}
          disabled={!estudiante || estudiante.trim() === "" || disabledInputs}
          className={
            !estudiante || estudiante.trim() === "" || disabledInputs
              ? "disabled-eval-btn"
              : ""
          }
        >
          Guardar evaluación
        </Button>
      </div>
    </div>
  );
};

export default EvaluationTable;
