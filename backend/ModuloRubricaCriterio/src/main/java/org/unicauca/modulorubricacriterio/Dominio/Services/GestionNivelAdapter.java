package org.unicauca.modulorubricacriterio.Dominio.Services;

import java.util.List;

import org.unicauca.modulorubricacriterio.Aplicacion.Input.IGestionNivelPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDNivelPort;
import org.unicauca.modulorubricacriterio.Dominio.Modelos.Nivel;

public class GestionNivelAdapter implements IGestionNivelPort {

    private final IConectorBDNivelPort conectorBDNivelPort;
    
    public GestionNivelAdapter(IConectorBDNivelPort conectorBDNivelPort) {
        this.conectorBDNivelPort = conectorBDNivelPort;
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
        return objNivelCreado;
    }

    @Override
    public Nivel modificarNivel(Long Id, Nivel objPNivel) {
        Nivel objActualizado = this.conectorBDNivelPort.update(Id, objPNivel);
        return objActualizado;
    }

    @Override
    public Nivel eliminarNivel(Long Id) {
        Nivel objNivelEliminado = this.conectorBDNivelPort.delete(Id);
        return objNivelEliminado;
    }

}