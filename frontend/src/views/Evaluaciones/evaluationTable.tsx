import React, { useState, useEffect, useCallback, useRef } from "react";
import "./EvaluacionesCSS/evaluationTable.css";
import Notification from "../../components/notifications/notification";
import { Button } from "../../components/ui/button.tsx";
import {
  submitEvaluation,
  submitCalificationRegister,
  getEvaluationByEnrollAndRubric,
  getCalificationsByEvaluationId,
} from "../../services/evaluationService";
import { Criterio, NotificationType, COLORS_BASE } from "./types";
import { getNivel, calculateTotalScore } from "./utils/evaluationUtils";

interface Props {
  criterios: Criterio[];
  enrollId: number;
  rubricaId: number;
  estudiante: string;
}

const EvaluationTable: React.FC<Props> = ({ criterios, enrollId, rubricaId, estudiante }) => {
  const [valores, setValores] = useState<(number | "")[]>([]);
  const [comentarios, setComentarios] = useState<string[]>([]);
  const [evaluationId, setEvaluationId] = useState<number | null>(null);
  const [disabledInputs, setDisabledInputs] = useState<boolean>(false);
  const [notification, setNotification] = useState<NotificationType | null>(null);
  const [hasChanges, setHasChanges] = useState<boolean>(false);
  const [isLoading, setIsLoading] = useState<boolean>(true);
  const [forceRender, setForceRender] = useState(0);
  
  // Ref para evitar múltiples cargas simultáneas
  const loadingRef = useRef(false);
  const currentStudentRef = useRef<string>("");

  const nivelesUnicos = Array.from(
    new Set(criterios.flatMap((c) => c.descriptores.map((d) => d.nivel)))
  );
  const rangos = nivelesUnicos.map((nivel, index) => ({
    nivel,
    color: COLORS_BASE[index % COLORS_BASE.length],
  }));

  // Función de limpieza INMEDIATA y COMPLETA
  const forceCleanState = useCallback(() => {
    const emptyValues = Array(criterios.length).fill("");
    const emptyComments = Array(criterios.length).fill("");
    
    // Limpieza SÍNCRONA e inmediata
    setValores(emptyValues);
    setComentarios(emptyComments);
    setEvaluationId(null);
    setDisabledInputs(false);
    setHasChanges(false);
    setIsLoading(true);
    
    // Forzar re-render para garantizar actualización visual
    setForceRender(prev => prev + 1);
    
    return { emptyValues, emptyComments };
  }, [criterios.length, estudiante]);

  // EFECTO PRINCIPAL: Detectar cambio de estudiante y limpiar INMEDIATAMENTE
  useEffect(() => {
    // Si cambió el estudiante, limpiar inmediatamente
    if (currentStudentRef.current !== estudiante && currentStudentRef.current !== "") {
      forceCleanState();
    }
    currentStudentRef.current = estudiante;
    // Si no tenemos datos mínimos, no continuar
    if (!enrollId || !rubricaId || criterios.length === 0) {
      setIsLoading(false);
      return;
    }
    // Prevenir múltiples cargas simultáneas
    if (loadingRef.current) {
      return;
    }
    const loadEvaluation = async () => {
      loadingRef.current = true;
      try {
        const evaluation = await getEvaluationByEnrollAndRubric(enrollId, rubricaId);
        if (!evaluation?.id) {
          // Asegurar estado limpio para estudiante sin evaluación
          const cleanValues = Array(criterios.length).fill("");
          const cleanComments = Array(criterios.length).fill("");
          setValores(cleanValues);
          setComentarios(cleanComments);
          setEvaluationId(null);
          setDisabledInputs(false);
          setIsLoading(false);
          return;
        }
        setEvaluationId(evaluation.id);
        setDisabledInputs(evaluation.evaluationStatus === "EVALUADO");
        const calificaciones = await getCalificationsByEvaluationId(evaluation.id);
        if (Array.isArray(calificaciones)) {
          const calificacionesPorCriterio = new Map(
            calificaciones.map((cal) => [cal.criteriaId, cal])
          );
          const nuevosValores = criterios.map((criterio) => {
            const registro = calificacionesPorCriterio.get(criterio.idCriterio);
            return registro?.calification ?? "";
          });
          const nuevosComentarios = criterios.map((criterio) => {
            const registro = calificacionesPorCriterio.get(criterio.idCriterio);
            return registro?.message ?? "";
          });
          setValores(nuevosValores);
          setComentarios(nuevosComentarios);
        }
      } catch (error) {
        // LIMPIEZA AGRESIVA en caso de error
        const cleanValues = Array(criterios.length).fill("");
        const cleanComments = Array(criterios.length).fill("");
        setValores(cleanValues);
        setComentarios(cleanComments);
        setEvaluationId(null);
        setDisabledInputs(false);
      } finally {
        setIsLoading(false);
        loadingRef.current = false;
      }
    };
    // Pequeño delay para asegurar que el estado se limpie primero
    const timeoutId = setTimeout(loadEvaluation, 100);
    return () => {
      clearTimeout(timeoutId);
      loadingRef.current = false;
    };
  }, [enrollId, rubricaId, estudiante, criterios, forceCleanState]);

  const handleChange = (index: number, event: React.ChangeEvent<HTMLInputElement>) => {
    let value = event.target.value;
    const numericValue = parseFloat(value);
    if ((!isNaN(numericValue) && numericValue > 5) || /[^\d.]/.test(value)) {
      setNotification({ type: "error", title: "Error", message: "Rango de calificación [0, 5]." });
      setValores((prev) => {
        const copy = [...prev];
        copy[index] = "";
        return copy;
      });
      return;
    }

    if (/^\d*\.?\d?$/.test(value) || value === "") {
      const numericValue = value === "" ? "" : parseFloat(value);
      setValores((prev) => {
        const copy = [...prev];
        copy[index] = numericValue;
        return copy;
      });
      setHasChanges(true);
    }
  };

  const handleNivelClick = (
    index: number,
    nivel: string,
    descriptores: Criterio["descriptores"]
  ) => {
    const descriptor = descriptores.find((d) => d.nivel === nivel);
    if (descriptor) {
      setValores((prev) => {
        const copy = [...prev];
        copy[index] = descriptor.superior;
        return copy;
      });
      setHasChanges(true);
    }
  };

  const handleKeyDown = (event: React.KeyboardEvent<HTMLInputElement>) => {
    if (event.key === "-") {
      event.preventDefault();
    }
  };

  const calcularTotalPonderado = () => {
    return criterios.reduce((acc, row, i) => {
      const valor = valores[i];
      const porcentajePonderado = row.porcentaje > 100 ? row.porcentaje / 100 : row.porcentaje;
      if (typeof valor === "number") {
        return acc + (valor * (porcentajePonderado / 100));
      }
      return acc;
    }, 0);
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

    const totalScore = calcularTotalPonderado();
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
        criterios.map((criterio, index) =>
          submitCalificationRegister({
            calification: valores[index],
            criteriaId: criterio.idCriterio,
            evaluationId: evaluation.id,
            level: getNivel(Number(valores[index])),
            message: comentarios[index] || "",
          })
        )
      );

      setNotification({
        type: "info",
        title: "Éxito",
        message: "Evaluación guardada correctamente.",
      });
      setDisabledInputs(true);
      setHasChanges(false);
    } catch (error) {
      setNotification({
        type: "error",
        title: "Error",
        message: "No se pudo guardar la evaluación.",
      });
    }
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
    <div className="evaluation-table-container" key={`${estudiante}-${enrollId}-${forceRender}`}>
      {notification && (
        <Notification
          type={notification.type}
          title={notification.title}
          message={notification.message}
          onClose={() => setNotification(null)}
        />
      )}

      {isLoading ? (
        <div className="loading-container">
          <p>Cargando evaluación para {estudiante}...</p>
        </div>
      ) : (
        <>
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
                {rangos.map((rango, i) => {
                  const descriptor = criterios
                    .map((c) => c.descriptores.find((d) => d.nivel === rango.nivel))
                    .find(Boolean);
                  return <th key={i}>{descriptor?.rangoNota ?? "—"}</th>;
                })}
                <th></th><th></th><th></th><th></th>
              </tr>
            </thead>
            <tbody>
              {criterios.map((row, index) => {
                const valorActual = valores[index] ?? "";
                let colored = false;
                return (
                  <tr key={`${row.idCriterio}-${forceRender}`}>
                    <td>{row.criterio}</td>
                    {rangos.map((rango, i) => {
                      const descriptor = row.descriptores.find((d) => d.nivel === rango.nivel);
                      let isActive = false;
                      if (!colored && valorActual !== "" && descriptor &&
                          Number(valorActual) >= descriptor.inferior &&
                          Number(valorActual) <= descriptor.superior) {
                        isActive = true;
                        colored = true;
                      }
                      return (
                        <td
                          key={i}
                          style={{
                            backgroundColor: isActive ? rango.color : "transparent",
                            color: isActive ? "white" : "black",
                            cursor: disabledInputs ? "default" : "pointer",
                          }}
                          onClick={() =>
                            !disabledInputs && handleNivelClick(index, rango.nivel, row.descriptores)
                          }
                        >
                          {descriptor?.nivelDescripcion ?? "-"}
                        </td>
                      );
                    })}
                    <td>{row.porcentaje > 100 ? row.porcentaje / 100 : row.porcentaje}%</td>
                    <td>
                      <input
                        key={`input-${index}-${forceRender}`}
                        type="number"
                        value={valorActual}
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
                    <td>
                      {typeof valorActual === "number"
                        ? (valorActual * ((row.porcentaje > 100 ? row.porcentaje / 100 : row.porcentaje) / 100)).toFixed(2)
                        : ""}
                    </td>
                    <td>
                      {disabledInputs ? (
                        <div className="comment-box" style={{ background: "#f8f8f8", color: "#888", minHeight: 40, padding: 8, borderRadius: 5 }}>
                          {comentarios[index]?.trim() ? comentarios[index] : "Sin comentario"}
                        </div>
                      ) : (
                        <>
                          <textarea
                            key={`textarea-${index}-${forceRender}`}
                            value={comentarios[index] ?? ""}
                            onChange={(e) => {
                              const copy = [...comentarios];
                              copy[index] = e.target.value;
                              setComentarios(copy);
                              setHasChanges(true);
                            }}
                            disabled={disabledInputs}
                            className="comment-box"
                            placeholder="Escribe un comentario..."
                            maxLength={250}
                          />
                          <div className={`char-count ${comentarios[index]?.length === 250 ? "char-limit-reached" : ""}`}>
                            {comentarios[index]?.length ?? 0}/250
                          </div>
                        </>
                      )}
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
                    ? calcularTotalPonderado().toFixed(2)
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
              disabled={!estudiante || estudiante.trim() === "" || disabledInputs || isLoading}
              className={
                !estudiante || estudiante.trim() === "" || disabledInputs || isLoading
                  ? "disabled-eval-btn"
                  : ""
              }
            >
              Guardar evaluación
            </Button>
          </div>
        </>
      )}
    </div>
  );
};

export default EvaluationTable;