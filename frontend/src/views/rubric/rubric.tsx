import React from 'react';
import "./rubric.css";
import { useEffect, useState } from "react";
import Notification from '../../components/notifications/notification.tsx';

import Layout from "../../components/layout/layout.tsx";
import {useLocation, useParams} from "react-router-dom";
import {EvaluationResponseViewDTO, getRubricEvaluation} from "../../services/rubricEvaluationService.ts";

const Rubric: React.FC = () => {

    const { idStudent , idSubject, period, idRubric } = useParams();
    const [rubrica, setRubrica] = useState<EvaluationResponseViewDTO | null>(null);
    const [showNotification, setShowNotification] = useState(false);
    const [notificationMessage, setNotificationMessage] = useState("");
    const { state } = useLocation();
    const rubricName = state?.rubricName;
    const nameTeacher = state?.nameTeacher;

    const handleClose = () => {
        setShowNotification(false);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const data = await getRubricEvaluation(idStudent, idSubject, period, idRubric);
                setRubrica(data);
            } catch (error: any) {
                setNotificationMessage(error.message);
                setShowNotification(true);
            }
        };

        fetchData();
    }, []);



  return (
      <Layout>
          <div>
              {showNotification && (
                  <Notification
                      type="error"
                      title="Error al cargar los datos"
                      message={notificationMessage}
                      onClose={handleClose}
                  />
              )}

              <div className="fila1">
                  <div className="fila1columna1">
                      <span className="material-symbols-outlined">view_list</span>
                  </div>
                  <div className="fila1columna2">
                      <h2 className="main">{rubricName}</h2>
                      <h4 className="main">{nameTeacher}</h4>
                  </div>
              </div>
              <div className="fila2">
                  <p className="descripcion">{rubrica?.description}</p>
                  <div className='tableRubric'>
                      <div className='scroll'>
                              <table >
                                  <thead>
                                  <tr>
                                      <th>Criterios de Evaluación</th>
                                      {rubrica?.criterias[0]?.levels.map((_, index) => (
                                          <th key={index}>{`Nivel ${index + 1}`}</th>
                                      ))}

                                      <th rowSpan={2}>Calificación</th>
                                      <th rowSpan={2}>Porcentaje</th>
                                      <th rowSpan={2}>Ponderación</th>
                                      <th rowSpan={2}>Comentarios</th>

                                  </tr>
                                  <tr>
                                      <th>Rangos:</th>
                                      {rubrica?.criterias[0]?.levels.map((level, index) => (
                                          <th key={index}>{level.range}</th>
                                      ))}
                                  </tr>

                                  </thead>
                                  <tbody>
                                  {rubrica?.criterias.map((criterio, i) => (
                                      <tr key={i}>
                                          {/* Nombre del criterio */}
                                          <td>{criterio.descriptionCriteria}</td>

                                          {/* Descripción de cada nivel */}
                                          {criterio.levels.map((level, j) => {
                                              // construye el nombre del nivel como "Nivel 1", "Nivel 2", etc.
                                              const nivelNombre = `Nivel ${j + 1}`;
                                              const nivelSeleccionado = rubrica.califications[i]?.level === nivelNombre;

                                              return (
                                                  <td
                                                      key={j}
                                                      className={nivelSeleccionado ? "selected-level" : ""}
                                                  >
                                                      {level.description}
                                                  </td>
                                              );
                                          })}


                                          {/* Calificación, si existe */}
                                          <td>{rubrica?.califications[i]?.calification ?? "-"}</td>

                                          {/* Porcentaje del criterio */}
                                          <td>{criterio.percentage}%</td>

                                          {/* Ponderación: calificación * porcentaje / 100 */}
                                          <td>
                                              {rubrica.califications[i]
                                                  ? ((Number(rubrica.califications[i].calification) * Number(criterio.percentage)) / 100).toFixed(2): "-"}
                                          </td>

                                          {/* Comentario del criterio */}
                                          <td>{rubrica.califications[i]?.message ?? "-"}</td>
                                      </tr>
                                  ))}

                                  </tbody>
                              </table>

                      </div>

                  </div>

              </div>

              <div className="fila3">
                  <h3>Total: {rubrica?.totalScore}</h3>
              </div>

          </div>
      </Layout>

  );
};

export default Rubric;

//http://localhost:2020/RubricEvaluation/{idStudent}/{idSubject}/{semester}/{idRubric}
//http://localhost:8080/api/RubricEvaluation/2/3/2025-1/300
/*

<div className='fila4'>
    <h3>Comentarios</h3> 
    <div className='comentarios'>
        <input
            id="nombre"
            type="text"
            placeholder="Escriba el comentario que desea enviarle al profesor aqui"
            style={{
                padding: '8px',
                borderRadius: '10px',
                outline: 'none',
            }}
        />

        <div>
            <p>comentariosssssssssssssssssssssss</p>
        </div>
    </div>
</div>

*/