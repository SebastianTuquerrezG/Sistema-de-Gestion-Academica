package org.unicauca.modulorubricacriterio.Aplicacion.Output;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;

public interface IConectorBDCriterioPort {

     List<Criterio> findAll();

     Criterio findById(Long idCriterio);

     Criterio save(Criterio objCriterio);

     Criterio update(Long id, Criterio objCriterio);

     Criterio delete(Long objCriterio);
}
