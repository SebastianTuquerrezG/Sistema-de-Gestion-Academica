package org.unicauca.modulorubricacriterio.Aplicacion.Output;

import java.util.List;

import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;

public interface IConectorBDCriterioPort {

    public List<Criterio> findAll();

    public Criterio findById(Long idCriterio);

    public Criterio save(Criterio objCriterio);

    public Criterio update(Long id, Criterio objCriterio);

    public Criterio delete(Long objCriterio);
}
