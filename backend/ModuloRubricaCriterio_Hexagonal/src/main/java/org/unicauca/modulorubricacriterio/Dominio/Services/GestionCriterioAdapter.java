package org.unicauca.modulorubricacriterio.Dominio.Services;

import java.util.List;

import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionCriterioPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDCriterioPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Input.IRabbitPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Criterio;

public class GestionCriterioAdapter implements IGestionCriterioPort{
    private final IConectorBDCriterioPort conectorBDCriterioPort;
    private final IRabbitPort rabbitPort;

    public GestionCriterioAdapter(IConectorBDCriterioPort objConectorBDCriterioPort, GestionRabbit gestionRabbit) {
        this.conectorBDCriterioPort = objConectorBDCriterioPort;
        this.rabbitPort = gestionRabbit;
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
        Criterio criterio = conectorBDCriterioPort.save(objPCriterio);
        rabbitPort.sendCriterio(criterio);
        return criterio;
    }

    @Override
    public Criterio modificarCriterio(Long Id, Criterio objPCriterio) {
        Criterio criterio = this.conectorBDCriterioPort.update(Id, objPCriterio);
        rabbitPort.updateCriterio(criterio);
        return criterio;
    }

    @Override
    public Criterio eliminarCriterio(Long Id) {
        Criterio criterio = this.conectorBDCriterioPort.delete(Id);
        rabbitPort.deleteCriterio(criterio);
        return criterio;
    }
}