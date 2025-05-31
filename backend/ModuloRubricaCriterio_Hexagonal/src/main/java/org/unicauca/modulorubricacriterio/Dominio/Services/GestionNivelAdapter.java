package org.unicauca.modulorubricacriterio.Dominio.Services;

import java.util.List;

import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionNivelPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDNivelPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Input.IRabbitPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;

public class GestionNivelAdapter implements IGestionNivelPort {

    private final IConectorBDNivelPort conectorBDNivelPort;
    private final IRabbitPort rabbit;
    
    public GestionNivelAdapter(IConectorBDNivelPort conectorBDNivelPort, GestionRabbit rabbit) {
        this.conectorBDNivelPort = conectorBDNivelPort;
        this.rabbit = rabbit;
    }

    @Override
    public List<Nivel> consultarNiveles() {
        return this.conectorBDNivelPort.findAll();
    }

    @Override
    public Nivel consultarNivel(Long Id) {
        return this.conectorBDNivelPort.findById(Id);
    }

    @Override
    public Nivel crearNivel(Nivel objPNivel) {
        Nivel objNivelCreado = this.conectorBDNivelPort.save(objPNivel);
        rabbit.sendNivel(objNivelCreado);
        return objNivelCreado;
    }

    @Override
    public Nivel modificarNivel(Long Id, Nivel objPNivel) {
        Nivel objActualizado = this.conectorBDNivelPort.update(Id, objPNivel);
        rabbit.updateNivel(objActualizado);
        return objActualizado;
    }

    @Override
    public Nivel eliminarNivel(Long Id) {
        Nivel objNivelEliminado = this.conectorBDNivelPort.delete(Id);
        rabbit.deleteNivel(objNivelEliminado);
        return objNivelEliminado;
    }

}