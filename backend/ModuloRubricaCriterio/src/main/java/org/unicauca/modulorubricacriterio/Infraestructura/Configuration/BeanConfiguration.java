package org.unicauca.modulorubricacriterio.Infraestructura.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDCriterioPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDNivelPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDRubricaPort;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionCriterioAdapter;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionNivelAdapter;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionRubricasAdapter;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    /*Intancias de los adaptadores de la capa de dominio
    para la gestion de rubricas, criterios y niveles, necesarias en la infraestructura*/

    @Bean
    public GestionRubricasAdapter crearObjGestorRubricas(IConectorBDRubricaPort conectorBDRubricaPort) {
        GestionRubricasAdapter objGestorRubricas = new GestionRubricasAdapter(conectorBDRubricaPort);
        return objGestorRubricas;
    }

    @Bean
    public GestionCriterioAdapter crearObjGestorCriterios(IConectorBDCriterioPort conectorBDCriterioPort) {
        GestionCriterioAdapter objGestorCriterios = new GestionCriterioAdapter(conectorBDCriterioPort);
        return objGestorCriterios;
    }


    @Bean
    public GestionNivelAdapter crearObjGestorNiveles(IConectorBDNivelPort conectorBDNivelPort) {
        GestionNivelAdapter objGestorNiveles = new GestionNivelAdapter(conectorBDNivelPort);
        return objGestorNiveles;
    }
}