package org.unicauca.modulorubricacriterio.Infraestructura.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDCriterioPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDNivelPort;
import org.unicauca.modulorubricacriterio.Aplicacion.Output.IConectorBDRubricaPort;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionCriterioAdapter;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionNivelAdapter;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionRubricasAdapter;

@Configuration
public class BeanConfiguration {
    /*Intancias de los adaptadores de la capa de dominio
    para la gestion de rubricas, criterios y niveles, necesarias en la infraestructura*/

    @Bean
    public GestionRubricasAdapter crearObjGestorRubricas(IConectorBDRubricaPort conectorBDRubricaPort) {
        return new GestionRubricasAdapter(conectorBDRubricaPort);
    }

    @Bean
    public GestionCriterioAdapter crearObjGestorCriterios(IConectorBDCriterioPort conectorBDCriterioPort) {
        return new GestionCriterioAdapter(conectorBDCriterioPort);
    }

    @Bean
    public GestionNivelAdapter crearObjGestorNiveles(IConectorBDNivelPort conectorBDNivelPort) {
        return new GestionNivelAdapter(conectorBDNivelPort);
    }
}