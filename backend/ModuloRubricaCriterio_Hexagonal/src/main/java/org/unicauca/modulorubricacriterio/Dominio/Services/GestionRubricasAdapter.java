package org.unicauca.modulorubricacriterio.Dominio.Services;

import java.util.List;

import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionRubricaPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDRubricaPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Input.IRabbitPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Materia;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;

public class GestionRubricasAdapter implements IGestionRubricaPort{

    private final IConectorBDRubricaPort conectorBDRubricaPort;
    private final IRabbitPort rabbitPort;

    public GestionRubricasAdapter(IConectorBDRubricaPort objConectorBDRubricaPort, GestionRabbit rabbit) {
        this.rabbitPort = rabbit;
        this.conectorBDRubricaPort = objConectorBDRubricaPort;
    }
    
    @Override
    public Rubrica consultarRubrica(Long Id) {
        System.out.println("Petición GET /api/rubricas recibida");

        return this.conectorBDRubricaPort.findById(Id);
    }

    @Override
    public List<Rubrica> consultarRubricas() {
        return this.conectorBDRubricaPort.findAll();
    }

    @Override
    public Rubrica crearRubrica(Rubrica objPRubrica) {
        objPRubrica.setEstado(EstadosEnum.ACTIVO);//Crear Rúbricas con estado activo por defecto
        Rubrica rubricaCreada = conectorBDRubricaPort.saveRubric(objPRubrica);
        rabbitPort.sendRubric(rubricaCreada);
        return rubricaCreada;
    }

    @Override
    public Rubrica modificarRubrica(Long Id, Rubrica objPRubrica) {
        Rubrica rubricaModificada = conectorBDRubricaPort.updateRubric(Id, objPRubrica);
        rabbitPort.updateRubric(rubricaModificada);
        return rubricaModificada;
    }

    @Override
    public Rubrica editarEstadoRubrica(Long Id, String objPRubrica) {
        Rubrica rubricaModificada = conectorBDRubricaPort.changeEstate(Id, objPRubrica);
        rabbitPort.updateRubric(rubricaModificada);
        return rubricaModificada;
    }

    @Override
    public Rubrica eliminarRubrica(Long Id) {
        Rubrica rubricaEliminada = conectorBDRubricaPort.deleteRubric(Id);
        rabbitPort.deleteRubric(rubricaEliminada);
        return rubricaEliminada;
    }

    @Override
    public List<Materia> consultarMaterias() {
        return this.conectorBDRubricaPort.findAllMaterias();
    }
}
