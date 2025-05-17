package org.unicauca.modulorubricacriterio.Dominio.Services;

import java.util.List;

import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionRubricaPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDRubricaPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;

public class GestionRubricasAdapter implements IGestionRubricaPort{

    private final IConectorBDRubricaPort conectorBDRubricaPort;

    public GestionRubricasAdapter(IConectorBDRubricaPort objConectorBDRubricaPort) {
        this.conectorBDRubricaPort = objConectorBDRubricaPort;
    }
    
    @Override
    public Rubrica consultarRubrica(Long Id) {
        Rubrica rubricaEncontrada = this.conectorBDRubricaPort.findById(Id);
        return rubricaEncontrada;
    }

    @Override
    public List<Rubrica> consultarRubricas() {
        List<Rubrica> listaRubricas = this.conectorBDRubricaPort.findAll();
        return listaRubricas;
    }

    @Override
    public Rubrica crearRubrica(Rubrica objPRubrica) {
        objPRubrica.setEstado(EstadosEnum.ACTIVO);//Crear Rúbricas con estado activo por defecto
        Rubrica rubricaCreada = conectorBDRubricaPort.saveRubric(objPRubrica);
        return rubricaCreada;
    }

    @Override
    public Rubrica modificarRubrica(Long Id, Rubrica objPRubrica) {
        Rubrica rubricaModificada = conectorBDRubricaPort.updateRubric(Id, objPRubrica);
        return rubricaModificada;
    }

    @Override
    public Rubrica editarEstadoRubrica(Long Id, String objPRubrica) {
        Rubrica rubricaModificada = conectorBDRubricaPort.changeEstate(Id, objPRubrica);
        return rubricaModificada;
    }

    @Override
    public Rubrica eliminarRubrica(Long Id) {
        Rubrica rubricaEliminada = conectorBDRubricaPort.deleteRubric(Id);
        return rubricaEliminada;
    }

}
