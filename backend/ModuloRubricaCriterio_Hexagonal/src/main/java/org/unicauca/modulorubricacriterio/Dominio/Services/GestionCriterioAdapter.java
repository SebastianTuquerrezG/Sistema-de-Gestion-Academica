package org.unicauca.modulorubricacriterio.Dominio.Services;

import java.util.List;

import org.unicauca.modulorubricacriterio.Aplicación.Input.IGestionCriterioPort;
import org.unicauca.modulorubricacriterio.Aplicación.Output.IConectorBDCriterioPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;

public class GestionCriterioAdapter implements IGestionCriterioPort{
    private final IConectorBDCriterioPort conectorBDCriterioPort;

    public GestionCriterioAdapter(IConectorBDCriterioPort objConectorBDCriterioPort) {
        this.conectorBDCriterioPort = objConectorBDCriterioPort;
    }

    @Override
    public List<Criterio> consultarCriterios() {
        return conectorBDCriterioPort.findAll();
    }

    @Override
    public Criterio consultarCriterio(Long Id) {
        return this.conectorBDCriterioPort.findById(Id);
    }

    @Override
    public Criterio crearCriterio(Criterio objPCriterio) {
        return this.conectorBDCriterioPort.save(objPCriterio);
    }

    @Override
    public Criterio modificarCriterio(Long Id, Criterio objPCriterio) {
        return this.conectorBDCriterioPort.update(Id, objPCriterio);
    }

    @Override
    public Criterio eliminarCriterio(Long Id) {
        return this.conectorBDCriterioPort.delete(Id);
    }
}