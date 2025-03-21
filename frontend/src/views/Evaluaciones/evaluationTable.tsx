import React, { useState } from "react";
import "./EvaluacionesCSS/evaluationTable.css";

const EvaluationTable: React.FC = () => {
  const data = [
    { criterio: "Genera ideas que dan solución a situaciones planteadas", nivel3: "La idea planteada satisface la necesidad generada y contempla aspectos de creatividad", nivel2: "Genera ideas que dan solución a las situaciones planteadas", nivel1: "Las ideas generadas apuntan a dar solución a la situación planteada pero no alcanzan a plasmarse en el prototipo presentado", porcentaje: 15 },
    { criterio: "Propuesta de Diseño claro y uso de herramientas acordes con la propuesta", nivel3: "Introduce herramientas computacionales novedosas al diseño trabajado", nivel2: "Propuesta de Diseño claro y uso de herramientas acordes con la propuesta", nivel1: "No hace uso de herramientas adecuadas para presentar el diseño", porcentaje: 30 },
    { criterio: "Evidencia Funcional del Diseño Propuesto", nivel3: "Utiliza nuevos planteamientos de validación del diseño", nivel2: "Evidencia funcional del diseño propuesto", nivel1: "La validación del diseño, presenta inconsistencias", porcentaje: 20 },
    { criterio: "Presenta el prototipo y cumple con el diseño planteado", nivel3: "El prototipo está soportado en elementos innovadores, manteniendo el diseño propuesto", nivel2: "Presenta el prototipo y cumple con el diseño planteado", nivel1: "El prototipo funciona de forma intermitente", porcentaje: 35 }
  ];

  // Estado para almacenar las calificaciones ingresadas por fila
  const [valores, setValores] = useState<(number | "")[]>(Array(data.length).fill(""));
  const [comentarios, setComentarios] = useState<string[]>(Array(data.length).fill(""));

  const rangos = [
    { nivel: "nivel3", superior: 5.0, inferior: 4.0, color: "#2e2ebe" },
    { nivel: "nivel2", superior: 3.9, inferior: 3.0, color: "#22229e" },
    { nivel: "nivel1", superior: 2.9, inferior: 0.0, color: "#13137c" },
  ];

  // Función para actualizar el valor de la calificación ingresada
  const handleChange = (index: number, event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    const newValores = [...valores];
    newValores[index] = value === "" ? "" : parseFloat(value);
    setValores(newValores);
  };

  const handleCommentChange = (index: number, event: React.ChangeEvent<HTMLTextAreaElement>) => {
    const newComentarios = [...comentarios];
    newComentarios[index] = event.target.value;
    setComentarios(newComentarios);
  };

  // Función que obtiene el nivel al que pertenece la calificación
  const getNivel = (valor: number) => {
    return rangos.find(r => valor >= r.inferior && valor <= r.superior)?.nivel || "";
  };

  return (
    <div className="evaluation-table-container">
      <table className="evaluation-table">
        <colgroup>
          <>
            <col style={{ width: "25%" }} />
            <col style={{ width: "15%" }} />
            <col style={{ width: "15%" }} />
            <col style={{ width: "15%" }} />
            <col style={{ width: "9%" }} />
            <col style={{ width: "9%" }} />
            <col style={{ width: "9%" }} />
            <col style={{ width: "20%" }} />
          </>
        </colgroup>

        <thead>
          <tr>
            <th rowSpan={3}>Criterios</th>
            <th colSpan={7}>Descriptores</th>
          </tr>
          <tr>
            <th id='header-level3'>Nivel 3</th>
            <th id='header-level2'>Nivel 2</th>
            <th id='header-level1'>Nivel 1</th>
            <th id='secondary-title' rowSpan={2}>Porcentaje</th>
            <th id='secondary-title' rowSpan={2}>Calificación</th>
            <th id='secondary-title' rowSpan={2}>Ponderación</th>
            <th id='secondary-title' rowSpan={2}>Comentario</th>
          </tr>
          <tr>
            {rangos.map((rango, index) => (
              <th key={index} style={{ backgroundColor: rango.color }}>
                {rango.inferior} - {rango.superior}
              </th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((row, index) => {
            const valorActual = valores[index];
            const nivelActual = valorActual === "" ? "" : getNivel(Number(valorActual));
            const ponderado = valorActual === "" ? "" : ((Number(valorActual) * (row.porcentaje / 100)).toFixed(2));

            return (
              <tr key={index}>
                <td id='criterios-column'>{row.criterio}</td>
                <td style={{ backgroundColor: nivelActual === "nivel3" ? "#2e2ebe" : "transparent", color: nivelActual === "nivel3" ? "white" : "black" }}>
                  {row.nivel3}
                </td>
                <td style={{ backgroundColor: nivelActual === "nivel2" ? "#22229e" : "transparent", color: nivelActual === "nivel2" ? "white" : "black" }}>
                  {row.nivel2}
                </td>
                <td style={{ backgroundColor: nivelActual === "nivel1" ? "#13137c" : "transparent", color: nivelActual === "nivel1" ? "white" : "black" }}>
                  {row.nivel1}
                </td>
                <td>{row.porcentaje}%</td>
                <td>
                  <input
                    type="number"
                    id={`calificacion-${index}`}   // ID único por fila
                    name={`calificacion-${index}`} // Nombre único por fila
                    placeholder="0.0"
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
                  <div>
                    <textarea
                      id={`comentario-${index}`}   // ID único por fila
                      name={`comentario-${index}`} // Nombre único por fila
                      value={comentarios[index]}
                      onChange={(e) => handleCommentChange(index, e)}
                      className="comment-box"
                      placeholder="Escribe un comentario..."
                    />
                  </div>
                </td>
              </tr>
            );
          })}
        </tbody>
        <tfoot>
          <tr>
            <td colSpan={5}></td>
            <td>TOTAL</td>
            <td>
              {data
                .reduce((acc, row, index) => acc + (valores[index] || 0) * (row.porcentaje / 100), 0)
                .toFixed(2)}
            </td>
          </tr>
        </tfoot>
      </table>

      {/* Botón Evaluar */}
      <div className="button-container">
        <button className="evaluate-button" onClick={() => {
          const evaluaciones = data.map((row, index) => ({
            criterio: row.criterio,
            nivelSeleccionado: getNivel(Number(valores[index])),
            calificacion: valores[index] || 0,
            comentario: comentarios[index]
          }));
          console.log("Evaluaciones:", JSON.stringify(evaluaciones, null, 2));
        }}>
          Evaluar
        </button>
      </div>
    </div>
  );
};

export default EvaluationTable;