package org.unicauca.modulorubricacriterio.Infraestructura.Configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.unicauca.modulorubricacriterio.Aplicación.Input.IRabbitPort;
import org.unicauca.modulorubricacriterio.Aplicación.Output.IConectorBDCriterioPort;
import org.unicauca.modulorubricacriterio.Aplicación.Output.IConectorBDNivelPort;
import org.unicauca.modulorubricacriterio.Aplicación.Output.IConectorBDRubricaPort;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionCriterioAdapter;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionNivelAdapter;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionRabbit;
import org.unicauca.modulorubricacriterio.Dominio.Services.GestionRubricasAdapter;

@Configuration
public class BeanConfiguration {
    /*Intancias de los adaptadores de la capa de dominio
    para la gestion de rubricas, criterios y niveles, necesarias en la infraestructura*/

    @Autowired
    private GestionRabbit rabbitPort;
    @Bean
    public GestionRubricasAdapter crearObjGestorRubricas(IConectorBDRubricaPort conectorBDRubricaPort) {
        GestionRubricasAdapter objGestorRubricas = new GestionRubricasAdapter(conectorBDRubricaPort, rabbitPort);
        return objGestorRubricas;
    }

    @Bean
    public GestionCriterioAdapter crearObjGestorCriterios(IConectorBDCriterioPort conectorBDCriterioPort) {
        GestionCriterioAdapter objGestorCriterios = new GestionCriterioAdapter(conectorBDCriterioPort, rabbitPort);
        return objGestorCriterios;
    }

    @Bean
    public GestionNivelAdapter crearObjGestorNiveles(IConectorBDNivelPort conectorBDNivelPort) {
        GestionNivelAdapter objGestorNiveles = new GestionNivelAdapter(conectorBDNivelPort, rabbitPort);
        return objGestorNiveles;
    }

}
