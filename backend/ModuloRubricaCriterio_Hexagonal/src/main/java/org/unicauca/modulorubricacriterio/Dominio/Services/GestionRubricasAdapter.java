package org.unicauca.modulorubricacriterio.Dominio.Services;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionRubricaPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDRubricaPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Materia;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Rubrica;
import org.unicauca.modulorubricacriterio.Infraestructura.Input.validacionEstados.EstadosEnum;

@RequiredArgsConstructor
public class GestionRubricasAdapter implements IGestionRubricaPort{

    private final IConectorBDRubricaPort conectorBDRubricaPort;
    
    @Override
    public Rubrica consultarRubrica(Long Id) {
        return this.conectorBDRubricaPort.findById(Id);
    }

    @Override
    public List<Rubrica> consultarRubricas() {
        return this.conectorBDRubricaPort.findAll();
    }

    @Override
    public Rubrica crearRubrica(Rubrica objPRubrica) {
        objPRubrica.setEstado(EstadosEnum.ACTIVO);//Crear Rúbricas con estado activo por defecto
        return conectorBDRubricaPort.saveRubric(objPRubrica);
    }

    @Override
    public Rubrica modificarRubrica(Long Id, Rubrica objPRubrica) {
        return conectorBDRubricaPort.updateRubric(Id, objPRubrica);
    }

    @Override
    public Rubrica editarEstadoRubrica(Long Id, String objPRubrica) {
        return conectorBDRubricaPort.changeEstate(Id, objPRubrica);
    }

    @Override
    public Rubrica eliminarRubrica(Long Id) {
        return conectorBDRubricaPort.deleteRubric(Id);
    }

    @Override
    public List<Materia> consultarMaterias() {
        return this.conectorBDRubricaPort.findAllMaterias();
    }
}
