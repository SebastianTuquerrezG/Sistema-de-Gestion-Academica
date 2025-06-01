package org.unicauca.modulorubricacriterio.Aplicacion.Input;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;

public interface IRabbitPort {
    void sendRubric(Rubrica rubrica);
    void sendCriterio(Criterio criterio);
    void sendNivel(Nivel nivel);
    void updateRubric(Rubrica rubrica);
    void deleteRubric(Rubrica rubrica);
    void updateCriterio(Criterio criterio);
    void deleteCriterio(Criterio criterio);
    void updateNivel(Nivel nivel);
    void deleteNivel(Nivel nivel);
}
